/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dm.beans.resp;

/**
 * 消息基类：公众号 -> 普通用户
 *
 * @author DUAN
 */
public class BaseMessage {

    private String ToUserName;      // 接收方账号（关注该公共号用户openID）
    private String FromUserName;    // 开发者微信号
    private long CreateTime;        // 消息创建时间
    private String MsgType;         // 消息类型
    private int FuncFlag;           // 位0x0001被标志时，星标刚收到的消息 

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

    public int getFuncFlag() {
        return FuncFlag;
    }

    public void setFuncFlag(int FuncFlag) {
        this.FuncFlag = FuncFlag;
    }

}
