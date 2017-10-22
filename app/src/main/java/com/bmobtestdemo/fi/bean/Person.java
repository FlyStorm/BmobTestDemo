package com.bmobtestdemo.fi.bean;


import cn.bmob.v3.BmobObject;

/**
 * 创建者     yangyanfei
 * 创建时间   2017/10/22 上午 01:11
 * 作用	      创建Person类的javabean,对应Bmob后台的数据表
 * <p/>
 * 版本       $$Rev$$
 * 更新者     $$Author$$
 * 更新时间   $$Date$$
 * 更新描述   ${TODO}
 */
public class Person extends BmobObject {
    private String name;
    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Person{" +
                "address='" + address + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
