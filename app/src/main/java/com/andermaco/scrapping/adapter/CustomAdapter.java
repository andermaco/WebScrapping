package com.andermaco.scrapping.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.andermaco.scrapping.R;
import com.andermaco.scrapping.model.LinkModel;

import java.util.ArrayList;

/**
 * Provide custom adapter based on CardView.
 * @version 1.0
 * @since 04/20/2016
 */
public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.LinkViewHolder> {

    private static final String TAG_LOG = "CustomAdapter";

    // Base dataset
    private ArrayList<LinkModel> mDataSet;
    private Context mContext;

    public CustomAdapter(Context context) {
        mContext = context;
    }

    /**
     * Initialize the dataset of the Adapter.
     *
     * @param dataSet String[] containing the data to populate views to be used by RecyclerView.
     */
    public CustomAdapter(ArrayList<LinkModel> dataSet, Context context) {
        mDataSet = dataSet;
        mContext = context;
    }

    /**
     * Provide a reference to the type of views that you are using (custom ViewHolder)
     */
    public class LinkViewHolder extends RecyclerView.ViewHolder {
        private final TextView dataLink;
        private final TextView urlLink;
        private final ImageView iconView;

        public LinkViewHolder(View v) {
            super(v);
            dataLink = (TextView) v.findViewById(R.id.linkMessageText);
            urlLink = (TextView) v.findViewById(R.id.linkUrl);
            iconView = (ImageView) v.findViewById(R.id.linkIcon);

            // Define click listener for the ViewHolder's View.
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG_LOG, "Element " + getLayoutPosition() + " clicked.");
                    Intent i =
                            new Intent(Intent.ACTION_VIEW, Uri.parse(urlLink.getText().toString()));
                    mContext.startActivity(i);
                }
            });
        }
    }

    // Create new views (invoked by the layout manager)
    @Override
    public LinkViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view.
        View v = LayoutInflater
                .from(viewGroup.getContext()).inflate(R.layout.text_row_item, viewGroup, false);
        return new LinkViewHolder(v);
    }

    /**
     * Replace the contents of a view
     * @param viewHolder LinkView holder
     * @param position Data index
     */
    @Override
    public void onBindViewHolder(LinkViewHolder viewHolder, final int position) {
        Log.d(TAG_LOG, "Element " + position + " set.");

        // Get element from your dataset at this position and replace the contents of the view
        // with that element
        viewHolder.dataLink.setText(mDataSet.get(position).getLinkText());
        viewHolder.urlLink.setText(mDataSet.get(position).getmLink());
        viewHolder.iconView.setImageResource(R.drawable.ic_link_black_24dp);
    }

    @Override
    public int getItemCount() {
        return mDataSet == null ?0:mDataSet.size();
    }

    public ArrayList<LinkModel> getmDataSet() {
        return mDataSet;
    }

    public void setmDataSet(ArrayList<LinkModel> mDataSet) {
        this.mDataSet = mDataSet;
    }
}
