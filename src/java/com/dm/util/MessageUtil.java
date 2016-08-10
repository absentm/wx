/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dm.util;

import com.dm.beans.resp.Article;
import com.dm.beans.resp.MusicMessage;
import com.dm.beans.resp.NewsMessage;
import com.dm.beans.resp.TextMessage;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * 微信消息工具类
 *
 * @author DUAN
 */
public class MessageUtil {

    public static final String MESSAGE_TEXT = "text";   // 文本消息 
    public static final String MESSAGE_IMAGE = "image"; // 图片消息
    public static final String MESSAGE_VOICE = "voice"; // 语音消息
    public static final String MESSAGE_VIDEO = "video"; // 视频消息
    public static final String MESSAGE_SHORTVIDEO = "shortvideo"; // 小视频消息
    public static final String MESSAGE_LINK = "link";   // 链接消息
    public static final String MESSAGE_LOCATION = "location"; // 地理位置消息

    public static final String MESSAGE_NEWS = "news";   // 图文消息 
    public static final String MESSAGE_MUSIC = "music";   // 音乐消息

    public static final String MESSAGE_EVENT = "event"; // 微信事件
    public static final String MESSAGE_SUBSCRIBE = "subscribe"; // 关注事件 
    public static final String MESSAGE_UNSUBSCRIBE = "unsubscribe"; // 取消关注事件
    public static final String MESSAGE_CLICK = "CLICK"; // 菜单点击事件
    public static final String MESSAGE_VIEW = "VIEW";   // 视图事件

    /**
     * xml转Map集合
     *
     * @param request
     * @return
     * @throws DocumentException
     * @throws IOException
     */
    public static Map<String, String> xml2Map(HttpServletRequest request) throws DocumentException, IOException {
        Map<String, String> map = new HashMap<String, String>();
        SAXReader reader = new SAXReader();
        InputStream ins = request.getInputStream();
        Document doc = reader.read(ins);
        Element root = doc.getRootElement();

        List<Element> list = root.elements();
        for (Element e : list) {
            map.put(e.getName(), e.getText());
        }
        ins.close();

        return map;
    }

    /**
     * java对象转化为xml
     *
     * @param textMessage
     * @return
     */
    public static String textMsg2Xml(TextMessage textMessage) {
        xstream.alias("xml", textMessage.getClass());
        return xstream.toXML(textMessage);
    }

    /**
     * 图文消息对象转换成xml
     *
     * @param newsMessage 图文消息对象
     * @return xml
     */
    public static String newsMessageToXml(NewsMessage newsMessage) {
        xstream.alias("xml", newsMessage.getClass());
        xstream.alias("item", new Article().getClass());
        return xstream.toXML(newsMessage);
    }

    /**
     * 音乐消息对象转换成xml
     *
     * @param musicMessage 音乐消息对象
     * @return xml
     */
    public static String musicMessageToXml(MusicMessage musicMessage) {
        xstream.alias("xml", musicMessage.getClass());
        return xstream.toXML(musicMessage);
    }
    /**
     * 扩展xstream，使其支持CDATA块
     */
    private static XStream xstream = new XStream(new XppDriver() {
        public HierarchicalStreamWriter createWriter(Writer out) {
            return new PrettyPrintWriter(out) {
                // 对所有xml节点的转换都增加CDATA标记  
                boolean cdata = true;

                @SuppressWarnings("unchecked")
                public void startNode(String name, Class clazz) {
                    super.startNode(name, clazz);
                }

                protected void writeText(QuickWriter writer, String text) {
                    if (cdata) {
                        writer.write("<![CDATA[");
                        writer.write(text);
                        writer.write("]]>");
                    } else {
                        writer.write(text);
                    }
                }
            };
        }
    });

    /**
     * emoji表情转换(hex -> utf-16)
     *
     * @param hexEmoji 16进制的Emoji格式
     * @return
     */
    public static String emoji(int hexEmoji) {
        return String.valueOf(Character.toChars(hexEmoji));
    }

    /**
     * utf编码
     *
     * @param source 任意字符串
     * @return utf-8类型字符串
     */
    public static String urlEncodeUTF8(String source) {
        String result = source;
        try {
            result = java.net.URLEncoder.encode(source, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 主菜单
     *
     * @return
     */
    public static String getMainMenu() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("您好，我是小M，请回复数字选择服务：").append("\n\n");
        buffer.append("    A  自我介绍").append("\n");
        buffer.append("    B  图文体验").append("\n");
        buffer.append("    C  功能帮助").append("\n");

        buffer.append("回复“?”显示此帮助菜单");
        return buffer.toString();
    }

    /**
     * 图文体验 帮助菜单
     *
     * @return
     */
    public static String getArticleHelp() {
        StringBuffer buffer = new StringBuffer();
        
        buffer.append("您好，我是小M，请回复数字选择图文体验：").append("\n\n");
        buffer.append("    a  单图文含图片").append("\n");
        buffer.append("    b  单图文无图片").append("\n");
        buffer.append("    c  多图文首条含图片").append("\n");
        buffer.append("    d  多图文首条无图片").append("\n");
        buffer.append("    e  多图文最后无图片").append("\n\n");
        buffer.append("回复“?”显示此帮助菜单");

        return buffer.toString();
    }

    /**
     * 功能帮助菜单
     *
     * @return
     */
    public static String getFunctonHelp() {
        StringBuffer buffer = new StringBuffer();
        
        buffer.append("您好，我是小M，请回复数字选择功能说明：").append("\n\n");
        buffer.append("    1  天气预报").append("\n");
        buffer.append("    2  公交查询").append("\n");
        buffer.append("    3  小译在线").append("\n");
        buffer.append("    4  手机号查询").append("\n");
        buffer.append("    5  人脸识别").append("\n");
        buffer.append("    6  爱音乐").append("\n");
        buffer.append("    7  你聊我说").append("\n");

//        buffer.append("    f  创建菜单").append("\n\n");
//        buffer.append("    g  菜单信息查询").append("\n\n");
        buffer.append("回复“?”显示此帮助菜单");

        return buffer.toString();
    }

    /**
     * 歌曲点播使用指南
     *
     * @return
     */
    public static String getMusicHelpMenu() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(emoji(0x1F3A4) + "  歌曲点播操作指南").append("\n\n");
        buffer.append("    回复：歌曲+歌名").append("\n");
        buffer.append("    例如：歌曲致青春").append("\n");
        buffer.append("    或者：歌曲致青春@王菲").append("\n\n");
        buffer.append("回复“?”显示主菜单");
        return buffer.toString();
    }

    /**
     * 百度翻译帮助菜单
     *
     * @return
     */
    public static String getTranslateHelpMenu() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(emoji(0xe148)).append("  小译通使用指南").append("\n\n");
        buffer.append("小译通为用户提供专业的多语言翻译服务，目前支持以下翻译方向：").append("\n");
        buffer.append("    中 -> 英").append("\n");
        buffer.append("    英 -> 中").append("\n");
        buffer.append("    日 -> 中").append("\n\n");
        buffer.append("使用示例：").append("\n");
        buffer.append("    翻译我是中国人").append("\n");
        buffer.append("    翻译dream").append("\n");
        buffer.append("    翻译さようなら").append("\n\n");
        buffer.append("回复“?”显示主菜单");
        return buffer.toString();
    }

    /**
     * 人脸检测帮助菜单
     */
    public static String getFaceHelpMenu() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(emoji(0x1F466)).append("  人脸检测使用指南  ").append(emoji(0x1F469)).append("\n\n");
        buffer.append("    发送一张清晰的照片，就能帮你分析出种族、年龄、性别等信息").append("\n\n");
        buffer.append("快来试试你是不是长得太着急");
        return buffer.toString();
    }

    /**
     * 天气预报帮助菜单
     *
     * @return
     */
    public static String getWeatherHelpMenu() {
        StringBuffer buffer = new StringBuffer();
        buffer.append(emoji(0x1F3A4) + "  天气预报操作指南").append("\n\n");
        buffer.append("    回复：天气+城市名称").append("\n");
        buffer.append("    例如：天气北京").append("\n\n");
        buffer.append("回复“?”显示主菜单");
        return buffer.toString();
    }

    /**
     * 聊天唠嗑帮助菜单
     *
     * @return
     */
    public static String getChatHelpMenu() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("\ue429" + "  陪你聊天唠嗑").append("\n\n");
        buffer.append("    亲，随便聊聊呗！看看新一代的尊上是如何炼成的！").append("\n");
        buffer.append("    例如：我漂亮吗？").append("\n\n");
        buffer.append("回复“?”显示主菜单");
        return buffer.toString();
    }

    /**
     * 手机号归属地帮助菜单
     *
     * @return
     */
    public static String getPhoneHelpMenu() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("\ue00a" + "  手机号查询操作指南").append("\n\n");
        buffer.append("    回复：手机号+手机号码（11位）").append("\n");
        buffer.append("    例如：手机号18908712xxx").append("\n\n");
        buffer.append("回复“?”显示主菜单");
        return buffer.toString();
    }

    /**
     * 公交查询帮助菜单
     *
     * @return
     */
    public static String getBusHelpMenu() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("\ue00a" + "  公交查询操作指南").append("\n\n");
        buffer.append("    回复：公交+城市名称@公交站牌名称").append("\n");
        buffer.append("    例如：公交郑州@郑州大学").append("\n\n");
        buffer.append("回复“?”显示主菜单");
        return buffer.toString();
    }
}
