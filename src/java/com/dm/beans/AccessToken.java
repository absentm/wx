package com.dm.beans;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * ACCESS_TOKEN 微信通用接口凭证
 *
 * @author DUAN
 */
public class AccessToken {

    private String token;           // 获得的凭证
    private int expiresIn;          // 超时时间

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }

}
