package com.util;

import com.zs.ConnectionTest;

import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * @Description 获取数据库的连接
 * @author John
 * @create 2021-09-1410:14\
 */
public class JDBCUtil {
    public static Connection getConnection() throws Exception{
        //        1.读取配置文件中的4个基本信息
        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream("jdbc.properties");

        Properties pros = new Properties();
        pros.load(is);

        String user = pros.getProperty("user");
        String password = pros.getProperty("password");
        String url = pros.getProperty("url");
        String driverClass = pros.getProperty("driverClass");

        //2.加载驱动
        Class.forName(driverClass);

//        3.获取连接
        Connection conn = DriverManager.getConnection(url, user, password);
        System.out.println(conn);
        return conn;
    }
    /**
     * @Description 关闭连接和statment
     * @author John
     * @create 2021-09-1410:14\
     */
    public static void closeResource(Connection conn, PreparedStatement ps){
        try {
            if(ps != null)
                ps.close();
            if(conn != null)
                conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void closeResource(Connection conn, PreparedStatement ps, ResultSet rs){
        try {
            if(ps != null)
                ps.close();
            if(conn != null)
                conn.close();
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
