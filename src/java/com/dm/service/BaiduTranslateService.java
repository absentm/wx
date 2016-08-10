/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dm.service;

import com.dm.beans.baidufanyi.TranslateResult;
import static com.dm.util.MessageUtil.urlEncodeUTF8;
import com.dm.util.WeixinUtil;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 百度翻译业务实现
 *
 * @author DUAN
 */
public class BaiduTranslateService {

    private final static Logger log = LoggerFactory.getLogger(BaiduTranslateService.class);

    /**
     * 发起http请求获取返回结果
     *
     * @param requestUrl 请求地址
     * @return
     */
    public static String httpRequest(String requestUrl) {
        StringBuffer buffer = new StringBuffer();
        try {
            URL url = new URL(requestUrl);
            HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();

            httpUrlConn.setDoOutput(false);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);

            httpUrlConn.setRequestMethod("GET");
            httpUrlConn.connect();

            // 将返回的输入流转换成字符串  
            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();
            // 释放资源  
            inputStream.close();
            inputStream = null;
            httpUrlConn.disconnect();

        } catch (Exception e) {
        }
        return buffer.toString();
    }

    /**
     * 翻译（中->英 英->中 日->中 ）
     *
     * @param source
     * @return
     */
    public static String translate(String source) {
        String dst = null;
        String requestUrl = WeixinUtil.BAIDU_FANYI_URL;     // 组装查询地址  
        requestUrl = requestUrl.replace("{BaiDuFanyi_ApiKey}", WeixinUtil.BAIDU_FANYI_API_KEY)
                .replace("{keyWord}", urlEncodeUTF8(source));    // 对参数q的值进行urlEncode utf-8编码  

        // 查询并解析结果  
        try {
            String json = httpRequest(requestUrl);      // 查询并获取返回结果  
            // 通过Gson工具将json转换成TranslateResult对象  
            TranslateResult translateResult = new Gson().fromJson(json, TranslateResult.class);
            // 取出translateResult中的译文  
            dst = translateResult.getTrans_result().get(0).getDst();
            log.info("json: {}", json);
//            使用json-lib方式获取翻译结果，好处为不用严格的构造json所对应的java类对象；但没Gson使用的更简单直白
//            JSONObject jsonObject = JSONObject.fromObject(json);
//            log.info("jsonObject: {}", jsonObject);
//            JSONArray jsonArray = jsonObject.getJSONArray("trans_result");
//            log.info("jsonArray: {}", jsonArray);
//            String resultStr = jsonArray.get(0).toString();
//            log.info("resultStr: {}", resultStr);
//            JSONObject resultObject = JSONObject.fromObject(resultStr);
//            log.info("resultObject: {}", resultObject.get("dst"));
//            log.info("dst: {}", resultObject);
//            dst = jsonObject.getJSONArray("trans_result").get(0);

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (null == dst) {
            dst = "翻译系统异常，请稍候尝试！";
        }
        return dst;
    }
}
