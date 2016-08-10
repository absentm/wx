/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dm.beans.resp;

/**
 * 文本消息
 *
 * @author DUAN
 */
public class TextMessage extends BaseMessage {

    private String Content;        // 消息内容

    public String getContent() {
        return Content;
    }

    public void setContent(String Content) {
        this.Content = Content;
    }

}
