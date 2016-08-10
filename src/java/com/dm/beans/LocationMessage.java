/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dm.beans;

/**
 * 地理位置消息
 *
 * @author DUAN
 */
public class LocationMessage {

    private String Location_X;      // 地理位置维度
    private String Location_Y;      // 地理位置经度
    private String Scale;           // 地图缩放大小
    private String Label;           // 地理位置信息

    public String getLocation_X() {
        return Location_X;
    }

    public void setLocation_X(String Location_X) {
        this.Location_X = Location_X;
    }

    public String getLocation_Y() {
        return Location_Y;
    }

    public void setLocation_Y(String Location_Y) {
        this.Location_Y = Location_Y;
    }

    public String getScale() {
        return Scale;
    }

    public void setScale(String Scale) {
        this.Scale = Scale;
    }

    public String getLabel() {
        return Label;
    }

    public void setLabel(String Label) {
        this.Label = Label;
    }

}
