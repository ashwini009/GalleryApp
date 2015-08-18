package com.houzify.ashwiask.adaptor;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.houzify.ashwiask.galleryapp.R;
import com.houzify.ashwiask.galleryapp.TouchImageView;

import java.util.ArrayList;

/**
 * Created by ashwiask on 8/12/2015.
 *
 * Adapter that takes control of handling all the bitmaps and populating them on the view pager
 */
public class FullScreenGalleryPagerAdapter extends PagerAdapter {


    private ArrayList<Bitmap> mImageBitmapList = null;
    private Context mContext = null;

    private TouchImageView ivImage = null;

    private LayoutInflater mLayoutInflater = null;

    public FullScreenGalleryPagerAdapter(Context context, ArrayList<Bitmap> imageBitmapList) {
        mImageBitmapList = imageBitmapList;
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mImageBitmapList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == (RelativeLayout) object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        View view = mLayoutInflater.inflate(R.layout.full_screen_image_layout, container,
                false);

        ivImage = (TouchImageView) view.findViewById(R.id.ivGallery);

        ivImage.setImageBitmap(mImageBitmapList.get(position));

        ((ViewPager) container).addView(view);

        return view;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((RelativeLayout) object);

    }
}
