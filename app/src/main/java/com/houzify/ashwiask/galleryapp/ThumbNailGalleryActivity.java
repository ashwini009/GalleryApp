package com.houzify.ashwiask.galleryapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.houzify.ashwiask.adaptor.ImageAdapter;
import com.houzify.ashwiask.com.houzify.ashwiask.util.BitmapHelper;
import com.houzify.ashwiask.com.houzify.ashwiask.util.Constants;
import com.houzify.ashwiask.com.houzify.ashwiask.util.ImageDownloaderTask;
import com.houzify.ashwiask.com.houzify.ashwiask.util.ImageUrlFetcherTask;
import com.houzify.ashwiask.com.houzify.ashwiask.util.RequestDataTask;
import com.houzify.ashwiask.listeners.DataTaskListener;
import com.houzify.ashwiask.listeners.DownloadImageListener;
import com.houzify.ashwiask.listeners.URLImageListener;

import java.util.ArrayList;
import java.util.HashMap;

public class ThumbNailGalleryActivity extends GalleryBaseActivity implements DataTaskListener, URLImageListener, DownloadImageListener {

    private static final String TAG = ThumbNailGalleryActivity.class.getSimpleName();

    private ImageAdapter mImageAdapter = null;
    private GridView gvImages = null;

    private Button mBtnFetchImage = null;

    private static final String FLICKR_BASE_URL = "https://api.flickr.com/services/rest/?method=";
    private static final String FLICKR_NO_JSON_CALLBACK = "&nojsoncallback=1";

    private static final String FLICKR_GET_PUBLIC_PHOTOS = "flickr.people.getPublicPhotos";

    // API key
    private static final String FLICKR_API_KEY = "&api_key=227be805b3d6e934926d049533bb938a";

    private static final String FLICKR_USER_ID = "&user_id=135487628%40N06"; //%40 for &
    private static final String FLICKR_FORMAT_JSON = "&format=json";

    private static final String FLICKR_PHOTO_ID_ = "&photo_id=";

    private static final String FLICKR_GET_SIZES = "flickr.photos.getSizes";

    private ArrayList<String> mPhotoIdList = null;

    private ArrayList<String> mImageUrlList = new ArrayList<>();


    private HashMap<String, Bitmap> mImageHashMap = new HashMap<>();

    private ProgressDialog mProgressDialog = null;

    private int mCounter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thumb_nail_gallery);

        gvImages = (GridView) findViewById(R.id.gridViewImages);

        mImageAdapter = new ImageAdapter(this, mDecodedBitmapList);

        mBtnFetchImage = (Button) findViewById(R.id.fetch_image_btn);

        mProgressDialog = new ProgressDialog(this);

        mBtnFetchImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isInternetAvailable()) {
                    showProgressDialog(getString(R.string.fetching_data));
                    String url = createUrl(FLICKR_GET_PUBLIC_PHOTOS, null);
                    new RequestDataTask(ThumbNailGalleryActivity.this, ThumbNailGalleryActivity.this).execute(url);


                } else {
                    Toast.makeText(getApplicationContext(), getString(R.string.no_internet_connection), Toast.LENGTH_LONG).show();
                    gvImages.setVisibility(View.GONE);
                }
            }
        });
//        gvImages.setAdapter(mImageAdapter);

        gvImages.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "Clicked Postion: " + position);

                BitmapHelper.getInstance().setImageList(mImageBitmapList);

                Intent intent = new Intent(getApplicationContext(), FullScreenGalleryActivity.class);

                intent.putExtra(Constants.IMAGE_CLICKED_POSITION_TAG, position);
                startActivity(intent);
            }
        });

    }


    /**
     * Check for the internet connection availability
     *
     * @return - true if internet is available
     * else flase
     */
    private boolean isInternetAvailable() {
        boolean isInternetPresent = false;
        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager != null) {
            NetworkInfo[] info = connectivityManager.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        isInternetPresent = true;
                    }
                }
            }
        }
        return isInternetPresent;
    }


    private String createUrl(String methodId, String photoId) {
        String url = "";
        switch (methodId) {
            case FLICKR_GET_PUBLIC_PHOTOS:
                url = FLICKR_BASE_URL + FLICKR_GET_PUBLIC_PHOTOS + FLICKR_API_KEY + FLICKR_USER_ID + FLICKR_FORMAT_JSON + FLICKR_NO_JSON_CALLBACK;
                break;
            case FLICKR_GET_SIZES:
                url = FLICKR_BASE_URL + FLICKR_GET_SIZES + FLICKR_PHOTO_ID_ + photoId + FLICKR_API_KEY + FLICKR_FORMAT_JSON + FLICKR_NO_JSON_CALLBACK;
                break;
        }
        return url;
    }

    @Override
    public void onDataTaskComplete(ArrayList<String> imageList) {
        if (imageList != null) {

            mPhotoIdList = imageList;

            startImageUrlFetcherTask();
        }

    }

    private void startImageUrlFetcherTask() {
        if (mPhotoIdList != null && mPhotoIdList.size() > 0) {
            if(mProgressDialog != null && mProgressDialog.isShowing()) {
                mProgressDialog.setMessage(getString(R.string.fetching_url));
                Log.d(TAG, "mPhotoIdList size: " + mPhotoIdList.size());
                for (String photoId : mPhotoIdList) {
                    new ImageUrlFetcherTask(this, this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, createUrl(FLICKR_GET_SIZES, photoId));
                }
            }
        }

    }

    @Override
    public void onImageUrlFetched(String imageUrl) {
        mImageUrlList.add(imageUrl);

        mCounter++;

        if (mCounter == mPhotoIdList.size()) {
            downloadImages();
            mCounter = 0;
        }


    }

    private void downloadImages() {
        if (mImageUrlList != null && mImageUrlList.size() > 0) {
            Log.d(TAG, "mImageUrlList size: " + mImageUrlList.size());
            if(mProgressDialog != null && mProgressDialog.isShowing()) {
                mProgressDialog.setMessage(getString(R.string.downloading_image));
                for (String url : mImageUrlList) {
                    new ImageDownloaderTask(this, this).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, url);
                }
            }
        }
    }

    @Override
    public void onDownloadComplete(Bitmap bm) {
        mImageBitmapList.add(bm);

        mCounter++;
        if (mCounter == mImageUrlList.size()) {
            //Set the adaptor
            populateBitmapArray();
            mProgressDialog.dismiss();
            mBtnFetchImage.setVisibility(View.GONE);

            gvImages.setVisibility(View.VISIBLE);
            gvImages.setAdapter(mImageAdapter);
        }
    }

    /**
     * Displays the progress dialog with the given message
     * @param message - Message to be displayed
     */
    private void showProgressDialog(String message){
        mProgressDialog.setMessage(message);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.show();
    }

}
