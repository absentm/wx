/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dm.servlet;

import com.dm.beans.AccessToken;
import static com.dm.beans.menus.MenuManager.getMenu;
import com.dm.beans.resp.Article;
import com.dm.beans.resp.Music;
import com.dm.beans.resp.MusicMessage;
import com.dm.beans.resp.NewsMessage;
import com.dm.beans.resp.TextMessage;
import com.dm.beans.templateMsg.TemplateMsg;
import com.dm.beans.templateMsg.detaildata.DetailDataUtil;
import com.dm.service.PhoneService;
import com.dm.service.BaiduMusicService;
import com.dm.service.BaiduTranslateService;
import com.dm.service.BusService;
import com.dm.service.FaceService;
import com.dm.service.HotArticleService;
import com.dm.service.TodayInHistoryService;
import com.dm.service.TuringRobotService;
import com.dm.service.WeatherService;
import com.dm.util.CheckUtil;
import com.dm.util.MessageUtil;
import static com.dm.util.MessageUtil.urlEncodeUTF8;
import com.dm.util.WeixinUtil;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * 微信Token验证类
 *
 * @author DUAN
 */
public class WeixinServlet extends HttpServlet {

    private static final Logger log = LoggerFactory.getLogger(WeixinServlet.class);
    private static final long serialVersionUID = 1L;

    /**
     * 微信公众号开发后台配置验证
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String signature = req.getParameter("signature");
        String timestamp = req.getParameter("timestamp");
        String nonce = req.getParameter("nonce");
        String echostr = req.getParameter("echostr");

        PrintWriter outWriter = resp.getWriter();
        if (CheckUtil.checkSignature(signature, timestamp, nonce)) {
            outWriter.print(echostr);
        }
    }

    /**
     * 消息的接受与响应
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter outWriter = resp.getWriter();

        try {
            // 获取用户的请求的基本信息
            Map<String, String> map = MessageUtil.xml2Map(req);
            String toUserName = map.get("ToUserName");
            final String fromUserName = map.get("FromUserName");
            String msgType = map.get("MsgType");
            String content = map.get("Content");

            // 纯文本类型消息
            TextMessage text = new TextMessage();
            text.setFromUserName(toUserName);
            text.setToUserName(fromUserName);
            text.setCreateTime(new Date().getTime());
            text.setFuncFlag(0);

            // 创建图文消息  
            NewsMessage newsMessage = new NewsMessage();
            newsMessage.setToUserName(fromUserName);
            newsMessage.setFromUserName(toUserName);
            newsMessage.setCreateTime(new Date().getTime());
            newsMessage.setMsgType(MessageUtil.MESSAGE_NEWS);
            newsMessage.setFuncFlag(0);

            List<Article> articleList = new ArrayList<>();

            String message = null;      // 根据消息的不同类型生成不同的消息，进行微信公众号消息转化的临时变量
            if (MessageUtil.MESSAGE_TEXT.equals(msgType)) {
                text.setMsgType("text");

                if ("?".equals(content.trim()) || "？".equals(content.trim())) {  // 主菜单显示
                    text.setContent(MessageUtil.getMainMenu());
                    message = MessageUtil.textMsg2Xml(text);
                } else if ("A".equals(content.trim())) {   // 自我介绍
                    String selfString = "本微信公众号旨在开发测试使用，更多乐趣与功能请尽请期待......";
                    text.setContent(selfString);
                    message = MessageUtil.textMsg2Xml(text);
                } else if ("B".equals(content.trim())) {   // 图文体验
                    String selfString = MessageUtil.getArticleHelp();
                    text.setContent(selfString);
                    message = MessageUtil.textMsg2Xml(text);
                } else if ("C".equals(content.trim())) {   // 自我介绍
                    String selfString = MessageUtil.getFunctonHelp();
                    text.setContent(selfString);
                    message = MessageUtil.textMsg2Xml(text);
                } else if ("a".equals(content.trim())) {   // 单图文含照片
                    Article article = new Article();
                    article.setTitle("微信公众帐号开发教程Java版");
                    article.setDescription("zim，80后，微信公众帐号开发经验4个月。为帮助初学者入门，特推出此系列教程，也希望借此机会认识更多同行！");
                    article.setPicUrl("http://0.xiaoqrobot.duapp.com/images/avatar_liufeng.jpg");
                    article.setUrl("http://blog.csdn.net/lyq8479");
                    articleList.add(article);

                    newsMessage.setArticleCount(articleList.size());    // 设置图文消息个数  
                    newsMessage.setArticles(articleList);       // 设置图文消息包含的图文集合  
                    message = MessageUtil.newsMessageToXml(newsMessage);       // 将图文消息对象转换成xml字符串  
                } else if ("b".equals(content.trim())) {       // 单图文无照片
                    Article article = new Article();
                    article.setTitle("微信公众帐号开发教程Java版");
                    // 图文消息中可以使用QQ表情、符号表情  
                    article.setDescription("zim，80后，" + MessageUtil.emoji(0x1F6B9)
                            + "，微信公众帐号开发经验4个月。为帮助初学者入门，特推出此系列连载教程，也希望借此机会认识更多同行！\n\n目前已推出教程共12篇，包括接口配置、消息封装、框架搭建、QQ表情发送、符号表情发送等。\n\n后期还计划推出一些实用功能的开发讲解，例如：天气预报、周边搜索、聊天功能等。");
                    // 将图片置为空  
                    article.setPicUrl("");
                    article.setUrl("http://blog.csdn.net/lyq8479");
                    articleList.add(article);
                    newsMessage.setArticleCount(articleList.size());
                    newsMessage.setArticles(articleList);
                    message = MessageUtil.newsMessageToXml(newsMessage);
                } else if ("c".equals(content.trim())) {      // 多图文第一条含大图
                    Article article1 = new Article();
                    article1.setTitle("微信公众帐号开发教程\n引言");
                    article1.setDescription("");
                    article1.setPicUrl("http://0.xiaoqrobot.duapp.com/images/avatar_liufeng.jpg");
                    article1.setUrl("http://blog.csdn.net/lyq8479/article/details/8937622");

                    Article article2 = new Article();
                    article2.setTitle("第2篇\n微信公众帐号的类型");
                    article2.setDescription("");
                    article2.setPicUrl("http://avatar.csdn.net/1/4/A/1_lyq8479.jpg");
                    article2.setUrl("http://blog.csdn.net/lyq8479/article/details/8941577");

                    Article article3 = new Article();
                    article3.setTitle("第3篇\n开发模式启用及接口配置");
                    article3.setDescription("");
                    article3.setPicUrl("http://avatar.csdn.net/1/4/A/1_lyq8479.jpg");
                    article3.setUrl("http://blog.csdn.net/lyq8479/article/details/8944988");

                    articleList.add(article1);
                    articleList.add(article2);
                    articleList.add(article3);
                    newsMessage.setArticleCount(articleList.size());
                    newsMessage.setArticles(articleList);
                    message = MessageUtil.newsMessageToXml(newsMessage);
                } else if ("d".equals(content.trim())) {       // 多图文无大图
                    Article article1 = new Article();
                    article1.setTitle("微信公众帐号开发教程Java版");
                    article1.setDescription("");
                    // 将图片置为空  
                    article1.setPicUrl("");
                    article1.setUrl("http://blog.csdn.net/lyq8479");

                    Article article2 = new Article();
                    article2.setTitle("第4篇\n消息及消息处理工具的封装");
                    article2.setDescription("");
                    article2.setPicUrl("http://avatar.csdn.net/1/4/A/1_lyq8479.jpg");
                    article2.setUrl("http://blog.csdn.net/lyq8479/article/details/8949088");

                    Article article3 = new Article();
                    article3.setTitle("第5篇\n各种消息的接收与响应");
                    article3.setDescription("");
                    article3.setPicUrl("http://avatar.csdn.net/1/4/A/1_lyq8479.jpg");
                    article3.setUrl("http://blog.csdn.net/lyq8479/article/details/8952173");

                    Article article4 = new Article();
                    article4.setTitle("第6篇\n文本消息的内容长度限制揭秘");
                    article4.setDescription("");
                    article4.setPicUrl("http://avatar.csdn.net/1/4/A/1_lyq8479.jpg");
                    article4.setUrl("http://blog.csdn.net/lyq8479/article/details/8967824");

                    articleList.add(article1);
                    articleList.add(article2);
                    articleList.add(article3);
                    articleList.add(article4);
                    newsMessage.setArticleCount(articleList.size());
                    newsMessage.setArticles(articleList);
                    message = MessageUtil.newsMessageToXml(newsMessage);
                } else if ("e".equals(content.trim())) {       // 多图文最后一条无图
                    Article article1 = new Article();
                    article1.setTitle("第7篇\n文本消息中换行符的使用");
                    article1.setDescription("");
                    article1.setPicUrl("http://0.xiaoqrobot.duapp.com/images/avatar_liufeng.jpg");
                    article1.setUrl("http://blog.csdn.net/lyq8479/article/details/9141467");

                    Article article2 = new Article();
                    article2.setTitle("第8篇\n文本消息中使用网页超链接");
                    article2.setDescription("");
                    article2.setPicUrl("http://avatar.csdn.net/1/4/A/1_lyq8479.jpg");
                    article2.setUrl("http://blog.csdn.net/lyq8479/article/details/9157455");

                    Article article3 = new Article();
                    article3.setTitle("如果觉得文章对你有所帮助，请通过博客留言或关注微信公众帐号xiaoqrobot来支持zim！");
                    article3.setDescription("");
                    // 将图片置为空  
                    article3.setPicUrl("");
                    article3.setUrl("http://blog.csdn.net/lyq8479");

                    articleList.add(article1);
                    articleList.add(article2);
                    articleList.add(article3);
                    newsMessage.setArticleCount(articleList.size());
                    newsMessage.setArticles(articleList);
                    message = MessageUtil.newsMessageToXml(newsMessage);
                } else if ("f".equals(content.trim())) {
                    // 调用接口获取access_token  
                    AccessToken at = TokenThread.accessToken;

                    if (null != at) {
                        int result = WeixinUtil.createMenu(getMenu(), at.getToken());   // 调用接口创建菜单  
                        if (0 == result) {      // 判断菜单创建结果  
                            text.setContent("菜单创建成功，请先取消关注，再次关注后查看效果");
                            message = MessageUtil.textMsg2Xml(text);
                        } else {
                            text.setContent("菜单创建失败，" + result);
                            message = MessageUtil.textMsg2Xml(text);
                        }
                    }
                } else if ("g".equals(content.trim())) {
                    // 调用接口获取access_token  
                    AccessToken at = TokenThread.accessToken;

                    log.info("menu time: {}", at.getExpiresIn());
                    log.info("menu token: {}", at.getToken());

                    if (null != at) {
                        String result = WeixinUtil.getMenuInfos(at.getToken());   // 调用接口查询菜单信息
                        text.setContent(result);
                        message = MessageUtil.textMsg2Xml(text);
                    } else {
                        log.info("AccessToken in Menu error");
                    }
                } else if ("h".equals(content.trim())) {
                    Runnable runnable = new Runnable() {
                        public void run() {
                            // task to run goes here  
                            AccessToken at = TokenThread.accessToken;   // 调用接口获取access_token  
                            String requestUrl = WeixinUtil.TEMPLATE_MSG_URL.replace("ACCESS_TOKEN", at.getToken());

                            log.info("access_token:{}", at.getToken());
                            // 构造完整模板消息
                            TemplateMsg templateMsg = new TemplateMsg();
                            templateMsg.setTouser(fromUserName);
                            templateMsg.setTemplate_id(WeixinUtil.TEMPLATE_MSG_ID);
                            templateMsg.setUrl("http://125.46.83.214/jcb/tj.html");
                            templateMsg.setTopcolor("#FF0000");
                            templateMsg.setData(DetailDataUtil.getVehCheckData());

                            // 发送消息的正文json数据
                            String jsonVehData = JSONObject.fromObject(templateMsg).toString();

                            if (null != at) {
                                JSONObject jSONObject = WeixinUtil.httpRequest(requestUrl, "POST", jsonVehData);
                                if (jSONObject != null) {
                                    if (0 != jSONObject.getInt("errcode")) {
                                        log.error("模板消息错误提示！errcode: {}, errMsg: {}", jSONObject.getInt("errcode"), jSONObject.getString("errmsg"));
                                    } else {
                                        log.info("模板消息发送结果为：{}, 时间：{}", jSONObject.getString("errmsg"), new Date());
                                    }
                                }
                            }
                        }
                    };

                    ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();
                    // 第二个参数为首次执行的延时时间，第三个参数为定时执行的间隔时间, 第四个参数指定之前的时间的单位 
                    service.scheduleAtFixedRate(runnable, 10, 86400, TimeUnit.SECONDS);

                    text.setContent(new Date() + ": 今日推送已完成！");
                    message = MessageUtil.textMsg2Xml(text);
                } else if ("1".equals(content.trim())) {   // 天气预报
                    String selfString = MessageUtil.getWeatherHelpMenu();
                    text.setContent(selfString);
                    message = MessageUtil.textMsg2Xml(text);
                } else if ("2".equals(content.trim())) {   // 公交查询
                    String selfString = MessageUtil.getBusHelpMenu();
                    text.setContent(selfString);
                    message = MessageUtil.textMsg2Xml(text);
                } else if ("3".equals(content.trim())) {   // 小译在线
                    String selfString = MessageUtil.getTranslateHelpMenu();
                    text.setContent(selfString);
                    message = MessageUtil.textMsg2Xml(text);
                } else if ("4".equals(content.trim())) {   // 手机号查询
                    String selfString = MessageUtil.getPhoneHelpMenu();
                    text.setContent(selfString);
                    message = MessageUtil.textMsg2Xml(text);
                } else if ("5".equals(content.trim())) {   // 人脸识别
                    String selfString = MessageUtil.getFaceHelpMenu();
                    text.setContent(selfString);
                    message = MessageUtil.textMsg2Xml(text);
                } else if ("6".equals(content.trim())) {   // 爱音乐
                    String selfString = MessageUtil.getMusicHelpMenu();
                    text.setContent(selfString);
                    message = MessageUtil.textMsg2Xml(text);
                } else if ("7".equals(content.trim())) {   // 你聊我说
                    String selfString = MessageUtil.getChatHelpMenu();
                    text.setContent(selfString);
                    message = MessageUtil.textMsg2Xml(text);
                } else if (content.trim().startsWith("歌曲")) {
                    // 将歌曲2个字及歌曲后面的+、空格、-等特殊符号去掉  
                    String keyWord = content.replaceAll("^歌曲[\\+ ~!@#%^-_=]?", "");

                    if ("".equals(keyWord)) {       // 如果歌曲名称为空  
                        content = MessageUtil.getMusicHelpMenu();
                        text.setContent(content);
                        message = MessageUtil.textMsg2Xml(text);
                    } else {
                        String[] kwArr = keyWord.split("@");
                        // 歌曲名称  
                        String musicTitle = kwArr[0];
                        // 演唱者默认为空  
                        String musicAuthor = "";
                        if (2 == kwArr.length) {
                            musicAuthor = kwArr[1];
                        }

                        // 搜索音乐  
                        Music music = BaiduMusicService.searchMusic(musicTitle, musicAuthor);
                        // 未搜索到音乐  
                        if (null == music) {
                            content = "对不起，没有找到你想听的歌曲<" + musicTitle + ">。";
                            text.setContent(content);
                            message = MessageUtil.textMsg2Xml(text);
                        } else {
                            // 音乐消息  
                            MusicMessage musicMessage = new MusicMessage();
                            musicMessage.setToUserName(fromUserName);
                            musicMessage.setFromUserName(toUserName);
                            musicMessage.setCreateTime(new Date().getTime());
                            musicMessage.setMsgType(MessageUtil.MESSAGE_MUSIC);
                            musicMessage.setMusic(music);
                            message = MessageUtil.musicMessageToXml(musicMessage);
                        }
                    }
                } else if (content.trim().startsWith("翻译")) {
                    String keyWord = content.replaceAll("^翻译", "").trim();
                    if ("".equals(keyWord)) {       // 翻译内容为空时显示帮助菜单
                        text.setContent(MessageUtil.getTranslateHelpMenu());
                    } else {
                        text.setContent(BaiduTranslateService.translate(keyWord));
                    }
                    message = MessageUtil.textMsg2Xml(text);
                } else if (content.trim().startsWith("天气")) {
                    String keyWord = content.replaceAll("^天气", "").trim();
                    if ("".equals(keyWord)) {       // 翻译内容为空时显示帮助菜单
                        text.setContent(MessageUtil.getWeatherHelpMenu());
                        message = MessageUtil.textMsg2Xml(text);
                    } else {
                        Article article = WeatherService.searchWeather(keyWord);
                        articleList.add(article);

                        newsMessage.setArticleCount(articleList.size());    // 设置图文消息个数  
                        newsMessage.setArticles(articleList);       // 设置图文消息包含的图文集合  
                        message = MessageUtil.newsMessageToXml(newsMessage);       // 将图文消息对象转换成xml字符串  
                    }
                } else if (content.trim().startsWith("手机号")) {
                    String keyWord = content.replaceAll("^手机号", "").trim();
                    if ("".equals(keyWord)) {       // 翻译内容为空时显示帮助菜单
                        text.setContent(MessageUtil.getPhoneHelpMenu());
                        message = MessageUtil.textMsg2Xml(text);
                    } else {
                        String phoneReqUrl = WeixinUtil.PHONE_SEARCH_URL;
                        phoneReqUrl = phoneReqUrl.replace("{num}", keyWord);
                        String phoneResult = PhoneService.getPhoneInfo(phoneReqUrl);
                        log.info("phoneResult:{}", phoneResult);

                        if (!phoneResult.isEmpty() && !phoneResult.equals("")) {
                            text.setContent(MessageUtil.emoji(0x1F449) + keyWord + " :" + phoneResult);
                            message = MessageUtil.textMsg2Xml(text);
                        }
                    }
                } else if (content.trim().startsWith("公交")) {
                    String keyWord = content.replaceAll("^公交", "").trim();
                    if ("".equals(keyWord)) {       // 翻译内容为空时显示帮助菜单
                        text.setContent(MessageUtil.getBusHelpMenu());
                        message = MessageUtil.textMsg2Xml(text);
                    } else {
                        String keyArrs[] = (keyWord.split("@"));
                        log.info("keyArrs:{}", keyArrs.toString());

                        if (keyArrs.length == 2) {
                            String requestUrl = WeixinUtil.BUS_SEARCH_URL;
                            requestUrl = requestUrl.replace("{city}", urlEncodeUTF8(keyArrs[0])).replace("{station}", urlEncodeUTF8(keyArrs[1]));
                            log.info("requestUrl:{}", requestUrl);
                            content = BusService.getBusInfo(requestUrl);

                            Article article = BusService.getBusArticleInfo(content);
                            articleList.add(article);
                            newsMessage.setArticleCount(articleList.size());    // 设置图文消息个数  
                            newsMessage.setArticles(articleList);       // 设置图文消息包含的图文集合  
                            message = MessageUtil.newsMessageToXml(newsMessage);
                        } else {
                            content = "以“公交”开头文字表示公交查询，请仔细检查您的输入格式！回复“？”查看帮助菜单";

                            text.setContent(content);
                            message = MessageUtil.textMsg2Xml(text);
                        }
                    }
                } else {
                    String requestUrl = WeixinUtil.TURING_ROBOT_URL;
                    requestUrl = requestUrl.replace("{info}", content);
                    content = TuringRobotService.getTuringRobotMsg(requestUrl);

                    text.setContent(content);
                    message = MessageUtil.textMsg2Xml(text);
                }
            } else if (MessageUtil.MESSAGE_EVENT.equals(msgType)) {
                text.setMsgType("text");
                String eventType = map.get("Event");

                if (eventType.equals(MessageUtil.MESSAGE_SUBSCRIBE)) {
                    content = "谢谢关注！\n" + MessageUtil.getMainMenu();
                    text.setContent(content);
                    message = MessageUtil.textMsg2Xml(text);
                } else if (eventType.equals(MessageUtil.MESSAGE_CLICK)) {
                    // 事件KEY值，与创建自定义菜单时指定的KEY值对应  
                    String eventKey = map.get("EventKey");

                    if (eventKey.equals("11")) {
                        content = MessageUtil.getWeatherHelpMenu();
                        text.setContent(content);
                        message = MessageUtil.textMsg2Xml(text);
                    } else if (eventKey.equals("12")) {
//                        content = "公交查询菜单项被点击！";
                        content = MessageUtil.getBusHelpMenu();
                        text.setContent(content);
                        message = MessageUtil.textMsg2Xml(text);
                    } else if (eventKey.equals("14")) {
                        content = MessageUtil.getPhoneHelpMenu();
                        text.setContent(content);
                        message = MessageUtil.textMsg2Xml(text);
                    } else if (eventKey.equals("13")) {
//                        content = "小译在线菜单项被点击了！";
                        content = MessageUtil.getTranslateHelpMenu();
                        text.setContent(content);
                        message = MessageUtil.textMsg2Xml(text);
                    } else if (eventKey.equals("15")) {
//                        content = "历史上的今天菜单项被点击！";
                        content = TodayInHistoryService.getTodayInHistoryInfo();
                        text.setContent(content);
                        message = MessageUtil.textMsg2Xml(text);
                    } else if (eventKey.equals("21")) {
                        content = MessageUtil.getMusicHelpMenu();
                        text.setContent(content);
                        message = MessageUtil.textMsg2Xml(text);
                    } else if (eventKey.equals("23")) {
//                        content = "美女电台菜单项被点击！";
                        List<Article> articles = HotArticleService.searchHotArticles();

                        newsMessage.setArticleCount(articles.size());    // 设置图文消息个数  
                        newsMessage.setArticles(articles);       // 设置图文消息包含的图文集合  
                        message = MessageUtil.newsMessageToXml(newsMessage);       // 将图文消息对象转换成xml字符串 
                    } else if (eventKey.equals("24")) {
//                        content = "人脸识别菜单项被点击！";
                        content = MessageUtil.getFaceHelpMenu();
                        text.setContent(content);
                        message = MessageUtil.textMsg2Xml(text);
                    } else if (eventKey.equals("25")) {
//                        content = "聊天唠嗑菜单项被点击！";
                        content = MessageUtil.getChatHelpMenu();
                        text.setContent(content);
                        message = MessageUtil.textMsg2Xml(text);
                    }
                }
            } else if (MessageUtil.MESSAGE_IMAGE.equals(msgType)) {     // 请求图片信息
                text.setMsgType("text");
                String picUrl = map.get("PicUrl");      // 取得图片地址  

                content = FaceService.detect(picUrl);   // 人脸检测  
                text.setContent(content);
                message = MessageUtil.textMsg2Xml(text);
            } else if (MessageUtil.MESSAGE_VOICE.equals(msgType)) {
                text.setMsgType("text");
                content = "大声点，我听不清楚。";

                text.setContent(content);
                message = MessageUtil.textMsg2Xml(text);
            } else if (MessageUtil.MESSAGE_SHORTVIDEO.equals(msgType)) {
                text.setMsgType("text");
                content = "这都是神马啊，一点也看不清楚。";

                text.setContent(content);
                message = MessageUtil.textMsg2Xml(text);
            } else if (MessageUtil.MESSAGE_LOCATION.equals(msgType)) {
                text.setMsgType("text");
                content = ">>>我在这里<<<";
                log.debug("loc1: {}", map.get("Location_X"));
                log.debug("loc2: {}", map.get("Location_Y"));
                log.debug("loc3: {}", map.get("Label"));
                text.setContent(content);
                message = MessageUtil.textMsg2Xml(text);
            }
            outWriter.print(message);
            log.debug("MSG:\n{}", message);
        } catch (DocumentException ex) {
            log.info("ex:{}", ex);
        } finally {
            outWriter.close();
        }
    }
}
