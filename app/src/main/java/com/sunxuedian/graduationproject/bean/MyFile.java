package com.sunxuedian.graduationproject.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 *  文件对应的数据库表
 * Created by SUN on 2017/4/27.
 */
public class MyFile extends BmobObject {

    public static final String FILE_TYPE_IMAGE = "image";
    public static final String FILE_TYPE_WORD = "word";
    public static final String FILE_TYPE_NORMAL = "normal";

    private BmobFile file;
    private String fileUrl;
    private String fileName;
    private String fileType = FILE_TYPE_NORMAL;

    public BmobFile getFile() {
        return file;
    }

    public void setFile(BmobFile file) {
        this.file = file;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

}
