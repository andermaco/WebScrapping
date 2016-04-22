package com.andermaco.scrapping.asynctask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.util.Patterns;

import com.andermaco.scrapping.R;
import com.andermaco.scrapping.model.DataLinkModel;
import com.andermaco.scrapping.model.LinkModel;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.ArrayList;

/**
 * Load data from remote server asynchronously
 *
 * @author andermaco@gmail.com
 * @version 1.0
 * @since 04/20/2016
 */
public class LoadDataAsyncTask extends AsyncTask<String, Void, DataLinkModel> {

    private static final String TAG_LOG = LoadDataAsyncTask.class.getSimpleName();
    private Elements mLinks;
    private Elements mImports;
    private ArrayList<LinkModel> mLinkList;
    private ArrayList<LinkModel> mImportList;
    private DownloadTaskDone mCallback;
    // ProgressDialog
    private ProgressDialog mProgressDialog;

    private Context mContext;

    public LoadDataAsyncTask(DownloadTaskDone callback) {
        mCallback = (DownloadTaskDone) callback;
        mContext = (Context) callback;
    }

    @Override
    protected void onPreExecute() {
        showProgressDialog();
    }

    @Override
    protected DataLinkModel doInBackground(String... params) {
        if (params.length == 0) {
                    throw new InvalidParameterException("Not valid parameter.");
                }
                if ( !Patterns.WEB_URL.matcher(params[0]).matches()) {
                    throw new InvalidParameterException("Not valid URI");
                }
        Document doc;
        try {
            doc = Jsoup.connect(params[0])
                    .userAgent("Mozilla/5.0 (Linux; Android 5.1.1; Nexus 5 Build/LMY48B; wv) " +
                            "AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/43.0.2357.65" +
                            " Mobile Safari/537.36")
                    .get();
        } catch (IOException e) {
            Log.e(TAG_LOG, "Error getting data");
            mCallback.onThrowException(e);
            return null;
        }
        mLinks = doc.select("a[href]");
        mImports = doc.select("link[href]");

        mLinkList = new ArrayList<LinkModel>();
        mImportList = new ArrayList<LinkModel>();

        for (Element link : mLinks) {
            mLinkList.add(new LinkModel(link));
        }
        for (Element link : mImports) {
            mImportList.add(new LinkModel(link));
        }
        return  new DataLinkModel(mLinkList, mImportList);
    }

    @Override
    protected void onPostExecute(DataLinkModel dataLinkModel) {
        hideProgressDialog();
        mCallback.onDownloadTaskDone(dataLinkModel);
    }

    // ProgressDialog methods
    /**
     * Shows undefined progress bar
     */
    private void showProgressDialog() {
        // Show dialog meanwhile adds are loaded
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(mContext);
        }
        if (mProgressDialog.isShowing())
            return;
        mProgressDialog.setTitle(mContext.getString(R.string.app_name));
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mProgressDialog.show();
    }
    /**
     * Hide undefined progress bar
     */
    private void hideProgressDialog() {
        // Avoiding IllegalArgumentException exceptions whenever progressDialog is being
        // shown and the device is rotated.
        try {
            mProgressDialog.dismiss();
        } catch (IllegalArgumentException e) {}
    }
}
