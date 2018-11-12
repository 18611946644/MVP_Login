package com.bwie.lx06_07login.model;

import com.bwie.lx06_07login.item.ICallBack;
import com.bwie.lx06_07login.utils.OKHttpUtils;

import java.lang.reflect.Type;

/**
 * date:2018/11/7
 * author:张自力(DELL)
 * function:  请求网络数据层  Model
 */

public class CModel {

    //定义一个方法  用来请求网络数据
    public void getModelData(String url, ICallBack iCallBack, Type type){
        //使用网络请求工具类  进行请求数据
        OKHttpUtils.getInstance().getData(url,iCallBack,type);
    }

}
