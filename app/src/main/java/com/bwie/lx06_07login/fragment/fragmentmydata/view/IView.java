package com.bwie.lx06_07login.fragment.fragmentmydata.view;

/**
 * date:2018/11/10
 * author:张自力(DELL)
 * function:
 */

public interface IView<T> {

    void success(T t);
    void failder(Exception e);

}
