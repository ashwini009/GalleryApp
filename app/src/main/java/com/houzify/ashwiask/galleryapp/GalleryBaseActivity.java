package com.houzify.ashwiask.galleryapp;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

/**
 * Created by ashwiask on 8/13/2015.
 * <p/>
 * This activity is the base activity for Gallery Application
 */
public class GalleryBaseActivity extends AppCompatActivity {

    /**
     * Images stored in drawable folder - Right now, 20 images are taken into consideration
     */
//
//    private static final Integer[] mImageArrays = {R.drawable.abstract_image_1, R.drawable.abstract_image_2, R.drawable.abstract_image_3,
//            R.drawable.abstract_image_4, R.drawable.abstract_image_5, R.drawable.abstract_image_6,
//            R.drawable.abstract_image_7, R.drawable.abstract_image_8, R.drawable.abstract_image_9, R.drawable.abstract_image_10,
//            R.drawable.abstract_image_11, R.drawable.abstract_image_12, R.drawable.abstract_image_13, R.drawable.abstract_image_14,
//            R.drawable.abstract_image_15, R.drawable.abstract_image_16, R.drawable.abstract_image_17, R.drawable.abstract_image_18,
//            R.drawable.abstract_image_19, R.drawable.abstract_image_20, R.drawable.abstract_image_21, R.drawable.abstract_image_22,
//            R.drawable.abstract_image_23, R.drawable.abstract_image_24, R.drawable.abstract_image_25, R.drawable.abstract_image_26,
//            R.drawable.abstract_image_27, R.drawable.abstract_image_28, R.drawable.abstract_image_29, R.drawable.abstract_image_30};

    /**
     * List of Bit map of all the images
     */

    protected ArrayList<Bitmap> mImageBitmapList = new ArrayList<>();

    protected ArrayList<Bitmap>mDecodedBitmapList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        //populateBitmapArray();
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
        for(Bitmap image: mImageBitmapList) {
            Bitmap abstractImageBitmap = Bitmap.createScaledBitmap(image, 200, 200, true);
            mDecodedBitmapList.add(abstractImageBitmap);
        }
    }



    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    /**
     * Calculates the size
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }
}
