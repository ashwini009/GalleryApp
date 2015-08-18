package com.houzify.ashwiask.com.houzify.ashwiask.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.houzify.ashwiask.galleryapp.R;
import com.houzify.ashwiask.listeners.DataTaskListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Created by ashwiask on 8/18/2015.
 */
public class RequestDataTask extends AsyncTask<String, Void, String> {
    private Context mContext = null;

    private static final String TAG = RequestDataTask.class.getSimpleName();

    private ProgressDialog mProgressDialog = null;

    private ArrayList<String>mPhotoIdList = new ArrayList<>();

    private DataTaskListener mTaskListener = null;

    public RequestDataTask(Context context, DataTaskListener listener) {
        mContext = context;
        mTaskListener = listener;
        mProgressDialog = new ProgressDialog(context);
    }

    @Override
    protected String doInBackground(String... uri) {
        StringBuilder builder = new StringBuilder("");
        java.net.URL url;
        BufferedReader reader = null;

        try {
            url = new URL(uri[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            String response;

            reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), Charset.forName("UTF-8")));
            while ((response = reader.readLine()) != null) {
                builder.append(response + "\n");
            }

        } catch (MalformedURLException ex) {
            Log.d(TAG, "URL is not proper " + ex.getMessage());
        } catch (SecurityException e) {
            Log.d(TAG, "Permission denied. Please check the API key");
        } catch (IOException e) {
            Log.d(TAG, "URL not found " + e.getMessage());
        } finally {
            try {

                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return builder.toString();

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        mProgressDialog.setMessage(mContext.getString(R.string.fetching_data));
        mProgressDialog.setIndeterminate(true);
//        mProgressDialog.show();
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if (mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }

        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONObject jsonPhotos = jsonObject.getJSONObject("photos");
            JSONArray jsonArray = jsonPhotos.getJSONArray("photo");

            for (int i=0; i< jsonArray.length(); i++){
                JSONObject jsonPhoto = jsonArray.getJSONObject(i);
                String photoId = jsonPhoto.getString("id");
                mPhotoIdList.add(photoId);

            }
            mTaskListener.onDataTaskComplete(mPhotoIdList);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
