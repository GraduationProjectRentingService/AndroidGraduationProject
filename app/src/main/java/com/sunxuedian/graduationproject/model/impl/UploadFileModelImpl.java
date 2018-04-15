package com.sunxuedian.graduationproject.model.impl;

import android.content.Context;
import android.util.Log;

import com.sunxuedian.graduationproject.bean.MyFile;
import com.sunxuedian.graduationproject.model.IUploadFileModel;
import com.sunxuedian.graduationproject.model.callback.IFindListener;
import com.sunxuedian.graduationproject.model.callback.IUploadFileListener;
import com.sunxuedian.graduationproject.utils.ResultCodeUtils;

import java.io.File;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * Created by SUN on 2017/4/27.
 */

public class UploadFileModelImpl implements IUploadFileModel {

    private String tag = "ModelFileManagerImpl";

    private Context mContext;

    public UploadFileModelImpl(Context context){
        this.mContext = context;
    }

    @Override
    public void uploadFile(String filePath, final IUploadFileListener listener) {

        File file = null;
        try{
            file = new File(filePath);
        }catch (Exception e){
            e.printStackTrace();
            listener.onFailed("上传的文件不存在！");
            return;
        }
        final BmobFile bmobFile = new BmobFile(file);
        bmobFile.uploadblock(mContext, new UploadFileListener() {
            @Override
            public void onSuccess() {
                MyFile myFile = new MyFile();
                myFile.setFile(bmobFile);
                myFile.setFileName(bmobFile.getFilename());
                myFile.setFileUrl(bmobFile.getFileUrl(mContext));
                myFile.setFileType(MyFile.FILE_TYPE_IMAGE);
                addFileToMyFile(myFile);
                listener.onSuccess(bmobFile.getFileUrl(mContext));
            }

            @Override
            public void onFailure(int i, String s) {
                String msg = "";
                switch (i){
                    case ResultCodeUtils.BMOB_UPLOAE_FILE_NO_EXIST:
                        msg = "上传的文件不存在！";
                        break;
                    case ResultCodeUtils.BMOB_UPLOAD_FILE_FAILURE:
                        msg = "上传文件失败！";
                        break;
                    case ResultCodeUtils.BMOB_UPLOAD_FILE_ERROR:
                        msg = "上传文件错误！";
                        break;
                    default:
                        msg = s;
                        break;
                }
                listener.onFailed(msg);
            }

            @Override
            public void onProgress(Integer value) {
                super.onProgress(value);
                listener.onProgress(value);
            }
        });
    }

    @Override
    public void searchFile(String fileName, final IFindListener listener) {

        BmobQuery<MyFile> query = new BmobQuery<>();
        query.addWhereEqualTo("fileName", fileName);
        query.findObjects(mContext, new FindListener<MyFile>() {
            @Override
            public void onSuccess(List<MyFile> list) {
                listener.onSuccess(list);
            }

            @Override
            public void onError(int i, String s) {
                listener.onFailed(s);
            }
        });
    }


    /**
     * 将文件的基本信息添加到数据表中
     * @param file
     */
    private void addFileToMyFile(MyFile file){
        file.save(mContext, new SaveListener() {
            @Override
            public void onSuccess() {
                Log.d(tag, "文件添加到表中成功！");
            }

            @Override
            public void onFailure(int i, String s) {
                Log.d(tag, "文件添加到表中失败！");
            }
        });
    }
}
