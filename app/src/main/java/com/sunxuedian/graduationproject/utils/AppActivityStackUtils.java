package com.sunxuedian.graduationproject.utils;

import android.app.Activity;


import java.util.LinkedList;

/**
 * Created by sunxuedian on 2017/7/11.
 */

public class AppActivityStackUtils {

    private static LinkedList<Activity> activities = new LinkedList<>();

    public static void popActivity(){
        getActivity();
    }

    public static void popAndFinishActivity(){
        try {
            getActivity().finish();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static Activity getActivity(){
        if (activities.size() > 0){
            return activities.pop();
        }else {
            return null;
        }
    }

    public static void pushActivity(Activity e){
        if (e != null){
            activities.push(e);
        }
    }

    public static void clear(){
        while (!activities.isEmpty()){
            popAndFinishActivity();
        }
    }

    public static int size(){
        return activities.size();
    }

}
