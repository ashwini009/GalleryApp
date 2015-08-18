package com.houzify.ashwiask.listeners;

import android.graphics.Bitmap;

/**
 * Created by ashwiask on 8/18/2015.
 * <p/>
 * DownloadImageListener that listens when the downloading of image has been completed
 */
public interface DownloadImageListener {
    void onDownloadComplete(Bitmap bm);
}
