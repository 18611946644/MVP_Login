package com.bwie.lx06_07login.application;

import android.app.Application;

import com.tencent.bugly.crashreport.CrashReport;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

/**
 * date:2018/11/8
 * author:张自力(DELL)
 * function:
 */

public class MyApplication extends Application {


    //注  第二个参数是  创建项目申请的Appid
    //     别忘了在清单中进行注册
    @Override
    public void onCreate() {
        super.onCreate();
        ZXingLibrary.initDisplayOpinion(this);
        CrashReport.initCrashReport(getApplicationContext(), "0ba18e04b9", true);
    }
}
