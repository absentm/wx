/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dm.beans.baidufanyi;

/**
 *
 * 百度翻译结果
 *
 * @author DUAN
 */
public class ResultPair {

    private String src;     // 原文  
    private String dst;     // 译文  

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getDst() {
        return dst;
    }

    public void setDst(String dst) {
        this.dst = dst;
    }
}
