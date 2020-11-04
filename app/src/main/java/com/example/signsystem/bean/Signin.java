package com.example.signsystem.bean;

import cn.bmob.v3.BmobObject;

public class Signin extends BmobObject {
    private String classNum;
    private String stuNum;
    private String signed;
    private String singTime;

    public String getClassNum() {
        return classNum;
    }

    public void setClassNum(String classNum) {
        this.classNum = classNum;
    }

    public String getStuNum() {
        return stuNum;
    }

    public void setStuNum(String stuNum) {
        this.stuNum = stuNum;
    }

    public String getSigned() {
        return signed;
    }

    public void setSigned(String signed) {
        this.signed = signed;
    }

    public String getSingTime() {
        return singTime;
    }

    public void setSingTime(String singTime) {
        this.singTime = singTime;
    }
}
