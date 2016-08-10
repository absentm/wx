/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dm.beans;

/**
 *
 * @author DUAN
 */
public class BaseMessage {

    private String ToUserName;      // 开发者微信号
    private String FromUserName;    // 发送方账号（关注该公共号用户openID）
    private long CreateTime;        // 消息创建时间
    private String MsgType;         // 消息类型
    private long MsgId;             // 消息id, 64位整型

    public String getToUserName() {
        return ToUserName;
    }

    public void setToUserName(String ToUserName) {
        this.ToUserName = ToUserName;
    }

    public String getFromUserName() {
        return FromUserName;
    }

    public void setFromUserName(String FromUserName) {
        this.FromUserName = FromUserName;
    }

    public long getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(long CreateTime) {
        this.CreateTime = CreateTime;
    }

    public String getMsgType() {
        return MsgType;
    }

    public void setMsgType(String MsgType) {
        this.MsgType = MsgType;
    }

    public long getMsgId() {
        return MsgId;
    }

    public void setMsgId(long MsgId) {
        this.MsgId = MsgId;
    }

}
