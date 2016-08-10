/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dm.beans;

/**
 * 图片消息
 *
 * @author DUAN
 */
public class ImageMessage {

    private String PicUrl;      // 图片链接
    private String MediaId;     // 图片消息媒体id，可以调用多媒体文件下载接口拉去数据

    public String getPicUrl() {
        return PicUrl;
    }

    public void setPicUrl(String PicUrl) {
        this.PicUrl = PicUrl;
    }

    public String getMediaId() {
        return MediaId;
    }

    public void setMediaId(String MediaId) {
        this.MediaId = MediaId;
    }

}
