package com.andermaco.scrapping;

import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.andermaco.scrapping.adapter.TabAdapter;
import com.andermaco.scrapping.asynctask.DownloadTaskDone;
import com.andermaco.scrapping.asynctask.LoadDataAsyncTask;
import com.andermaco.scrapping.broadcast.NetworkStatusReceiver;
import com.andermaco.scrapping.bus.ScrappingBus;
import com.andermaco.scrapping.fragment.ScrapFragment;
import com.andermaco.scrapping.model.DataLinkModel;
import com.andermaco.scrapping.util.ConnectivityDialogFragment;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.List;

/**
 * MainActiviy app launcher.
 *
 * @author andermaco@gmail.com
 * @version 1.0
 * @since 04/20/2016
 */
public class MainActivity extends AppCompatActivity implements
        TabLayout.OnTabSelectedListener, ViewPager.OnPageChangeListener, DownloadTaskDone {

    // Saved Instance state params and tab number
    private final static String VIEWPAGER_INDEX = "viewpager_index";
    private final static String DATALINK_MODEL_PARAM = "datalink_model_param";

    // Network status receiver
    private NetworkStatusReceiver mReceiver;

    // Otto even bus, used as sample of sending event through Otto.
    private Bus bus;

    // Manage App tab layout
    private TabLayout mTabLayout;

    // ViewPager
    private ViewPager mViewPager;

    // Tab adapter
    private TabAdapter mTabAdapter;

    // DataLinkModel object with links and imports
    private DataLinkModel mDataLinkModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Init Broadcast Connection receiver
        mReceiver = new NetworkStatusReceiver();

        // Get ViewPager
        mViewPager = (ViewPager) findViewById(R.id.pager);
        // Set ViewPager page listener
        mViewPager.addOnPageChangeListener(this);

        // Get tabs.
        buildTabs();

        // Setting ViewPager Adapter
        mTabAdapter = new TabAdapter(getSupportFragmentManager(), mTabLayout.getTabCount());
        // Setting Viewpager adapter
        mViewPager.setAdapter(mTabAdapter);

        if (savedInstanceState != null) {
            // Get Tab index previous to rotation
            selectPage(savedInstanceState.getInt(VIEWPAGER_INDEX));

            // Get Datalink_model object
            mDataLinkModel = savedInstanceState.getParcelable(DATALINK_MODEL_PARAM);
        }

        // Get ViewPager
        mViewPager = (ViewPager) findViewById(R.id.pager);

        // Init Otto event bus
        bus = ScrappingBus.getInstance();

        if (!isNetworkAvailable()) {
            ConnectivityDialogFragment connectivityDialogFragment = new ConnectivityDialogFragment();
            connectivityDialogFragment.show(getSupportFragmentManager(), "ErrorDialog");
        } else {
            if (mDataLinkModel == null) {
                // Load data
                new LoadDataAsyncTask(MainActivity.this)
                        .execute(getString(R.string.url));
            }
        }
    }

    private void buildTabs() {
        mTabLayout = (TabLayout) findViewById(R.id.tab_layout);
        mTabLayout.addTab(mTabLayout.newTab().setText("Links"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Imports"));
        mTabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        mTabLayout.setOnTabSelectedListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Sample of Register a Broadcast receiver
        registerReceiver(mReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        // Sample of using Otto even bus
        ScrappingBus.getInstance().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Un-register broadcast receiver
        if (mReceiver != null)
            unregisterReceiver(mReceiver);
        // Un-register Otto event bus
        ScrappingBus.getInstance().unregister(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        // Saves current viewpager position
        outState.putInt(VIEWPAGER_INDEX, mViewPager.getCurrentItem());

        // Put Datalink_model object
        outState.putParcelable(DATALINK_MODEL_PARAM, mDataLinkModel);

        super.onSaveInstanceState(outState);
    }

    @Subscribe
    public void onNetworkConnected(Boolean connected)
    {
        // Load data
        if (mTabAdapter.getmLinkList() == null) {
            new LoadDataAsyncTask(MainActivity.this)
                    .execute("http://www.yahoo.com");
        }
    }

    /**
     * Checks whether there is network connectivity
     * @return Return whether network is available.
     */
    private boolean isNetworkAvailable() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }


    // TabLayout.OnTabSelectedListener methods
    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        selectPage(tab.getPosition());
    }
    @Override
    public void onTabUnselected(TabLayout.Tab tab) {}
    @Override
    public void onTabReselected(TabLayout.Tab tab) {}


    // Viewpager methods
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
    @Override
    public void onPageSelected(int position) {
        selectPage(position);
    }
    @Override
    public void onPageScrollStateChanged(int state) {
    }

    // Updates Viewpager and Tablayout indexes.
    private void selectPage(int pageIndex){
        mTabLayout.setScrollPosition(pageIndex, 0f, true);
        mViewPager.setCurrentItem(pageIndex);
    }


    // DownloadTaskDone methods
    @Override
    public void onDownloadTaskDone(DataLinkModel dataLinkModel) {
        mDataLinkModel = dataLinkModel;
        notifyUpdateToFragments(dataLinkModel);
    }
    @Override
    public void onThrowException(Exception e) {

    }

    /**
     * Notify Fragment's adapter data needs to be updated.
     */
    private void notifyUpdateToFragments(DataLinkModel dataLinkModel) {
        // Update fragments
        List<Fragment> fragmentList =  getSupportFragmentManager().getFragments();
        for (Fragment fragment: fragmentList) {
            if (fragment != null) {
                if (fragment.getTag().equals("android:switcher:" + R.id.pager + ":0"))
                    ((ScrapFragment) fragment).notifyDataChanged(dataLinkModel.getmLinks());
                else
                    ((ScrapFragment) fragment).notifyDataChanged(dataLinkModel.getmImportLinks());
            }
        }
    }
}
