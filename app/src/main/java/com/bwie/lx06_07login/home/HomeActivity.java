package com.bwie.lx06_07login.home;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bwie.lx06_07login.R;
import com.bwie.lx06_07login.fragment.fragmentmydata.view.FragmentMyData;
import com.bwie.lx06_07login.fragment.fragmentmyname.FragmentMyName;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewPager mVpFragment;
    private TextView mTxtMyData;
    private TextView mTxtMyName;
    private List<Fragment> mFragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);
        //1 初始化控件
        getinit();
        //2 设置VPFragment前的准备__创建list  和  Fragment
        setVPFragmentQ();
        //3 设置VPFragment
        setVPFragmentStart();
        //4 设置点击事件监听
        setOnClickListener();

    }

    /**
     * 5 底部按钮 点击事件
     * */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.txt_mydata://我的数据
                mVpFragment.setCurrentItem(0);
                setColorChanger(0);
                break;

            case R.id.txt_myname://我的名片
                mVpFragment.setCurrentItem(1);
                setColorChanger(1);
                break;
        }
    }

    /**
     * //4 设置点击事件监听
     * */
    private void setOnClickListener() {
        //点击底部我的数据  和  我的名片的点击
        mTxtMyData.setOnClickListener(this);
        mTxtMyName.setOnClickListener(this);
    }

    /**
     * //3 设置VPFragment
     * */
    private void setVPFragmentStart() {
        //3.1 为vp设置适配器
       mVpFragment.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
           @Override
           public Fragment getItem(int position) {
               return mFragmentList.get(position);
           }

           @Override
           public int getCount() {
               return mFragmentList.size();
           }
       });

       //3.2 为vp设置改变事件
       mVpFragment.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
           @Override
           public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

           }

           @Override
           public void onPageSelected(int position) {
               //3.2.1 创建一个当底部按钮改变时,按钮底部背景色同步改变的方法
               setColorChanger(position);
           }

           @Override
           public void onPageScrollStateChanged(int state) {

           }
       });
    }



    /**
     * //2 设置VPFragment前的准备
     *      创建list  和  Fragment
     * */
    private void setVPFragmentQ() {
        //定义一个存放Fragment的
        mFragmentList = new ArrayList<>();
        //初始化Fragment
        FragmentMyData fragmentMyData = new FragmentMyData();
        FragmentMyName fragmentMyName = new FragmentMyName();
        //将fragment加入到list
        mFragmentList.add(fragmentMyData);
        mFragmentList.add(fragmentMyName);
    }

    /**
     * //1 初始化控件
     * */
    private void getinit() {
        mVpFragment = findViewById(R.id.vp_fragment);//vp
        mTxtMyData = findViewById(R.id.txt_mydata);//我的数据
        mTxtMyName = findViewById(R.id.txt_myname);//我的名片

        //设置底部按钮默认背景色
        mTxtMyData.setBackgroundColor(Color.GRAY);
        mTxtMyName.setBackgroundColor(Color.WHITE);
    }


    /**
     * 3.2.1 创建一个当底部按钮改变时,按钮底部背景色同步改变的方法
     * */
    private void setColorChanger(int position) {
        mTxtMyData.setBackgroundColor(position==0? Color.GRAY:Color.WHITE);
        mTxtMyName.setBackgroundColor(position==1? Color.GRAY:Color.WHITE);
    }
}
