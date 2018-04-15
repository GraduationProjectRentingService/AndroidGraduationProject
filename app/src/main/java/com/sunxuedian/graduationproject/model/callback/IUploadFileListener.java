package com.sunxuedian.graduationproject.model.callback;

/**
 * 上传文件的回调
 * Created by SUN on 2017/4/27.
 */

public interface IUploadFileListener {
    void onSuccess(String fileUrl);//上传成功返回的文件路径
    void onProgress(Integer value);//上传的进度
    void onFailed(String msg);//上传失败
}
