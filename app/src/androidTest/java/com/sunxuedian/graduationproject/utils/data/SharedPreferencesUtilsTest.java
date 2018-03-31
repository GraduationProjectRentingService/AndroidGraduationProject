package com.sunxuedian.graduationproject.utils.data;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import com.sunxuedian.graduationproject.utils.LoggerFactory;
import com.sunxuedian.graduationproject.utils.MyLog;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by sunxuedian on 2018/3/23.
 */
public class SharedPreferencesUtilsTest {

    MyLog log = LoggerFactory.getLogger(getClass());
    Context appContext = InstrumentationRegistry.getTargetContext();

    @Test
    public void saveString() throws Exception {
        log.d("saveString: " + SharedPreferencesUtils.saveString(appContext, "testStr", "text"));
        log.d("getString: " + SharedPreferencesUtils.readString(appContext, "testStr", ""));
    }

    @Test
    public void saveBoolean() throws Exception {
        log.d("saveBoolean: " + SharedPreferencesUtils.saveBoolean(appContext, "testBool", true));
        log.d("getBoolean: " + SharedPreferencesUtils.readBoolean(appContext, "testBool", false));
    }

    @Test
    public void saveInt() throws Exception {
        log.d("saveInt: " + SharedPreferencesUtils.saveInt(appContext, "testInt", 123));
        log.d("getInt: " + SharedPreferencesUtils.readInt(appContext, "testInt", 0));
    }

    @Test
    public void saveFloat() throws Exception {
        log.d("saveFloat: " + SharedPreferencesUtils.saveFloat(appContext, "testFloat", 123.f));
        log.d("getFloat: " + SharedPreferencesUtils.readFloat(appContext, "testFloat", 0));
    }

    @Test
    public void saveLong() throws Exception {
        log.d("saveLong: " + SharedPreferencesUtils.saveLong(appContext, "testLong", 123l));
        log.d("getLong: " + SharedPreferencesUtils.readLong(appContext, "testLong", 0));
    }

    @Test
    public void readString() throws Exception {

    }

    @Test
    public void readBoolean() throws Exception {

    }

    @Test
    public void readInt() throws Exception {

    }

    @Test
    public void readLong() throws Exception {

    }

    @Test
    public void readFloat() throws Exception {

    }

}