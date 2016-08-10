/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dm.beans.templateMsg.detaildata;

import com.dm.beans.AccessToken;
import com.dm.beans.templateMsg.basedata.Data;
import com.dm.beans.templateMsg.basedata.First;
import com.dm.beans.templateMsg.basedata.Keyword1;
import com.dm.beans.templateMsg.basedata.Keyword2;
import com.dm.beans.templateMsg.basedata.Keyword3;
import com.dm.beans.templateMsg.basedata.Remark;
import com.dm.beans.templateMsg.basedata.WxDate;
import com.dm.servlet.TokenThread;
import com.dm.util.DateUtil;
import com.dm.util.WeixinUtil;
import java.text.SimpleDateFormat;
import java.util.Date;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 包装车辆模板消息体的data数据
 *
 * @author DUAN
 */
public class DetailDataUtil {

    private static Logger log = LoggerFactory.getLogger(DetailDataUtil.class);

    /**
     * 生成车检消息体的data数据
     *
     * @return
     */
    public static Data getVehCheckData() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        Date date = new Date();
        String dateStr = sdf.format(date);

        Data data = new Data();
        First first = new First();
        Keyword1 keyword1 = new Keyword1();
        Keyword2 keyword2 = new Keyword2();
        Keyword3 keyword3 = new Keyword3();
        Remark remark = new Remark();

        first.setValue("亲爱的会员，你的车辆年检已到期！\n");
        first.setColor("#173177");

        keyword1.setValue("小型机动车");
        keyword1.setColor("#173177");

        keyword2.setValue("豫A01B2B");
        keyword2.setColor("#173177");

        keyword3.setValue(dateStr);
        keyword3.setColor("#173177");

        remark.setValue("\n\n请务必在车辆年审到期之前进行处理，更多内容请查看详情");
        remark.setColor("#33CCFF");

        data.setFirst(first);
        data.setKeyword1(keyword1);
        data.setKeyword2(keyword2);
        data.setKeyword3(keyword3);
        data.setRemark(remark);

        return data;
    }

    /**
     * 获取用户关注总数
     *
     * @return int类型
     */
    private static int getUserCount(Date oneDate) {
        int result = 0;
        Date beforeDate = DateUtil.addDay(oneDate, -1);

        AccessToken at = TokenThread.accessToken;   // 调用接口获取access_token  
        String requestUrl = WeixinUtil.TEMPLATE_MSG_URL.replace("ACCESS_TOKEN", at.getToken());
        log.info("access_token:{}", at.getToken());

        WxDate date = new WxDate();
        date.setBegin_date(DateUtil.defaultDate2String(beforeDate));
        date.setEnd_date(DateUtil.defaultDate2String(beforeDate));

        // 发送消息的正文json数据
        String jsonData = JSONObject.fromObject(date).toString();

        if (null != at) {
            JSONObject jSONObject = WeixinUtil.httpRequest(requestUrl, "POST", jsonData);
            log.debug("jSONObject: {}", jSONObject.toString());

            if (jSONObject != null) {
                String firstElemStr = jSONObject.getJSONArray("list").get(0).toString();
                JSONObject firstElemJSONObject = JSONObject.fromObject(firstElemStr);
                log.debug("firstElemJSONObject: {}", firstElemJSONObject);

                result = firstElemJSONObject.getInt("cumulate_user");
                log.debug("result: {}", result);
            }
        }
        
        return result;
    }
}
