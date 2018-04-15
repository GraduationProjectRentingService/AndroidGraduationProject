package com.sunxuedian.graduationproject.model;

import com.sunxuedian.graduationproject.model.callback.IFindListener;
import com.sunxuedian.graduationproject.model.callback.IUploadFileListener;

/**
 * 处理图片的网络处理
 * Created by SUN on 2017/4/27.
 */

public interface IUploadFileModel {
    void uploadFile(String filePath, IUploadFileListener listener);//上传文件
    void searchFile(String fileName, IFindListener listener);//查找文件是否存在
}
