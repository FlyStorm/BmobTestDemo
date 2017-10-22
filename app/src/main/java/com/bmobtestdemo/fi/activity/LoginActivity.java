package com.bmobtestdemo.fi.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bmobtestdemo.fi.R;
import com.bmobtestdemo.fi.bean.User;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DownloadFileListener;
import cn.bmob.v3.listener.SaveListener;

public class LoginActivity extends Activity implements View.OnClickListener {

    private EditText mEt_name;
    private EditText mEt_pwd;
    private Button   mBtn_login;
    private Button   mBtn_regist;
    private BmobUser mBmobUser;
    private User     mUser;

    private ImageView mIv_photo;
    private String    mName;
    private String    mPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //第一：默认初始化
        Bmob.initialize(this, "771c17303ade44118e1bb1ee51dec111");

        initView();

        initListener();

        initData();
    }

    private void initData() {

    }

    private void initListener() {
        mBtn_login.setOnClickListener(this);
        mBtn_regist.setOnClickListener(this);
    }

    private void initView() {
        mEt_name = (EditText) findViewById(R.id.et_name);
        mEt_pwd = (EditText) findViewById(R.id.et_pwd);
        mBtn_login = (Button) findViewById(R.id.btn_login);
        mBtn_regist = (Button) findViewById(R.id.btn_regist);
        mIv_photo = (ImageView) findViewById(R.id.iv_photo);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:

                mName = mEt_name.getText().toString();
                mPwd = mEt_pwd.getText().toString();
                /*User user=new User();
                user.setUsername(name);
                user.setPassword(pwd);*/
                mBmobUser = new BmobUser();
                mBmobUser.setUsername(mName);
                mBmobUser.setPassword(mPwd);

                mBmobUser.login(new SaveListener<BmobUser>() {
                    @Override
                    public void done(BmobUser bmobUser1, BmobException e) {
                        if (e == null) {
                            //拿到用户信息的一个缓存
                            mUser = BmobUser.getCurrentUser(User.class);
                            //获取上传的图像
                            BmobFile bmobFile = mUser.getIcon();
                            Log.e("bmob", "bmobFile=" + String.valueOf(bmobFile));
                            //下载文件
                            bmobFile.download(new DownloadFileListener() {
                                @Override
                                public void onStart() {
                                    super.onStart();
                                }

                                @Override
                                public void done(String savePath, BmobException e) {
                                    if (e == null) {
//                                        Toast.makeText(LoginActivity.this, "下载成功,保存路径:" + savePath, Toast.LENGTH_SHORT).show();
                                        Log.e("bmob", "LoginActivity:下载成功，保存路径savePath==" + savePath);
                                        //显示图片
                                        Bitmap bitmap = BitmapFactory.decodeFile(savePath);
                                        mIv_photo.setImageBitmap(bitmap);
                                    } else {
//                                        Toast.makeText(LoginActivity.this, "下载失败：" + e.getErrorCode() + "," + e.getMessage(), Toast.LENGTH_SHORT).show();
                                        Log.e("bmob", "LoginActivity：下载失败==" + e.getErrorCode() + "," + e.getMessage());
                                    }
                                }

                                @Override
                                public void onProgress(Integer value, long newworkSpeed) {
                                    Log.e("bmob", "下载进度：" + value + "," + newworkSpeed);
                                }
                            });


                            //拿到用户登录信息,把登录信息传递给登录页面
                            /*StringBuffer userMessage=new StringBuffer();
                            userMessage.append("userName="+user.getUsername());
                            userMessage.append(",userEmail="+user.getEmail());*/
                            Toast.makeText(LoginActivity.this, "登录成功!", Toast.LENGTH_SHORT).show();
                            //登录成功，跳转到主页面
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.putExtra("user", mUser);
                            startActivity(intent);
                        } else {
                            //登录失败
                            Toast.makeText(LoginActivity.this, "帐号或者密码不正确，请重新输入！" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
            case R.id.btn_regist:
                //跳转到注册页面
                Intent intent = new Intent(this, RegistActivity.class);
                startActivity(intent);
                break;
        }
    }
}
