/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dm.httpUtil;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * http 工具类
 *
 * @author DUAN
 */
public class HttpRequest {

    private final static Logger log = LoggerFactory.getLogger(HttpRequest.class);

    /**
     * 发送http请求取得返回的输入流
     *
     * @param requestUrl 请求地址
     * @return InputStream
     */
    public static InputStream httpRequestStream(String requestUrl) {
        InputStream inputStream = null;
        try {
            URL url = new URL(requestUrl);
            java.net.HttpURLConnection httpUrlConn = (java.net.HttpURLConnection) url.openConnection();
            httpUrlConn.setDoInput(true);
            httpUrlConn.setRequestMethod("GET");
            httpUrlConn.connect();
            // 获得返回的输入流  
            inputStream = httpUrlConn.getInputStream();
            httpUrlConn.disconnect();
        } catch (Exception e) {
            log.info("e:{}", e);
        }
        return inputStream;
    }

    /**
     * 发起http请求获取返回结果 GET
     *
     * @param requestUrl 请求地址
     * @return
     */
    public static String httpRequest(String requestUrl) {
        StringBuffer buffer = new StringBuffer();
        try {
            // 将返回的输入流转换成字符串  
            InputStream inputStream = httpRequestStream(requestUrl);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }

            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();        // 释放资源  
            inputStream = null;
        } catch (Exception e) {
            log.info("e:{}", e);
        }

        return buffer.toString();
    }

}
