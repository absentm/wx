/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dm.beans.resp;

import java.util.List;

/**
 * 图文消息
 *
 * @author DUAN
 */
public class NewsMessage extends BaseMessage {

    private int ArticleCount;                  // 图文消息个数，限制为10条以内  
    private List<Article> Articles;            // 多条图文消息信息，默认第一个item为大图  

    public int getArticleCount() {
        return ArticleCount;
    }

    public void setArticleCount(int ArticleCount) {
        this.ArticleCount = ArticleCount;
    }

    public List<Article> getArticles() {
        return Articles;
    }

    public void setArticles(List<Article> Articles) {
        this.Articles = Articles;
    }

}
