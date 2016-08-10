/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dm.service;

import com.dm.util.WeixinUtil;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 周边搜索
 *
 * @author DUAN
 */
public class PhoneService {

    private final static Logger log = LoggerFactory.getLogger(PhoneService.class);

    /**
     * 手机号归属地查询服务
     *
     * @param httpUrl
     * @return
     */
    public static String request(String httpUrl) {
        BufferedReader reader = null;
        String result = null;
        StringBuffer sbf = new StringBuffer();

        try {
            URL url = new URL(httpUrl);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setRequestMethod("GET");
            // 填入apikey到HTTP header
            connection.setRequestProperty("apikey", WeixinUtil.PHONE_SEARCH_API_KEY);
            connection.connect();
            InputStream is = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sbf.append(strRead);
                sbf.append("\r\n");
            }
            reader.close();
            result = sbf.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 生成返回的手机号信息
     *
     * @param url
     * @return
     */
    public static String getPhoneInfo(String url) {
        String result = "";
        String jsonString = request(url);
        JSONObject jSONObject = JSONObject.fromObject(jsonString);
        log.info("jSONObject:{}", jSONObject);

        int ret_code = jSONObject.getJSONObject("showapi_res_body").getInt("ret_code");

        if (ret_code == 0) {
            String proviceStr = jSONObject.getJSONObject("showapi_res_body").getString("prov");
            String cityName = jSONObject.getJSONObject("showapi_res_body").getString("city");
            String phoneType = jSONObject.getJSONObject("showapi_res_body").getString("name");

            result = proviceStr + cityName + phoneType;
        } else if (ret_code == -1) {
            result = jSONObject.getJSONObject("showapi_res_body").getString("error_info");
        } else {
            result = "不好意思咦，没有查询到相关信息啊！";
        }

        return result;
    }

}
