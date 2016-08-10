/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dm.beans.baidufanyi;

import java.util.List;

/**
 * 调用百度翻译api查询结果
 *
 * @author DUAN
 */
public class TranslateResult {

    private String from;        // 实际采用的源语言  
    private String to;          // 实际采用的目标语言 
    private List<ResultPair> trans_result;      // 结果体 

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public List<ResultPair> getTrans_result() {
        return trans_result;
    }

    public void setTrans_result(List<ResultPair> trans_result) {
        this.trans_result = trans_result;
    }
}
