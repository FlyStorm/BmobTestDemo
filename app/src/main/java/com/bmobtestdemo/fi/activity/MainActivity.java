package com.bmobtestdemo.fi.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bmobtestdemo.fi.R;
import com.bmobtestdemo.fi.bean.Person;
import com.bmobtestdemo.fi.bean.User;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.DownloadFileListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class MainActivity extends Activity implements View.OnClickListener {

    private Button mBtn_add;
    private Button mBtn_del;
    private Button mBtn_update;
    private Button mBtn_query;

    //图像对象
    private ImageView mImageView;
    //显示用户信息控件
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //第一：默认初始化
//        Bmob.initialize(this, "771c17303ade44118e1bb1ee51dec111");

        //初始化控件
        initView();
        initListener();
        initData();
    }

    private void initData() {
        //获取传递过来的数据,BmobObject实现了序列化接口
        Intent intent=getIntent();
        User user= (User) intent.getSerializableExtra("user");
        mTextView.setText(user.getUsername()+","+user.getEmail());

        //这里需要拿到用户信息的一个缓存，否则图片下载失败，无法显示
        user= BmobUser.getCurrentUser(User.class);
        //获取上传的图像
        BmobFile bmobFile=user.getIcon();
        Log.e("bmob", "bmobFile=" + String.valueOf(bmobFile));
        bmobFile.download(new DownloadFileListener() {
            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void done(String savePath, BmobException e) {
                if(e==null){
//                    Toast.makeText(MainActivity.this,"下载成功,保存路径:"+savePath,Toast.LENGTH_SHORT).show();
                    Log.e("bmob", "MainActivity:下载成功，保存路径savePath==" + savePath);
                    //显示图片
                    Bitmap bitmap = BitmapFactory.decodeFile(savePath);
                    mImageView.setImageBitmap(bitmap);
                }else{
//                    Toast.makeText(MainActivity.this,"下载失败："+e.getErrorCode()+","+e.getMessage(),Toast.LENGTH_SHORT).show();
                    Log.e("bmob", "MainActivity:下载失败：" + e.getErrorCode() + "," + e.getMessage());
                }
            }

            @Override
            public void onProgress(Integer value, long newworkSpeed) {
                Log.e("bmob", "下载进度：" + value + "," + newworkSpeed);
            }
        });
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

        mImageView= (ImageView) findViewById(R.id.iv_icon_main);
        mTextView= (TextView) findViewById(R.id.tv_user_main);
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
