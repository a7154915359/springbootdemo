package com.xiaowei.springbootdemo.pro.pojo;

import com.xiaowei.springbootdemo.pro.entity.User;

public class LoginInfo extends User {
    private String cToken;
    private String captcha;

    public String getcToken() {
        return cToken;
    }

    public void setcToken(String cToken) {
        this.cToken = cToken;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }
}
