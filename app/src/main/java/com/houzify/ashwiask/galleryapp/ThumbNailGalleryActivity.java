package com.houzify.ashwiask.galleryapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.houzify.ashwiask.adaptor.ImageAdapter;
import com.houzify.ashwiask.com.houzify.ashwiask.common.Constants;

import java.util.ArrayList;

public class ThumbNailGalleryActivity extends GalleryBaseActivity {

    private static final String TAG = ThumbNailGalleryActivity.class.getSimpleName();

    private ImageAdapter mImageAdapter = null;
    private GridView gvImages = null;

    // Used to resize the bitmap but not used currently because of out of memory issue
    private ArrayList<Bitmap> mImageResizeBitMapList = new ArrayList<>(20);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thumb_nail_gallery);

        gvImages = (GridView) findViewById(R.id.gridViewImages);

        mImageAdapter = new ImageAdapter(this, mImageBitmapList);

        gvImages.setAdapter(mImageAdapter);

        gvImages.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "Clicked Postion: " + position);
                Intent intent = new Intent(getApplicationContext(), FullScreenGalleryActivity.class);

                intent.putExtra(Constants.IMAGE_CLICKED_POSITION_TAG, position);
                startActivity(intent);
            }
        });

    }




}
