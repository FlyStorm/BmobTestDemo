package com.bmobtestdemo.fi;

import android.app.Activity;
import android.os.Bundle;

import cn.bmob.v3.Bmob;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //第一：默认初始化
        Bmob.initialize(this, "771c17303ade44118e1bb1ee51dec111");
    }
}
