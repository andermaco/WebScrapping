package com.andermaco.scrapping.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andermaco.scrapping.R;
import com.andermaco.scrapping.adapter.CustomAdapter;
import com.andermaco.scrapping.model.LinkModel;

import java.util.ArrayList;

/**
 * Fragment containing recycle vies
 *
 * @author andermaco@gmail.com
 * @version 1.0
 * @since 04/20/2016
 */
public class ScrapFragment extends Fragment {

    // SaveInstance param.
    private static final String PARAM = "param";

    // Custom RecyclerView
    private RecyclerView mRecyclerView;

    // Recycler view custom adapter
    private CustomAdapter mAdapter;

    // RecyclerView layout container
    private RecyclerView.LayoutManager mLayoutManager;

    // Links data
    private ArrayList<LinkModel> mBaseLinks;

    // Recycler view
    private View rootView;

    /**
     * Common fragment used to show links and imports.
     * @param param ArrayList<LinkModel>.
     * @return A new instance of fragment DataFragment.
     */
    public static ScrapFragment newInstance(ArrayList<LinkModel> param) {
        ScrapFragment fragment = new ScrapFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(PARAM, param);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Builds custom view
     * @param inflater XML to be inflated.
     * @param container ViewGroup container
     * @param savedInstanceState Instance state
     * @return Fragment's view
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
             Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);

        rootView = inflater.inflate(R.layout.recycler_view_frag, container, false);

        // Setting the adapter
        mAdapter = new CustomAdapter(getActivity());

        // Get recycler view
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        // Setting the RecyclerView layout manager.
        setRecyclerViewLayoutManager();

        // Set CustomAdapter as the adapter for RecyclerView.
        mRecyclerView.setAdapter(mAdapter);

        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(PARAM, mBaseLinks);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null)
            mBaseLinks = savedInstanceState.getParcelableArrayList(PARAM);
    }


    /**
     * Init fragment's LayoutManager and Recycler view
     */
    private void setRecyclerViewLayoutManager() {
        int scrollPosition = 0;
        // If a layout manager has already been set, get current scroll position.
        if (mRecyclerView.getLayoutManager() != null) {
            scrollPosition = ((LinearLayoutManager) mRecyclerView.getLayoutManager())
                    .findFirstCompletelyVisibleItemPosition();
        }
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.scrollToPosition(scrollPosition);
    }

    /**
     * Updates adapter items.
     * @param linkModels Data link array list
     */
    public void notifyDataChanged(ArrayList<LinkModel> linkModels) {
        mAdapter.setmDataSet(linkModels);
        mRecyclerView.invalidate();
        mAdapter.notifyDataSetChanged();
    }
}
