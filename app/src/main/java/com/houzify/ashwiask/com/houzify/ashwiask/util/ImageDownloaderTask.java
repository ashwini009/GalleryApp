package com.houzify.ashwiask.com.houzify.ashwiask.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.houzify.ashwiask.listeners.DownloadImageListener;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by ashwiask on 8/18/2015.
 * <p/>
 * This class downloads the image from the fetched url asynchronously
 */
public class ImageDownloaderTask extends AsyncTask<String, Void, Bitmap> {

    private DownloadImageListener mDownLoadImageListener = null;

    public ImageDownloaderTask(DownloadImageListener downloadListener, Context context) {
        mDownLoadImageListener = downloadListener;
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        Bitmap bm = null;
        try {
            URL url = new URL(params[0]);
            URLConnection conn = url.openConnection();
            conn.connect();
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            bm = BitmapFactory.decodeStream(bis);
            bis.close();
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bm;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        mDownLoadImageListener.onDownloadComplete(bitmap);

    }
}
