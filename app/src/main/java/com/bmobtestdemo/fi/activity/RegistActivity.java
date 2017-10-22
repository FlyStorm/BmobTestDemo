package com.bmobtestdemo.fi.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bmobtestdemo.fi.R;

public class RegistActivity extends Activity {
    private EditText et_name_regist,et_pwd_regist,et_email_regist,et_icon_regist;
    private ImageView iv_back;
    private Button btn_reg_regist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);

        //初始化控件
        initView();

        btn_reg_regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(RegistActivity.this, "注册", Toast.LENGTH_SHORT).show();
            }
        });
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        et_name_regist= (EditText) findViewById(R.id.et_name_regist);
        et_pwd_regist= (EditText) findViewById(R.id.et_pwd_regist);
        et_email_regist= (EditText) findViewById(R.id.et_email_regist);
        et_icon_regist= (EditText) findViewById(R.id.et_icon_regist);
        iv_back= (ImageView) findViewById(R.id.iv_back);
        btn_reg_regist= (Button) findViewById(R.id.btn_reg_regist);
    }
}
