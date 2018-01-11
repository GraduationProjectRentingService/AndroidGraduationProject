package com.sunxuedian.graduationproject;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void test(){
        int size = 13;
        int mod = (Integer.MAX_VALUE / 2) % size;
        d("最大整数的一半为：" + Integer.MAX_VALUE / 2);
        d("最大数的一半对" + size + "取余数为：" + mod);
        int result = ((Integer.MAX_VALUE / 2) - mod);
        d("最后得到的数为：" + result);
        d("得到的数对 " + size + "取余数得到：" + result % size);
    }

    void d(String msg){
        System.out.println(msg);
    }
}