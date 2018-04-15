package com.sunxuedian.graduationproject.utils;

import android.app.Fragment;
import android.content.Context;
import android.support.annotation.IdRes;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.sunxuedian.graduationproject.R;

import java.io.File;

import jp.wasabeef.glide.transformations.BlurTransformation;

import static android.R.attr.radius;
import static android.view.KeyCharacterMap.load;
import static cn.smssdk.utils.a.e;

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

    public static void showImage(Context context, ImageView imageView, String url, Integer errorResId){
        Glide.with(context).load(url).error(errorResId).into(imageView);
    }

    public static void showImage(Context context, ImageView imageView, Integer resId, Integer errorResId){
        Glide.with(context).load(resId).error(errorResId).into(imageView);
    }

    public static void showImage(Fragment context, ImageView imageView, String url, Integer errorResId){
        Glide.with(context).load(url).error(errorResId).into(imageView);
    }

    public static void showImage(Fragment context, ImageView imageView, Integer resId, Integer errorResId){
        Glide.with(context).load(resId).error(errorResId).into(imageView);
    }

    public static void showImageFromFilePath(Context context, ImageView imageView, String filePath, Integer errorResId){
        Glide.with(context).load(new File(filePath)).error(errorResId).into(imageView);
    }

    /**
     * 实现高斯模糊
     * @param context
     * @param imageView
     * @param url
     * @param radius 1-25 radius越大，模糊度越高
     */
    public static void showBlurTransformationImage(Context context, ImageView imageView, String url, Integer errorResId, int radius){
        Glide.with(context).load(url).error(errorResId).bitmapTransform(new BlurTransformation(context, radius)).into(imageView);
    }

    public static void showBlurTransformationImage(Context context, ImageView imageView, int resId, int errorResId, int radius){
        Glide.with(context).load(resId).error(errorResId).bitmapTransform(new BlurTransformation(context, radius)).into(imageView);
    }


}
