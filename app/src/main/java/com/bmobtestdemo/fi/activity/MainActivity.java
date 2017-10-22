package com.bmobtestdemo.fi.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.bmobtestdemo.fi.R;
import com.bmobtestdemo.fi.bean.Person;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

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
            case R.id.btn_addData://添加一行数据
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
                final Person p3 = new Person();
                p3.setObjectId("012a5cef4b");
                p3.delete(new UpdateListener() {

                    @Override
                    public void done(BmobException e) {
                        if(e==null){
                            Toast.makeText(MainActivity.this,"删除成功:"+p3.getUpdatedAt(),Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(MainActivity.this,"删除失败：" + e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }

                });
                break;
            case R.id.btn_updateData:
                //更新Person表里面id为6b6c11c537的数据，address内容更新为“北京朝阳”
                final Person p2 = new Person();
                p2.setAddress("天庭");
                p2.update("012a5cef4b", new UpdateListener() {

                    @Override
                    public void done(BmobException e) {
                        if(e==null){
                            Toast.makeText(MainActivity.this,"更新成功："+p2.getUpdatedAt(),Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(MainActivity.this,"更新失败：" + e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }

                });
                break;
            case R.id.btn_queryData:
                //查找Person表里面id为6b6c11c537的数据
                BmobQuery<Person> bmobQuery = new BmobQuery<Person>();
                bmobQuery.getObject("012a5cef4b", new QueryListener<Person>() {
                @Override
                public void done(Person object,BmobException e) {
                    if(e==null){
                        Toast.makeText(MainActivity.this,"查询成功",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(MainActivity.this,"查询失败"+e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
            });
                break;
        }
    }
}
