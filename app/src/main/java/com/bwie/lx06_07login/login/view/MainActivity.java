package com.bwie.lx06_07login.login.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.bwie.lx06_07login.R;
import com.bwie.lx06_07login.bean.LoginBean;
import com.bwie.lx06_07login.home.HomeActivity;
import com.bwie.lx06_07login.login.presenter.LoginPresenter;

public class MainActivity extends AppCompatActivity implements IView<LoginBean>, View.OnClickListener {//实现IView接口

    //登录使用的用户名和密码
    //http://www.xieast.com/api/user/login.php?username=13800138000&password=123456
    private String url = "http://www.zhaoapi.cn/user/login";


    private EditText etUsername;
    private EditText etPassword;
    private Button btnLogin;
    private Button btnZhuce;
    private ViewFlipper mFilpper;
    private LoginPresenter mLoginPresenter;
    private String mUsername = null;
    private String mPassword = null;
    private CheckBox mCheckM;
    private CheckBox mCheckD;

    //定义一个选框是否被选中
    public boolean isCheckM=false;
    private SharedPreferences mSpM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //1 控件初始化
        getinit();
        //2 底部跑马灯设置
        setHorseRaceLamp();
        //3 设置点击事件监听
        setOnClickListener();

        //4 初始化Present层
        getPresenterInit();


        //测试Bugly错误
/*        int x=0;
        int y=10;
        int z = y/x;
        Toast.makeText(MainActivity.this,"测试Bugly,被除数不能为0"+z,Toast.LENGTH_SHORT).show();*/

    }

    /**
     * //4 初始化Present层
     * */
   private void getPresenterInit() {
       mLoginPresenter = new LoginPresenter();
       mLoginPresenter.attach(this);
    }

    /**
     * //2 底部跑马灯设置
     * */
    private void setHorseRaceLamp() {
        //1 需要创建跑马灯的效果布局  layout_custom.xml
        //2 动态添加
        for (int i = 1; i <= 3; i++) {
            if(i==1){
                View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.layout_custom,null);
                mFilpper.addView(view);
            }else if(i==2){
                View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.layout_custom2,null);
                mFilpper.addView(view);
            }else {
                View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.layout_custom3,null);
                mFilpper.addView(view);
            }
        }
    }

    /**
     * //3.2 点击事件
     * */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login:
                //1 判断记住是否被选中
                if(mCheckM.isChecked()){
                    isCheckM =true;
                    //第一次登录  如果设置为选中了  就让全局ischeckM设置为true
                    //3 从输入框中拿数据
                    mUsername = etUsername.getText().toString().trim();
                    mPassword = etPassword.getText().toString();
                    //2 最终判断非空
                    if(TextUtils.isEmpty(mUsername) || TextUtils.isEmpty(mPassword)){
                        Toast.makeText(MainActivity.this,"对不起！用户名或者密码不能为空！",Toast.LENGTH_SHORT).show();
                    }
                    //登录
                    mLoginPresenter.getPresenterData(url, mUsername, mPassword);//调用请求方法
                }

                break;

            case R.id.btn_zhuce:

                break;
        }
    }

    /**
     * //3 设置点击事件监听
     * */
    private void setOnClickListener() {
        btnLogin.setOnClickListener(this);
        btnZhuce.setOnClickListener(this);
    }

    /**
     * //1 控件初始化
     * */
    private void getinit() {
        mFilpper = findViewById(R.id.filpper);//跑马灯
        //输入框控件
        etUsername = findViewById(R.id.et_username);
        etPassword = findViewById(R.id.et_password);
        //登录注册按钮
        btnLogin = findViewById(R.id.btn_login);
        btnZhuce = findViewById(R.id.btn_zhuce);
        //记住密码   和  自动登录  （逻辑没写呢）
        mCheckM = findViewById(R.id.checkm);//记住密码
        mCheckD = findViewById(R.id.checkd);//自动登录


        //初始化记住密码的sp
        mSpM = getSharedPreferences("spM", MODE_PRIVATE);
        SharedPreferences.Editor edit = mSpM.edit();
        isCheckM = mSpM.getBoolean("ischeckm2", false);
        //首先 从 sp中的
        if(isCheckM){
            String ismobile = mSpM.getString("ismobile", "");
            String ispassword = mSpM.getString("ispassword", "");
            //2 并赋值给输入框
            etPassword.setText(ispassword);
            etUsername.setText(ismobile);
            mUsername = ismobile;
            mPassword = ispassword;
            //让选框为选中状态
            mCheckM.setChecked(true);
        }else{
            //清空sp 设置ischeck为未选中状态
            mCheckM.setChecked(false);
            edit.clear();
            edit.commit();
        }



    }


    /**
     * 实现接口后  实现的方法
     * */
    @Override
    public void success(LoginBean loginBean) {
        if(loginBean!=null){
            //得到code值
            String code = loginBean.getCode();
            if(code.equals("0")){//判断是否登录成功
                //0 代表登录成功  提示
                //进行跳转
                String mobile = loginBean.getData().getMobile();
                String password1 = loginBean.getData().getPassword();
                SharedPreferences spm =getSharedPreferences("spM", Context.MODE_PRIVATE);
                spm.edit().putString("ismobile",mobile)
                        .putString("ispassword",password1)
                        .putBoolean("ischeckm2",true)
                        .commit();
                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(intent);
                Toast.makeText(MainActivity.this,""+loginBean.getMsg(),Toast.LENGTH_SHORT).show();
            }else{
                //1 代表登录失敗  提示
                Toast.makeText(MainActivity.this,""+loginBean.getMsg(),Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void failder(Exception e) {
        //提示網絡請求失敗
        Toast.makeText(MainActivity.this,""+e,Toast.LENGTH_SHORT).show();
    }

    //使用sp的传递当前this的接口
    @Override
    public Context isthis() {
        return this;
    }


    //防止内存泄漏
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mLoginPresenter!=null){
            mLoginPresenter.datach();
        }
    }


}
