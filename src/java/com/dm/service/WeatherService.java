/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dm.service;

import com.dm.beans.resp.Article;
import static com.dm.util.MessageUtil.urlEncodeUTF8;
import com.dm.util.WeixinUtil;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author DUAN
 */
public class WeatherService {
    
    public static final Logger log = LoggerFactory.getLogger(WeatherService.class);

    /**
     * @param httpUrl :请求地址
     * @return 返回结果
     */
    public static String request(String httpUrl) {
        BufferedReader reader = null;
        String result = null;
        StringBuffer sbf = new StringBuffer();
        
        try {
            URL url = new URL(httpUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("apikey", WeixinUtil.WEATHER_API_KEY);    // 填入apikey到HTTP header
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
     * 查询天气信息，并包装成图文类型（Article）
     *
     * @param cityName 城市名称
     * @return Article
     */
    public static Article searchWeather(String cityName) {
        Article article = new Article();
        
        String requestUrl = WeixinUtil.WEATHER_URL.replace("{cityName}", urlEncodeUTF8(cityName));
        String resultStr = request(requestUrl);
        
        log.info("weatherStr:{}", resultStr);
        
        JSONObject jSONObject0 = JSONObject.fromObject(resultStr);
        JSONArray jSONArray = jSONObject0.getJSONArray("HeWeather data service 3.0");
        String firstResult = jSONArray.getString(0);
        JSONObject jSONObject1 = JSONObject.fromObject(firstResult);
        String cityNameStr = jSONObject1.getJSONObject("basic").getString("city");
        String statusStr = jSONObject1.getJSONObject("now").getJSONObject("cond").getString("txt");
        String tmpStr = jSONObject1.getJSONObject("now").getString("tmp");
        String windDirStr = jSONObject1.getJSONObject("now").getJSONObject("wind").getString("dir");
        String windsScStr = jSONObject1.getJSONObject("now").getJSONObject("wind").getString("sc");
        String windsSpdStr = jSONObject1.getJSONObject("now").getJSONObject("wind").getString("spd");
        String visStr = jSONObject1.getJSONObject("now").getString("vis");
        String humStr = jSONObject1.getJSONObject("now").getString("hum");
        String pcpnStr = jSONObject1.getJSONObject("now").getString("pcpn");
        
        String apiStr = jSONObject1.getJSONObject("aqi").getJSONObject("city").getString("aqi");
        String qltyStr = jSONObject1.getJSONObject("aqi").getJSONObject("city").getString("qlty");
//        String coStr = jSONObject1.getJSONObject("aqi").getJSONObject("city").getString("co");
//        String no2Str = jSONObject1.getJSONObject("aqi").getJSONObject("city").getString("no2");
//        String o3Str = jSONObject1.getJSONObject("aqi").getJSONObject("city").getString("o3");
        String pm25Str = jSONObject1.getJSONObject("aqi").getJSONObject("city").getString("pm25");
//        String so2Str = jSONObject1.getJSONObject("aqi").getJSONObject("city").getString("so2");

        String drsg = jSONObject1.getJSONObject("suggestion").getJSONObject("drsg").getString("txt");
        String flu = jSONObject1.getJSONObject("suggestion").getJSONObject("flu").getString("txt");
        String sport = jSONObject1.getJSONObject("suggestion").getJSONObject("sport").getString("txt");
        String cw = jSONObject1.getJSONObject("suggestion").getJSONObject("cw").getString("txt");
        String trav = jSONObject1.getJSONObject("suggestion").getJSONObject("trav").getString("txt");
        String uv = jSONObject1.getJSONObject("suggestion").getJSONObject("uv").getString("txt");
        
        JSONArray jSONArray1 = jSONObject1.getJSONArray("daily_forecast");
//        log.info("jSONArray1:{}", jSONArray1);

        StringBuffer sb = new StringBuffer();
        for (int i = 1; i <= 3; i++) {
            JSONObject jSONObject = (JSONObject) jSONArray1.get(i);
            String dateStr = jSONObject.getString("date");
            String weaStr1 = jSONObject.getJSONObject("cond").getString("txt_d");
            String weaStr2 = jSONObject.getJSONObject("cond").getString("txt_n");
            String minTmp = jSONObject.getJSONObject("tmp").getString("min");
            String maxTmp = jSONObject.getJSONObject("tmp").getString("max");
            String sc = jSONObject.getJSONObject("wind").getString("sc");
            String dir = jSONObject.getJSONObject("wind").getString("dir");
            
            sb.append(dateStr).append("  ").append(weaStr1).append("（白天） ").append(weaStr2).append("（夜间） ").append("  最低温").append(minTmp).append("℃").append("  最高温").append(maxTmp).append("℃").append("  ").append(sc).append("  ").append(dir).append("\n\n");
        }
        
        article.setTitle(cityNameStr + "天气预报");
        article.setDescription("实时天气状况：\n" + statusStr + "  " + tmpStr + "℃" + "  "
                + windDirStr + " " + windsScStr + "级" + "  " + "风速" + windsSpdStr + "km/h\n" + "能见度" + visStr + "km"
                + "  相对湿度" + humStr + "%  " + "降水量" + pcpnStr + "mm\n" + "空气质量"
                + qltyStr + "    质量指数" + apiStr + "  PM2.5  " + pm25Str + "(ug/m³)\n\n" + "未来三天预报：\n" + sb.toString()
                + "\n小M，特别提醒：\n" + "穿衣指数：" + drsg + "\n\n" + "感冒指数：" + flu + "\n\n"
                + "运动指数：" + sport + "\n\n" + "洗车指数：" + cw + "\n\n" + "旅游指数：" + trav + "\n\n"
                + "紫外线指数：" + uv + "\n\n");
        article.setUrl("");
        return article;
    }
    
}
