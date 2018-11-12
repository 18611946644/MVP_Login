package com.bwie.lx06_07login.login.view;

import android.content.Context;

/**
 * date:2018/11/7
 * author:张自力(DELL)
 * function: IView 接口
 */

public interface IView<T> {

    //成功和失败的方法
    void success(T t);
    void failder(Exception e);

    //传递MainActivity  this
    Context isthis();

    /*//设置用来存储在Activity中，用户输入的用户名和密码
    void setUserName(int userName);
    void setUserPasswrod(String passwrod);

    //设置在P层用来得到用户输入的用户名和密码
    String getUserName();
    String getPasswrod();*/

}
