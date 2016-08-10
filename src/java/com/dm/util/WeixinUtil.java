/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dm.util;

import com.dm.beans.AccessToken;
import com.dm.beans.menus.Menu;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import net.sf.json.JSONException;

import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * 微信工具类
 *
 * @author DUAN
 */
public class WeixinUtil {

    private final static Logger log = LoggerFactory.getLogger(WeixinUtil.class);

    // 微信公众号wx项目后台ID和秘钥
    public final static String APPID = "wx164a763325456ff4";
    public final static String APPSECRET = "dc847e99119b75daba46b3f20889fed2";

    // 获取access_token的接口地址（GET） 限2000（次/天）  
    public final static String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

    // 菜单创建（POST） 限100（次/天）、 菜单查询（GET）
    public static String MENU_CREATE_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
    public static String MENU_QUERY_URL = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN";

    // 模板消息ID（POST）发送
    public static String TEMPLATE_MSG_ID = "7uJPGBIPvKxzod8UtWpo2rnp7UjoJU42AtjfE6vzXw8";
    public static String TEMPLATE_MSG_URL = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=ACCESS_TOKEN";

    // 获取累计用户数据（POST）
    public static String GET_USER_CUMULATE = "https://api.weixin.qq.com/datacube/getusercumulate?access_token=ACCESS_TOKEN";
    
    // 百度翻译URL（GET）、API_KEY
    public static String BAIDU_FANYI_URL = "http://openapi.baidu.com/public/2.0/bmt/translate?client_id={BaiDuFanyi_ApiKey}&q={keyWord}&from=auto&to=auto";
    public static String BAIDU_FANYI_API_KEY = "K88ZAOTEWz28lKgQDbeRcRYF";

    // Face++人脸识别请求地址、API_KEY、API_SECRET
    public static String FACE_DETECT_URL = "http://apicn.faceplusplus.com/v2/detection/detect?url=URL&api_secret=API_SECRET&api_key=API_KEY";
    public static String FACE_API_KEY = "3623df9c30d81d099e2036c4abb7c454";
    public static String FACE_API_SECRET = "zaOl9j5VtXqEGzfd07ynTbjGUPM53wLy";

    // 天气预报请求地址、API_KEY
    public static String WEATHER_URL = "http://apis.baidu.com/heweather/weather/free?city={cityName}";
    public static String WEATHER_API_KEY = "9bc54988bf1086d45f54cad7e82669d0";

    // 微信热门文章精选请求地址、API_KEY
    public static String HOT_ARTICLE_URL = "http://apis.baidu.com/txapi/weixin/wxhot?num=4&rand=1&page=1";
    public static String HOT_ARTICLE_API_KEY = "9bc54988bf1086d45f54cad7e82669d0";

    // 手机号归属地查询请求地址、API_KEY
    public static String PHONE_SEARCH_URL = "http://apis.baidu.com/showapi_open_bus/mobile/find?num={num}";
    public static String PHONE_SEARCH_API_KEY = "9bc54988bf1086d45f54cad7e82669d0";

    // 图灵机器人问答请求地址、API_KEY
    public static String TURING_ROBOT_URL = "http://apis.baidu.com/turing/turing/turing?key=879a6cb3afb84dbf4fc84a1df2ab7319&info={info}&userid=eb2edb736";
    public static String TURING_ROBOT_API_KEY = "9bc54988bf1086d45f54cad7e82669d0";

    // 公交查询请求地址、API_KEY
    public static String BUS_SEARCH_URL = "http://apis.baidu.com/apistore/bustransport/busstations?city={city}&station={station}";
    public static String BUS_API_KEY = "9bc54988bf1086d45f54cad7e82669d0";

    /**
     * 发起https请求并获取结果
     *
     * @param requestUrl 请求地址
     * @param requestMethod 请求方式（GET、POST）
     * @param outputStr 提交的数据
     * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
     */
    public static JSONObject httpRequest(String requestUrl, String requestMethod, String outputStr) {
        JSONObject jsonObject = null;
        StringBuffer buffer = new StringBuffer();
        try {
            // 创建SSLContext对象，并使用我们指定的信任管理器初始化  
            TrustManager[] tm = {new CommX509TrustManager()};
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(null, tm, new java.security.SecureRandom());
            // 从上述SSLContext对象中得到SSLSocketFactory对象  
            SSLSocketFactory ssf = sslContext.getSocketFactory();

            URL url = new URL(requestUrl);
            HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
            httpUrlConn.setSSLSocketFactory(ssf);

            httpUrlConn.setDoOutput(true);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);
            // 设置请求方式（GET/POST）  
            httpUrlConn.setRequestMethod(requestMethod);

            if ("GET".equalsIgnoreCase(requestMethod)) {
                httpUrlConn.connect();
            }

            // 当有数据需要提交时  
            if (null != outputStr) {
                OutputStream outputStream = httpUrlConn.getOutputStream();
                // 注意编码格式，防止中文乱码  
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }

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
            jsonObject = JSONObject.fromObject(buffer.toString());
        } catch (ConnectException ce) {
            log.error("Weixin server connection timed out.");
        } catch (Exception e) {
            log.error("https request error:{}", e);
        }

        return jsonObject;
    }

    /**
     * 获取access_token
     *
     * @param appid 凭证
     * @param appsecret 密钥
     * @return
     */
    public static AccessToken getAccessToken(String appid, String appsecret) {
        AccessToken accessToken = null;

        String requestUrl = ACCESS_TOKEN_URL.replace("APPID", appid).replace("APPSECRET", appsecret);
        JSONObject jsonObject = httpRequest(requestUrl, "GET", null);

        if (null != jsonObject) {       // 如果请求成功  
            try {
                accessToken = new AccessToken();
                accessToken.setToken(jsonObject.getString("access_token"));
                accessToken.setExpiresIn(jsonObject.getInt("expires_in"));
            } catch (JSONException e) {
                accessToken = null;
                // 获取token失败  
                log.error("获取token失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
            }
        }

        return accessToken;
    }

    /**
     * 创建菜单
     *
     * @param menu 菜单实例
     * @param accessToken 有效的access_token
     * @return 0表示成功，其他值表示失败
     */
    public static int createMenu(Menu menu, String accessToken) {
        int result = 0;

        // 拼装创建菜单的url  
        String url = MENU_CREATE_URL.replace("ACCESS_TOKEN", accessToken);
        // 将菜单对象转换成json字符串  
        String jsonMenu = JSONObject.fromObject(menu).toString();
        // 调用接口创建菜单  
        JSONObject jsonObject = httpRequest(url, "POST", jsonMenu);

        if (null != jsonObject) {
            if (0 != jsonObject.getInt("errcode")) {
                result = jsonObject.getInt("errcode");
                log.error("创建菜单失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
            }
        }

        return result;
    }

    /**
     * 菜单查询（GET）
     *
     * @param accessToken 微信验证票据
     * @return 有则，返回菜单信息相关的json字符串；无，则返回不含数据信息
     */
    public static String getMenuInfos(String accessToken) {
        Menu menu = null;
        String url = MENU_QUERY_URL.replace("ACCESS_TOKEN", accessToken);
        JSONObject jsonObject = httpRequest(url, "GET", null);

        if (jsonObject != null) {
            return jsonObject.toString();
        } else {
            log.info("NO Menu Info!");
            return "没有查询到相关信息";
        }
    }
}
