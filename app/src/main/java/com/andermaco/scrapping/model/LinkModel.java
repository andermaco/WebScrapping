package com.andermaco.scrapping.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.jsoup.nodes.Element;

/**
 * @author andermaco@gmail.com
 * @version 1.0
 * @see Parcelable
 *
 * Parcelable object. It will be used to store org.jsoup.nodes.Element object between activities.
 */
public class LinkModel implements Parcelable {

    private String mLink;
    private String linkText;

    LinkModel(Parcel in) {
        mLink = in.readString();
        linkText = in.readString();
    }

    public LinkModel() {}

    public LinkModel(Element e) {
        this.mLink = e.attr("abs:href");
        this.linkText = e.text();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mLink);
        dest.writeString(linkText);
    }

    @SuppressWarnings("unused")
    public static final Creator<LinkModel> CREATOR = new Creator<LinkModel>() {
        @Override
        public LinkModel createFromParcel(Parcel in) {
            return new LinkModel(in);
        }

        @Override
        public LinkModel[] newArray(int size) {
            return new LinkModel[size];
        }
    };

    public String getmLink() {
        return mLink;
    }

    public void setmLink(String mLink) {
        this.mLink = mLink;
    }

    public String getLinkText() {
        return linkText;
    }

    public void setLinkText(String linkText) {
        this.linkText = linkText;
    }
}
