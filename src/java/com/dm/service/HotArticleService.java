/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dm.service;

import com.dm.beans.resp.Article;
import com.dm.util.WeixinUtil;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 热门文章精选
 *
 * @author DUAN
 */
public class HotArticleService {

    private final static Logger log = LoggerFactory.getLogger(HotArticleService.class);

    /**
     * 请求数据
     *
     * @param httpUrl 请求地址
     * @return
     */
    public static String request(String httpUrl) {
        BufferedReader reader = null;
        String result = null;
        StringBuffer sbf = new StringBuffer();

        try {
            URL url = new URL(httpUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            // 填入apikey到HTTP header
            connection.setRequestProperty("apikey", WeixinUtil.HOT_ARTICLE_API_KEY);
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
            log.info("e:{}", e);
        }

        return result;
    }

    /**
     * 生成对应的文章列表
     *
     * @return
     */
    public static List<Article> searchHotArticles() {
        List<Article> articleList = null;
        String resultStr = request(WeixinUtil.HOT_ARTICLE_URL);
        log.info("resultStr: {}", resultStr);

        Article article1 = new Article();
        Article article2 = new Article();
        Article article3 = new Article();
        Article article4 = new Article();

        JSONObject jSONObject = JSONObject.fromObject(resultStr);
        article1.setTitle(jSONObject.getJSONObject("0").getString("title"));
        article1.setDescription(jSONObject.getJSONObject("0").getString("description"));
        article1.setPicUrl(jSONObject.getJSONObject("0").getString("picUrl"));
        article1.setUrl(jSONObject.getJSONObject("0").getString("url"));

        article2.setTitle(jSONObject.getJSONObject("1").getString("title"));
        article2.setDescription(jSONObject.getJSONObject("1").getString("description"));
        article2.setPicUrl(jSONObject.getJSONObject("1").getString("picUrl"));
        article2.setUrl(jSONObject.getJSONObject("1").getString("url"));

        article3.setTitle(jSONObject.getJSONObject("2").getString("title"));
        article3.setDescription(jSONObject.getJSONObject("2").getString("description"));
        article3.setPicUrl(jSONObject.getJSONObject("2").getString("picUrl"));
        article3.setUrl(jSONObject.getJSONObject("2").getString("url"));

        article4.setTitle(jSONObject.getJSONObject("3").getString("title"));
        article4.setDescription(jSONObject.getJSONObject("3").getString("description"));
        article4.setPicUrl(jSONObject.getJSONObject("3").getString("picUrl"));
        article4.setUrl(jSONObject.getJSONObject("3").getString("url"));

        articleList = new ArrayList<>();
        articleList.add(article1);
        articleList.add(article2);
        articleList.add(article3);
        articleList.add(article4);

        return articleList;
    }

}
