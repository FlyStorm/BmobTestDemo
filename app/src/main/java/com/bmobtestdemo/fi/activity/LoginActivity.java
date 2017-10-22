package com.bmobtestdemo.fi.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bmobtestdemo.fi.R;
import com.bmobtestdemo.fi.bean.User;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class LoginActivity extends Activity implements View.OnClickListener {

    private EditText mEt_name;
    private EditText mEt_pwd;
    private Button   mBtn_login;
    private Button mBtn_regist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //第一：默认初始化
        Bmob.initialize(this, "771c17303ade44118e1bb1ee51dec111");

        initView();

        initListener();
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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login:
                String name=mEt_name.getText().toString();
                String pwd=mEt_pwd.getText().toString();

                User user=new User();
                user.setUsername(name);
                user.setPassword(pwd);
                user.login(new SaveListener<User>() {
                    @Override
                    public void done(User user, BmobException e) {
                        if (e==null){
                            //登录成功，跳转到主页面
                            Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                            startActivity(intent);
                        }else {
                            //登录失败
                            Toast.makeText(LoginActivity.this,"帐号或者密码不正确，请重新输入！",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
            case R.id.btn_regist:
                //跳转到注册页面
                Intent intent=new Intent(this,RegistActivity.class);
                startActivity(intent);
                break;
        }
    }
}
