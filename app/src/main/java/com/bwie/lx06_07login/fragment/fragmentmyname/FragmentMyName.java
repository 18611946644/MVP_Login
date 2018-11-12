package com.bwie.lx06_07login.fragment.fragmentmyname;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bwie.lx06_07login.R;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;

/**
 * date:2018/11/10
 * author:张自力(DELL)
 * function:
 *
 */

public class FragmentMyName extends Fragment implements View.OnClickListener {

    private ImageView mImgSaoYiSao;
    private EditText mEtContent;
    private Button mBtnImg;
    private Button mBtnLogoimg;
    private ImageView mMImgQR;

    private int REQUEST_CODE = 5;//二维码扫描
    private Bitmap mBitmap;
    private ImageView mImgBack;
    private Button mBtnBack;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         View view = inflater.inflate(R.layout.fragment_myname,null);
        //1. 初始化控件
        getinit(view);
        //2 点击事件监听
        setOnClickListener(view);
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return ;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    Toast.makeText(getActivity(), "解析结果:" + result, Toast.LENGTH_LONG).show();
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(getActivity(), "解析二维码失败", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    /**
     * //1. 初始化控件
     * */
    private void getinit(View view) {
        mImgBack = view.findViewById(R.id.img_back);//返回按钮
        mImgSaoYiSao = view.findViewById(R.id.img_saoyisao);//扫一扫
        //输入框
        mEtContent = view.findViewById(R.id.et_content);
        //生成普通二维码
        mBtnImg = view.findViewById(R.id.btn_img);
        //生成带logo的二维码
        mBtnLogoimg = view.findViewById(R.id.btn_logoimg);
        //生成的二维码
        mMImgQR = view.findViewById(R.id.img_qr);
        //生成普通二维码(还没有完成)
        mBtnBack = view.findViewById(R.id.btn_back);
    }

    /**
     * //2 点击事件监听
     * */
    private void setOnClickListener(View view) {
        mImgBack.setOnClickListener(this);
        mImgSaoYiSao.setOnClickListener(this);
        mBtnImg.setOnClickListener(this);
        mBtnLogoimg.setOnClickListener(this);
        mMImgQR.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.img_back://返回按钮
                getActivity().finish();
                break;
            case R.id.img_saoyisao://点击扫一扫
                Intent intent = new Intent(getActivity(), CaptureActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
                break;
            case R.id.btn_img://生成普通二维码
                String textContent2 = mEtContent.getText().toString();
                if (TextUtils.isEmpty(textContent2)) {
                    Toast.makeText(getActivity(), "您的输入为空!", Toast.LENGTH_SHORT).show();
                    return;
                }
                mEtContent.setText("");
                mBitmap = CodeUtils.createImage(textContent2, 400, 400, null);
                mMImgQR.setImageBitmap(mBitmap);
                break;
            case R.id.btn_logoimg://生成带logo的二维码
                String textContent = mEtContent.getText().toString();
                if (TextUtils.isEmpty(textContent)) {
                    Toast.makeText(getActivity(), "您的输入为空!", Toast.LENGTH_SHORT).show();
                    return;
                }
                mEtContent.setText("");
                mBitmap = CodeUtils.createImage(textContent, 400, 400, BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
                mMImgQR.setImageBitmap(mBitmap);
                break;
        }
    }
}
