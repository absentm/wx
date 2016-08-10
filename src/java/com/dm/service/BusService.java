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
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import net.sf.json.JSONObject;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 公交查询
 *
 * @author DUAN
 */
public class BusService {

    private final static Logger log = LoggerFactory.getLogger(BusService.class);

    /**
     * 请求地址
     *
     * @return 返回结果
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
            connection.setRequestProperty("apikey", WeixinUtil.BUS_API_KEY);
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
     * 返回格式化后的公交信息
     *
     * @param url
     * @return
     */
    public static String getBusInfo(String url) {
        String result = "";
        String respStr = request(url);
        JSONObject jSONObject = JSONObject.fromObject(respStr);
        log.info("jSONObject:{}", jSONObject);

        int errNum = jSONObject.getInt("errNum");
        if (errNum == 0) {
            String xmlStr = jSONObject.getJSONObject("retData").getString("result");
            log.info("xmlStr:{}", xmlStr);

            Map map = xmlString2Map(xmlStr);
            result = (String) map.get("line_names");
        } else {
            result = "对不起，您的输入有误！ " + jSONObject.getString("retMsg");
        }

        return result;
    }

    /**
     * 将xml样式的字符串解析为map对象
     *
     * @param xml
     * @return
     */
    private static Map xmlString2Map(String xml) {
        Map map = new HashMap();
        Document doc = null;
        try {
            // 将字符串转为XML  
            doc = DocumentHelper.parseText(xml);
            // 获取根节点  
            Element rootElt = doc.getRootElement();

            // 获取根节点下的子节点stats 
            Iterator iter = rootElt.elementIterator("stats");
            // 遍历stats节点  
            while (iter.hasNext()) {
                Element recordEle = (Element) iter.next();
                // 获取子节点stats下的子节点stat
                Iterator iters = recordEle.elementIterator("stat");
                // 遍历Header节点下的Response节点  
                while (iters.hasNext()) {
                    Element itemEle = (Element) iters.next();
                    // 拿到stat下的子节点line_names下的字节点的值  
                    String line_names = itemEle.elementTextTrim("line_names");
                    log.info("line_names:{}", line_names);

                    map.put("line_names", line_names);
                    break;
                }
            }
        } catch (DocumentException e) {
            log.info("e:{}", e);
        } catch (Exception e) {
            log.info("e:{}", e);
        }
        return map;
    }

    /**
     * 获取格式化（图文）公交信息
     *
     * @param busInfoString
     * @return
     */
    public static Article getBusArticleInfo(String busInfoString) {
        Article article = new Article();
        article.setTitle("附近公交车详情");
        article.setDescription(busInfoString.replace(";", "\n"));
        article.setUrl("");

        return article;
    }

}
