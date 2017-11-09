/******************************************
 * Custom adapter for image grid view
 ***********************************************/
package com.example.tingting.hw2.controller;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.tingting.hw2.R;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;

    public ImageAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return mThumbIds.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageResource(mThumbIds[position]);
        return imageView;
    }


    public void changeImage(int row, int column, int turn)
    {
        int pos = (41 - ((row * 7) + (6 - column)));
        // if it is player one
        if(turn == 1)
        {
            mThumbIds[pos] = R.drawable.red;
        }

        // player two
        else
        {
            mThumbIds[pos] = R.drawable.blue;
        }
    }


    private Integer[] setImage()
    {
        Integer image[] = new Integer[42];
        for(int i = 0; i < 42; ++i)
        {
            image[i] = R.drawable.empty;
        }
        return image;
    }


    // references to our images
    public Integer[] mThumbIds = setImage();
}