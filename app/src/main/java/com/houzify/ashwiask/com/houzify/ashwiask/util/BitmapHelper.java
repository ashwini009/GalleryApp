package com.houzify.ashwiask.com.houzify.ashwiask.util;

import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * Created by ashwiask on 8/19/2015.
 * <p/>
 * Helper class to store list of bitmaps. This helps in passing bitmap list between activities
 */
public class BitmapHelper {

    private ArrayList<Bitmap> imageList = null;

    private static BitmapHelper bitmapHelper = null;

    private BitmapHelper() {

    }

    public static synchronized BitmapHelper getInstance() {
        if (bitmapHelper == null) {
            bitmapHelper = new BitmapHelper();

        }
        return bitmapHelper;
    }


    public ArrayList<Bitmap> getImageList() {
        return imageList;
    }

    public void setImageList(ArrayList<Bitmap> bitmapList) {
        imageList = bitmapList;
    }

}
