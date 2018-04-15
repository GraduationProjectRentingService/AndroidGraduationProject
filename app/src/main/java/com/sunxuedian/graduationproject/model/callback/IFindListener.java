package com.sunxuedian.graduationproject.model.callback;

import java.util.List;

/**
 * presenter的回调
 * Created by SUN on 2017/4/24.
 */

public interface IFindListener<T> {
    void onSuccess(List<T> list);
    void onFailed(String msg);
}
