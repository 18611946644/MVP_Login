package com.bwie.lx06_07login.fragment.fragmentmydata.presenter;

import com.bwie.lx06_07login.bean.MyDataBean;
import com.bwie.lx06_07login.fragment.fragmentmydata.view.IView;
import com.bwie.lx06_07login.item.ICallBack;
import com.bwie.lx06_07login.model.CModel;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

/**
 * date:2018/11/10
 * author:张自力(DELL)
 * function:
 */

public class MyDataPresenter {

    //1 需要初始化IView 层的对象  和  Model层的对象
    private IView mIView;
    private CModel mCModel;

    //2 创建一个方法让Iview和mCmodel 进行交互
    public void attach(IView mIView){
        this.mIView = mIView;
        mCModel = new CModel();
    }

    //4 定义一个方法  用来判断用户输入的密码和用户名  和  网路请求的用户名和密码是否相同
    public void getMyDataPresenter(String url){
        //2.2 定义一个Type的泛型
        Type type = new TypeToken<MyDataBean>(){}.getType();

        //2 通过M层对象,调用请求数据方法
        mCModel.getModelData(url, new ICallBack() {
            @Override
            public void OnSuccess(Object o) {
                mIView.success(o);
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
