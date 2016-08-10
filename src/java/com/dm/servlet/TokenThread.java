/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dm.servlet;

import com.dm.beans.AccessToken;
import com.dm.util.WeixinUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 定时获取微信access_token的线程
 *
 * @author DUAN
 */
public class TokenThread implements Runnable {

    private static Logger log = LoggerFactory.getLogger(TokenThread.class);
    
    public static String appid = "";        // 第三方用户唯一凭证  
    public static String appsecret = "";    // 第三方用户唯一凭证密钥  
    public static AccessToken accessToken = null;

    public void run() {
        while (true) {
            try {
                accessToken = WeixinUtil.getAccessToken(appid, appsecret);
                if (null != accessToken) {
                    log.info("获取access_token成功，有效时长{}秒 token:{}", accessToken.getExpiresIn(), accessToken.getToken());
                    Thread.sleep((accessToken.getExpiresIn() - 200) * 1000);    // 休眠7000秒  
                } else {
                    log.info("accessToken为空，休息60秒后，重新获取！");
                    Thread.sleep(60 * 1000);    // 如果access_token为null，60秒后再获取  
                }
            } catch (InterruptedException e) {
                try {
                    Thread.sleep(60 * 1000);
                } catch (InterruptedException e1) {
                    log.error("{}", e1);
                }
                log.error("{}", e);
            }
        }
    }
}
