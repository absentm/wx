/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dm.beans.templateMsg;

import com.dm.beans.templateMsg.basedata.Data;

/**
 * 微信发送模板消息模板类
 *
 * @author DUAN
 */
public class TemplateMsg {

    private String touser;          // 微信id
    private String template_id;     // 模板消息id
    private String url;             // 模板消息链接
    private String topcolor;        // 顶部颜色
    private Data data;              // 消息体数据

    public String getTouser() {
        return touser;
    }

    public void setTouser(String touser) {
        this.touser = touser;
    }

    public String getTemplate_id() {
        return template_id;
    }

    public void setTemplate_id(String template_id) {
        this.template_id = template_id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTopcolor() {
        return topcolor;
    }

    public void setTopcolor(String topcolor) {
        this.topcolor = topcolor;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }
}
