/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dm.beans.menus;

/**
 * 普通按钮（子菜单）
 *
 * @author DUAN
 */
public class CommonButton extends Button {

    private String type;    // 类型
    private String key;     // 菜单KEY值，用于消息接口推送，不超过128字节

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
