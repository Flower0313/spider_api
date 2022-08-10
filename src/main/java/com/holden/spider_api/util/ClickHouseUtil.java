package com.holden.spider_api.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @ClassName spider_api-ClickHouseUtil
 * @Author Holden_—__——___———____————_____Xiao
 * @Create 2022年8月09日15:47 - 周二
 * @Describe
 */
public class ClickHouseUtil {
    private static Connection connection = null;

    static {
        try {
            Class.forName("ru.yandex.clickhouse.ClickHouseDriver");// 驱动包
            String url = "jdbc:clickhouse://43.142.117.50:51515/spider_base";// url路径
            String user = "default";// 账号
            String password = "root";// 密码
            connection = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {
        return connection;
    }


    public static void close() throws SQLException {
        try {
            connection.close();
        } finally {
            connection.close();
        }
    }


}
