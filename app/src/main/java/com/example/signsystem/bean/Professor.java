package com.example.signsystem.bean;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;

public class Professor extends BmobObject implements Serializable {
    private String pNum;
    private String name;
    private String college;
    private String pwd;

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getpNum() {
        return pNum;
    }

    public void setpNum(String pNum) {
        this.pNum = pNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }
}
