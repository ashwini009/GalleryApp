package com.houzify.ashwiask.adaptor;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by ashwiask on 8/12/2015.
 *
 * Adapter that holds the images to be displayed on the grid view
 */
public class ImageAdapter extends BaseAdapter {
    private ArrayList<Bitmap> mImageBitmapList = null;

    private GridView.LayoutParams mImageViewLauoutParams = null;

    private Context mContext = null;

    public ImageAdapter(Context context, ArrayList<Bitmap>imageBitmapList) {
        mContext = context;
        mImageBitmapList = imageBitmapList;
        mImageViewLauoutParams =new GridView.LayoutParams(GridView.LayoutParams.MATCH_PARENT, GridView.LayoutParams.MATCH_PARENT);
    }

    @Override
    public int getCount() {
        return mImageBitmapList.size();
    }

    @Override
    public Bitmap getItem(int position) {
        Bitmap imageBitmap = null;
        if(mImageBitmapList != null && mImageBitmapList.size() > 0){
            imageBitmap = mImageBitmapList.get(position);
        }
        return imageBitmap;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = null;
        if(convertView == null){
            imageView = new ImageView(mContext);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setLayoutParams(new GridView.LayoutParams(mImageViewLauoutParams));

        }else{// For view recycling
            imageView = (ImageView) convertView;
        }

        imageView.setImageBitmap(mImageBitmapList.get(position));

        return imageView;
    }

}
