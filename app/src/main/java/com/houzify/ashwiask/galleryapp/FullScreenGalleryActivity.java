package com.houzify.ashwiask.galleryapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.houzify.ashwiask.adaptor.FullScreenGalleryPagerAdapter;
import com.houzify.ashwiask.com.houzify.ashwiask.util.BitmapHelper;
import com.houzify.ashwiask.com.houzify.ashwiask.util.Constants;

import java.util.ArrayList;

/**
 * Created by ashwiask on 8/12/2015.
 * <p/>
 * This activity shows the full size image of the thumbnail image clicked.
 */
public class FullScreenGalleryActivity extends GalleryBaseActivity {

    private FullScreenGalleryPagerAdapter mPagerAdapter = null;

    /**
     * View pager that holds all the images. The image can be swiped right or left to see the next or previous one
     */
    private ViewPager mImageViewPager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_full_screen_gallery);

        mImageViewPager = (ViewPager) findViewById(R.id.galleryPager);

        Intent intent = getIntent();

        int imageClickedPos = intent.getIntExtra(Constants.IMAGE_CLICKED_POSITION_TAG, -1);

        ArrayList<Bitmap>imageList = BitmapHelper.getInstance().getImageList();

        Log.d("Full Screen", "bitmap list size "+ imageList.size());
        mPagerAdapter = new FullScreenGalleryPagerAdapter(this, imageList);

        mImageViewPager.setAdapter(mPagerAdapter);

        mImageViewPager.setCurrentItem(imageClickedPos);


    }
}
