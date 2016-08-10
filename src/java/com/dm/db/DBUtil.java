/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dm.db;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * mysql数据库操作帮助类
 *
 * @author DUAN
 */
public class DBUtil {

    private final static Logger log = LoggerFactory.getLogger(DBUtil.class);
    private static final String SQL_ROAD_NAME = "sql.properties";

    /**
     * 获取数据库连接
     *
     * @return
     */
    public static Connection getConnection() {
        Connection conn = null;
        try {
            Context sourceCtx = new InitialContext();
            DataSource ds = (DataSource) sourceCtx.lookup("java:comp/env/jdbc/wxDB");
            conn = ds.getConnection();
        } catch (NamingException | SQLException ex) {
            log.debug("ex:{}", ex);
        }

        return conn;
    }

    /**
     * 读取sql.properies文件中的属性
     *
     * @param sqlName sql语句名称
     * @return
     */
    public static String getSqlProValue(String key) {
        String value = "";
        Properties properties = new Properties();
        InputStream inputStream = null;
        try {
            inputStream = new BufferedInputStream(new FileInputStream(SQL_ROAD_NAME));
            properties.load(inputStream);

            value = properties.getProperty(key);
        } catch (FileNotFoundException ex) {
            log.debug("ex: {}", ex);
        } catch (IOException ex) {
            log.debug("ex: {}", ex);
        } finally {
            try {
                inputStream.close();
            } catch (IOException ex) {
                log.debug("ex: {}", ex);
            }
        }

        return value;
    }

    /**
     * 关闭数据库连接
     *
     * @param conn
     */
    public static void closeConnection(java.sql.Connection conn) {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * 生成序列号：时间戳方式
     *
     * @return
     */
    public static String generateSeqNum() {
        Date date = new Date();

        return String.valueOf(date.getTime());
    }
}
