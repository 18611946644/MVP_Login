package com.bwie.lx06_07login.fragment.fragmentmydata.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bwie.juan_mao.xlistview.XListView;
import com.bwie.lx06_07login.R;
import com.bwie.lx06_07login.bean.MyDataBean;
import com.bwie.lx06_07login.fragment.fragmentmydata.adapter.MyDataAdapter;
import com.bwie.lx06_07login.fragment.fragmentmydata.presenter.MyDataPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * date:2018/11/10
 * author:张自力(DELL)
 * function:
 */

public class FragmentMyData extends Fragment implements IView<MyDataBean> {

    private String url = "http://www.xieast.com/api/news/news.php";

    private RecyclerView mRecyclerView;
    private XListView mXList;
    private List<MyDataBean.DataBean> mList;
    private MyDataAdapter mMyDataAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         View view = inflater.inflate(R.layout.fragment_mydata,null);
         //1 初始化
        getinit(view);
        //2 设置XList
        setOnXListView(view);
        return view;
    }

    /**
     * //2 设置XList
     * */
    private void setOnXListView(View view) {
        //定义一个集合
        mList = new ArrayList<>();
        //初始化一个适配器
        mMyDataAdapter = new MyDataAdapter(getActivity(), mList);
        //为list设置
        mXList.setAdapter(mMyDataAdapter);
    }

    /**
     * //1 初始化控件
     * */
    private void getinit(View view) {
        /*mRecyclerView = view.findViewById(R.id.recyclerview);*/
        mXList = view.findViewById(R.id.xlist);
        //设置可上拉下拉
        mXList.setPullRefreshEnable(true);
        mXList.setPullLoadEnable(true);

        //初始化p层对象
        MyDataPresenter myDataPresenter = new MyDataPresenter();
        myDataPresenter.attach(this);
        myDataPresenter.getMyDataPresenter(url);
    }

    /**
     * 成功  得到数据
     * */
    @Override
    public void success(MyDataBean myDataBean) {
        //判断非空
        if(myDataBean!=null){
            List<MyDataBean.DataBean> data = myDataBean.getData();
            if(data!=null){
                mList.clear();
                mList.addAll(data);
                mMyDataAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void failder(Exception e) {
        Toast.makeText(getActivity(),""+e,Toast.LENGTH_SHORT).show();
    }
}
