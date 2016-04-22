package com.andermaco.scrapping.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.andermaco.scrapping.fragment.ScrapFragment;
import com.andermaco.scrapping.model.LinkModel;

import java.util.ArrayList;

/**
 * TabAdapter
 *
 * @author andermaco@gmail.com
 * @version 1.0
 * @since 04/20/2016
 */
public class TabAdapter extends FragmentPagerAdapter {

    private int mTabsNumber;
    private ArrayList<LinkModel> mLinkList;
    private ArrayList<LinkModel> mImportList;

    public TabAdapter(FragmentManager fm, int tabsNumber) {
        super(fm);
        mTabsNumber = tabsNumber;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return ScrapFragment.newInstance(mLinkList);
            case 1:
                return ScrapFragment.newInstance(mImportList);
        }
        return  null;
    }

    @Override
    public int getCount() {
        return mTabsNumber;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Links";
            case 1:
                return "Imports";
        }
        return super.getPageTitle(position);
    }

    public void updateViewPager() {
        for (int i=0; i < mTabsNumber; i++) {
            getItem(i);
        }
    }

    public ArrayList<LinkModel> getmLinkList() {
        return mLinkList;
    }

    public void setmLinkList(ArrayList<LinkModel> mLinkList) {
        this.mLinkList = mLinkList;
    }

    public ArrayList<LinkModel> getmImportList() {
        return mImportList;
    }

    public void setmImportList(ArrayList<LinkModel> mImportList) {
        this.mImportList = mImportList;
    }

}