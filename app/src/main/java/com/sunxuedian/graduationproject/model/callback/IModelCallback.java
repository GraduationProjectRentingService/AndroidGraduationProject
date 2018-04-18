package com.sunxuedian.graduationproject.model.callback;

/**
 * Created by sunxuedian on 2018/3/14.
 */

public interface IModelCallback<T> {
    void onSuccess(T data);
    void onFailure(String msg);
    void onResultCode(String code);
}
