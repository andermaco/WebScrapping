package com.andermaco.scrapping.asynctask;


import com.andermaco.scrapping.model.DataLinkModel;

public interface DownloadTaskDone {
    void onDownloadTaskDone(DataLinkModel dataLinkModel);
    void onThrowException(Exception e);
}
