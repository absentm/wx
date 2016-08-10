/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dm.beans.resp;

/**
 * 音乐消息
 *
 * @author DUAN
 */
public class MusicMessage extends BaseMessage {

    private Music Music;

    public Music getMusic() {
        return Music;
    }

    public void setMusic(Music Music) {
        this.Music = Music;
    }

}
