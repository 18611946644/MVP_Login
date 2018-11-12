package com.bwie.lx06_07login.utils;

import android.os.Handler;
import android.util.Log;

import com.bwie.lx06_07login.bean.LoginBean;
import com.bwie.lx06_07login.item.ICallBack;
import com.google.gson.Gson;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * date:2018/11/7
 * author:张自力(DELL)
 * function:  网络请求工具类
 */

public class OKHttpUtils {

    private static final String TAG = "OKHttpUtils";

    /**
     * 1 使用单例模式，初始化对象
     * */
    //1.1 初始化一个静态方法
    private static volatile OKHttpUtils instance;

    private OkHttpClient mOkHttpClient;
    private Handler handler = new Handler();

    //1.2 定义一个无惨构造  私有化
    private OKHttpUtils() {
       //2.1 初始化OK
        mOkHttpClient = new OkHttpClient();
    }
    //1.3 定义一个  初始化对象的方法
    public static OKHttpUtils getInstance(){
        if(instance==null){
            //设置一个锁
            synchronized (OKHttpUtils.class){
                if(null==instance){
                    //就初始化一个对象
                    instance = new OKHttpUtils();
                }
            }
        }
        return instance;
    }

    /**
     * 2 设置一个请求数据方法
     * */
    public void getData(String url, final ICallBack iCallBack, final Type type){
        //2.2 创建一个请求方法
        final Request request = new Request.Builder()
                .get()
                .url(url)
                .build();
        //2.3 使用client  和 request  得到一个call
        Call call = mOkHttpClient.newCall(request);
        //2.4 使用call  调用一个方法
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                //网络访问失败  返回主线程原因  需要handler
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                       //使用ICallBack对象  进行返回失败原因
                        iCallBack.OnFailder(e);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                 //网络访问成功  将数据返回主线程中
                //得到数据  进行解析
                String result = response.body().string();
                 Gson gson = new Gson();
                final Object o = gson.fromJson(result, type);
                //将解析后的数据发送给主线程
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                       //使用ICallBack 的成功的方法
                        iCallBack.OnSuccess(o);
                    }
                });
            }
        });
    }

}
