package com.bmobtestdemo.fi.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bmobtestdemo.fi.R;
import com.bmobtestdemo.fi.bean.Person;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class MainActivity extends Activity implements View.OnClickListener {

    private Button mBtn_add;
    private Button mBtn_del;
    private Button mBtn_update;
    private Button mBtn_query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //第一：默认初始化
        Bmob.initialize(this, "771c17303ade44118e1bb1ee51dec111");

        //初始化控件
        initView();
        initListener();

    }

    private void initListener() {
        mBtn_add.setOnClickListener(this);
        mBtn_del.setOnClickListener(this);
        mBtn_update.setOnClickListener(this);
        mBtn_query.setOnClickListener(this);
    }

    private void initView() {
        mBtn_add = (Button) findViewById(R.id.btn_addData);
        mBtn_del = (Button) findViewById(R.id.btn_delData);
        mBtn_update = (Button) findViewById(R.id.btn_updateData);
        mBtn_query = (Button) findViewById(R.id.btn_queryData);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_addData:
                Person p1=new Person();
                p1.setName("孙悟空");
                p1.setAddress("花果山");
                p1.save(new SaveListener<String>() {
                    @Override
                    public void done(String objectId,BmobException e) {
                        if(e==null){
                            Toast.makeText(MainActivity.this,"添加数据成功，返回objectId为：" + objectId,Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(MainActivity.this, "创建数据失败：" + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
            case R.id.btn_delData:
                break;
            case R.id.btn_updateData:
                break;
            case R.id.btn_queryData:
                break;
        }
    }
}
