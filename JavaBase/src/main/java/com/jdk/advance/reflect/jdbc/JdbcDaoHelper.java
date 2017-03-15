package com.jdk.advance.reflect.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 提供获取数据库连接、释放资源的接口
 */
public class JdbcDaoHelper {

    /**
     * 数据库用户名
     */
    private static final String USER = "root";

    /**
     * 数据库密码
     */
    private static final String PASSWORD = "root";

    /**
     * 连接数据库的地址
     */
//    private static final String URL = "jdbc:oracle:thin:@127.0.0.1:1521:study";
    private static final String URL = "jdbc:mysql://localhost:3306/test";


    private static ConcurrentHashMap<String, Connection> map = new ConcurrentHashMap<>();

    /**
     * 获得一个数据库连接对象
     *
     * @return java.sql.Connection实例
     */
    public static Connection getConnection() {
        Connection conn = map.get(URL);
        if (conn == null) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection(URL, USER, PASSWORD);
                map.put(URL, conn);
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }
        return conn;
    }

    /**
     * 释放数据库资源
     */
    public static void release(PreparedStatement ps, ResultSet rs) {
        try {
            if (ps != null) {
                ps.close();
            }
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}