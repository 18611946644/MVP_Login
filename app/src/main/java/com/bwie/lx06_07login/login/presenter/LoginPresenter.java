package com.bwie.lx06_07login.login.presenter;

import android.util.Log;

import com.bwie.lx06_07login.bean.LoginBean;
import com.bwie.lx06_07login.item.ICallBack;
import com.bwie.lx06_07login.model.CModel;
import com.bwie.lx06_07login.login.view.IView;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

/**
 * date:2018/11/7
 * author:张自力(DELL)
 * function: 登录使用的逻辑层  LoginPresenter
 */

public class LoginPresenter {
    private static final String TAG = "LoginPresenter";
    //1 需要初始化IView 层的对象  和  Model层的对象
    private IView mIView;
    private CModel mCModel;

    //2 创建一个方法让Iview和mCmodel 进行交互
    public void attach(IView mIView){
        this.mIView = mIView;
        mCModel = new CModel();
    }

    //4 定义一个方法  用来判断用户输入的密码和用户名  和  网路请求的用户名和密码是否相同
    public void getPresenterData(String url, String username, String password){

        //1.2 将得到的用户名和密码 进行拼接url
        url = url.concat("?mobile=").concat(username).concat("&password=").concat(password);
        //2.2 定义一个Type的泛型
        Type type = new TypeToken<LoginBean>(){}.getType();

        //2 通过M层对象,调用请求数据方法
        mCModel.getModelData(url, new ICallBack() {
            @Override
            public void OnSuccess(Object o) {
                Log.i(TAG, "OnSuccess: "+o);
                mIView.success(o);
                LoginBean loginBean = (LoginBean) o;
                //判断是否选择了记住密码
               /* if(ischeckm){//如果是
                    //就选择记住密码
                    //得到数据
                   *//*
                    String mobile = loginBean.getData().getMobile();
                    String password1 = loginBean.getData().getPassword();
                    SharedPreferences spm = mIView.isthis().getSharedPreferences("spM", Context.MODE_PRIVATE);
                    spm.edit().putString("ismobile",mobile)
                            .putString("ispassword",password1)
                            .putBoolean("ischeckm2",ischeckm)
                            .commit();*//*
                }*/

            }

            @Override
            public void OnFailder(Exception e) {
                 //通过IView对象  将失败的原因传过去
                mIView.failder(e);
            }
        }, type);


    }

    //3 解除IView 和  MCmodel层的关联
    public void datach(){
        if(mIView!=null){
            mIView=null;
        }
    }

}
