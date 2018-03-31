package com.sunxuedian.graduationproject.utils;

import android.app.Fragment;
import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by sunxuedian on 2018/3/15.
 */

public class ImageLoader {

    public static void showImage(Context context, ImageView imageView, String url){
        Glide.with(context).load(url).into(imageView);
    }

    public static void showImage(Context context, ImageView imageView, Integer resId){
        Glide.with(context).load(resId).into(imageView);
    }

    public static void showImage(Fragment context, ImageView imageView, String url){
        Glide.with(context).load(url).into(imageView);
    }

    public static void showImage(Fragment context, ImageView imageView, Integer resId){
        Glide.with(context).load(resId).into(imageView);
    }
}
