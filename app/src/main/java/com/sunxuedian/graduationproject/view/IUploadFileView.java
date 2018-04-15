package com.sunxuedian.graduationproject.view;

/**
 * Created by SUN on 2017/4/27.
 */

public interface IUploadFileView {
    void onFileUploadSuccess(String fileUrl);//上传成功
    void onFileUploadFailed(String msg);//上传失败
    void showFileUploadProgress(Integer value);//上传的进度
}
