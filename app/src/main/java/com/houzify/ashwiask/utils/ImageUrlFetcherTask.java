package com.houzify.ashwiask.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.houzify.ashwiask.listeners.URLImageListener;

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

/**
 * Created by ashwiask on 8/18/2015.
 * <p/>
 * This class fetches the image URL of every public photo using the photo ID fetched from RequestDataTask.
 */
public class ImageUrlFetcherTask extends AsyncTask<String, Void, String> {

    private URLImageListener imageUrlFetchListner = null;

    private static final String TAG = ImageUrlFetcherTask.class.getSimpleName();

    public ImageUrlFetcherTask(URLImageListener imageListener, Context context) {
        imageUrlFetchListner = imageListener;
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

    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONObject photoSizesJson = jsonObject.getJSONObject("sizes");
            JSONArray sizeJsonArray = photoSizesJson.getJSONArray("size");
            for (int i = 0; i < sizeJsonArray.length(); i++) {
                JSONObject size = sizeJsonArray.getJSONObject(i);
                String label = size.getString("label");
                if (label.equalsIgnoreCase("Original")) {
                    String imageUrl = size.getString("source");
                    imageUrlFetchListner.onImageUrlFetched(imageUrl);
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
