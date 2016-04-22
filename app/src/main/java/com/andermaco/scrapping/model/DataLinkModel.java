package com.andermaco.scrapping.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class DataLinkModel implements Parcelable {

    private ArrayList<LinkModel> mLinks;
    private ArrayList<LinkModel> mImportLinks;

    public DataLinkModel(Parcel in) {
        if (in.readByte() == 0x01) {
            mLinks = new ArrayList<LinkModel>();
            in.readList(mLinks, LinkModel.class.getClassLoader());
        } else {
            mLinks = null;
        }
        if (in.readByte() == 0x01) {
            mImportLinks = new ArrayList<LinkModel>();
            in.readList(mImportLinks, LinkModel.class.getClassLoader());
        } else {
            mImportLinks = null;
        }
    }

    public DataLinkModel(ArrayList<LinkModel> linkList, ArrayList<LinkModel> importList) {
        this.mLinks = linkList;
        this.mImportLinks = importList;
    }

    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (mLinks == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(mLinks);
        }
        if (mImportLinks == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(mImportLinks);
        }
    }

    @SuppressWarnings("unused")
    public static final Creator<DataLinkModel> CREATOR = new Creator<DataLinkModel>() {
        @Override
        public DataLinkModel createFromParcel(Parcel in) {
            return new DataLinkModel(in);
        }

        @Override
        public DataLinkModel[] newArray(int size) {
            return new DataLinkModel[size];
        }
    };

    public ArrayList<LinkModel> getmLinks() {
        return mLinks;
    }

    public void setmLinks(ArrayList<LinkModel> mLinks) {
        this.mLinks = mLinks;
    }

    public ArrayList<LinkModel> getmImportLinks() {
        return mImportLinks;
    }

    public void setmImportLinks(ArrayList<LinkModel> mImportLinks) {
        this.mImportLinks = mImportLinks;
    }
}
