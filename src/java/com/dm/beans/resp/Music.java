/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dm.beans.resp;

/**
 *
 * @author DUAN
 */
public class Music {

    private String Title;   // 音乐名称  
    private String Description;     // 音乐描述  
    private String MusicUrl;    // 音乐链接  
    private String HQMusicUrl;      // 高质量音乐链接，WIFI环境优先使用该链接播放音乐

    public String getTitle() {
        return Title;
    }

    public void setTitle(String Title) {
        this.Title = Title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public String getMusicUrl() {
        return MusicUrl;
    }

    public void setMusicUrl(String MusicUrl) {
        this.MusicUrl = MusicUrl;
    }

    public String getHQMusicUrl() {
        return HQMusicUrl;
    }

    public void setHQMusicUrl(String HQMusicUrl) {
        this.HQMusicUrl = HQMusicUrl;
    }

}
