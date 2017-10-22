package com.bmobtestdemo.fi.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bmobtestdemo.fi.R;
import com.bmobtestdemo.fi.bean.User;

import java.io.File;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;

public class RegistActivity extends Activity {
    private EditText et_name_regist, et_pwd_regist, et_email_regist, et_icon_regist;
    private ImageView iv_back;
    private Button    btn_reg_regist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);

        //初始化控件
        initView();

        //注册按钮的点击监听
        btn_reg_regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //                Toast.makeText(RegistActivity.this, "注册", Toast.LENGTH_SHORT).show();
                //拿到输入框的值
                final String name = et_name_regist.getText().toString();
                final String pwd = et_pwd_regist.getText().toString();
                final String email = et_email_regist.getText().toString();
//                String icon = et_icon_regist.getText().toString();


                //获取图片文件的路径(这里写死)
                String path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/22.jpg";
                Log.e("TAG", "path==" + path);
                //图片的上传
                final BmobFile bmobFile = new BmobFile(new File(path));
                bmobFile.upload(new UploadFileListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            Toast.makeText(RegistActivity.this, "上传成功", Toast.LENGTH_SHORT).show();
                            //上传成功，用户的信息要存到用户的表里面
                            User user = new User();
                            user.setUsername(name);
                            user.setPassword(pwd);
                            user.setEmail(email);
                            user.setIcon(bmobFile);

                            //注册
                            user.signUp(new SaveListener<User>() {
                                @Override
                                public void done(User o, BmobException e) {
                                    if (e == null) {
                                        Toast.makeText(RegistActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(RegistActivity.this, "注册失败"+e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                        } else {
                            Toast.makeText(RegistActivity.this, "上传失败" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        //返回按钮的点击监听
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        et_name_regist = (EditText) findViewById(R.id.et_name_regist);
        et_pwd_regist = (EditText) findViewById(R.id.et_pwd_regist);
        et_email_regist = (EditText) findViewById(R.id.et_email_regist);
        et_icon_regist = (EditText) findViewById(R.id.et_icon_regist);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        btn_reg_regist = (Button) findViewById(R.id.btn_reg_regist);
    }
}
