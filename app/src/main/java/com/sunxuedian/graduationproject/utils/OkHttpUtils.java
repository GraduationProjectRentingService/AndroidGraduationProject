package com.sunxuedian.graduationproject.utils;

import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;

import com.sunxuedian.graduationproject.bean.KeyValue;
import com.sunxuedian.graduationproject.bean.ResponseBean;
import com.sunxuedian.graduationproject.model.callback.IModelCallback;

import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by SUN on 2017/5/6.
 */
public class OkHttpUtils {

    private static MediaType TYPE_JSON = MediaType.parse("application/json; charset=utf-8");
    private static MediaType TYPE_ENCODED = MediaType.parse("application/x-www-form-urlencoded");

    private static OkHttpClient mClient = new OkHttpClient();
    private static final String Tag = "OkHttpUtils";
    /**
     * 异步post获取数据，回掉操作
     * @param url 访问的链接地址
     * @param jsonParam post参数
     * @param callback 回调
     */

    public static void enqueue(String url, String jsonParam, Callback callback){
        if (TextUtils.isEmpty(url)){
            return;
        }

        RequestBody requestBody = RequestBody.create(TYPE_JSON, jsonParam);
        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
        mClient.newCall(request).enqueue(callback);
    }

    public static void enqueue(String url, List<KeyValue> param, Callback callback){

        url = buildQueryUrl(url, param);
        Request request = new Request.Builder()
                .url(url)
                .build();
        mClient.newCall(request).enqueue(callback);
    }

    /**
     *  将键值对转码为 encoded格式
     * @param url
     * @param params
     * @return
     */
    public static String buildQueryUrl(String url, List<KeyValue> params) {
        StringBuilder queryBuilder = new StringBuilder(url);
        if (!url.contains("?")) {
            queryBuilder.append("?");
        } else if (!url.endsWith("?")) {
            queryBuilder.append("&");
        }
        List<KeyValue> queryParams = params;
        if (queryParams != null) {
            for (KeyValue kv : queryParams) {
                String name = kv.key;
                String value = kv.getValueStr();
                if (!TextUtils.isEmpty(name) && value != null) {
                    queryBuilder.append(Uri.encode(name, "utf-8")).append("=").append(Uri.encode(value, "utf-8")).append("&");
                }
            }
        }

        if (queryBuilder.charAt(queryBuilder.length() - 1) == '&') {
            queryBuilder.deleteCharAt(queryBuilder.length() - 1);
        }

        if (queryBuilder.charAt(queryBuilder.length() - 1) == '?') {
            queryBuilder.deleteCharAt(queryBuilder.length() - 1);
        }
        return queryBuilder.toString();
    }

    public static void enqueue(String url, String json, final IModelCallback listener, final OnSuccessCallBack onSuccessCallBack){
        enqueue(url, json, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                listener.onFailure("OkHttp--OnFailure()  Message: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()){
                    ResponseBean responseBean = JsonUtils.jsonStrToResponseBean(response.body().string());
                    onSuccessCallBack.onSuccess(responseBean);
                }else {
                    listener.onFailure("OkHttp--OnResponse()  Message: " + response.message());
                }
            }
        });
    }


    public static void enqueue(String url, Map<String, Object> params, IModelCallback listener, OnSuccessCallBack onSuccessCallBack){
        JSONObject json = new JSONObject(params);
        enqueue(url, json.toString(), listener, onSuccessCallBack);
    }

    public static void enqueueUrl(String url, Callback callback){
        if (TextUtils.isEmpty(url)){
            return;
        }

        OkHttpClient mClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        mClient.newCall(request).enqueue(callback);
    }

    public static void enqueueUrl(String url, final IModelCallback listener, final OnSuccessCallBack onSuccessCallBack){
        enqueueUrl(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                listener.onFailure("OkHttp--OnFailure()  Message: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()){
                    ResponseBean responseBean = JsonUtils.jsonStrToResponseBean(response.body().string());
                    onSuccessCallBack.onSuccess(responseBean);
                }else {
                    listener.onFailure("OkHttp--OnResponse()  Message: " + response.message());
                }
            }
        });
    }

    /**
     * 请求成功的接口
     */
    public interface OnSuccessCallBack{
        void onSuccess(ResponseBean responseBean);
    }

    /**
     * 在这里实现回调到主线程中
     */
    private static Handler handler = new Handler(Looper.getMainLooper());
    //失败的回调
    private static void postFailureOnUIThread(final IModelCallback listener, final String msg){
        handler.post(new Runnable() {
            @Override
            public void run() {
                listener.onFailure(msg);
            }
        });
    }
    //成功的回调
    private static void postSuccessOnUIThread(final OnSuccessCallBack onSuccessCallBack, final ResponseBean responseBean){
        handler.post(new Runnable() {
            @Override
            public void run() {
             onSuccessCallBack.onSuccess(responseBean);
            }
        });
    }
    //结果回调到主线程的方法
    public static void executeRequest(String url, Map<String, Object> params, final IModelCallback listener, final OnSuccessCallBack onSuccessCallBack){
        JSONObject json = new JSONObject(params);
        enqueue(url, json.toString(), new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                String msg = "OkHttp--OnFailure()  Message: " + e.getMessage();
                postFailureOnUIThread(listener, msg);
                Log.e(Tag, msg);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()){
                    ResponseBean responseBean = JsonUtils.jsonStrToResponseBean(response.body().string());
                    postSuccessOnUIThread(onSuccessCallBack, responseBean);
                    Log.i(Tag, responseBean.toString());
                }else {
                    String msg = "OkHttp--OnResponse()  Message: " + response.message();
                    postFailureOnUIThread(listener, msg);
                    Log.e(Tag, msg);
                }
            }
        });
    }
    
}
