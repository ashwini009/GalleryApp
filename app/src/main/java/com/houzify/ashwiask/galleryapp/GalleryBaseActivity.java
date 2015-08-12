package com.houzify.ashwiask.galleryapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

/**
 * Created by ashwiask on 8/13/2015.
 *
 * This activity is the base activity for Gallery Application
 */
public class GalleryBaseActivity extends AppCompatActivity {

    /**
     * Images stored in drawable folder - Right now, 20 images are taken into consideration
     */

    private static final Integer[] mImageArrays = {R.drawable.abstract_image_1, R.drawable.abstract_image_2, R.drawable.abstract_image_3,
            R.drawable.abstract_image_4, R.drawable.abstract_image_5, R.drawable.abstract_image_6,
            R.drawable.abstract_image_7, R.drawable.abstract_image_8, R.drawable.abstract_image_9, R.drawable.abstract_image_10,
            R.drawable.abstract_image_11, R.drawable.abstract_image_12, R.drawable.abstract_image_13, R.drawable.abstract_image_14,
            R.drawable.abstract_image_15, R.drawable.abstract_image_16, R.drawable.abstract_image_17, R.drawable.abstract_image_18,
            R.drawable.abstract_image_19, R.drawable.abstract_image_20};

    /**
     * List of Bit map of all the images
     */

    protected ArrayList<Bitmap>mImageBitmapList = new ArrayList<>(20);


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        populateBitmapArray();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_thumb_nail_gallery, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Convert image to bit map and populate into bitmap list
     */
    public void populateBitmapArray() {
        for (int i = 0; i < mImageArrays.length; i++) {
            Bitmap abstractImageBitmap = BitmapFactory.decodeResource(this.getResources(), mImageArrays[i]);
            mImageBitmapList.add(abstractImageBitmap);
        }
    }
}
