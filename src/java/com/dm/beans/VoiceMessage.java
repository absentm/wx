/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dm.beans;

/**
 * 语音消息
 *
 * @author DUAN
 */
public class VoiceMessage {

    private String MediaId;     // 语音消息媒体id，可以调用多媒体文件下载接口拉去数据
    private String Format;      // 语音格式, 如amr、speex等

    public String getMediaId() {
        return MediaId;
    }

    public void setMediaId(String MediaId) {
        this.MediaId = MediaId;
    }

    public String getFormat() {
        return Format;
    }

    public void setFormat(String Format) {
        this.Format = Format;
    }

}
