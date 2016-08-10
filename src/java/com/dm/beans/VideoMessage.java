/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dm.beans;

/**
 * 视频消息
 *
 * @author DUAN
 */
public class VideoMessage {

    private String MediaId;             // 视频消息媒体id，可以调用多媒体文件下载接口拉去数据
    private String ThumbMediaId;        // 视频消息缩略图的媒体id，可以调用多媒体文件下载接口拉去数据

    public String getMediaId() {
        return MediaId;
    }

    public void setMediaId(String MediaId) {
        this.MediaId = MediaId;
    }

    public String getThumbMediaId() {
        return ThumbMediaId;
    }

    public void setThumbMediaId(String ThumbMediaId) {
        this.ThumbMediaId = ThumbMediaId;
    }

}
