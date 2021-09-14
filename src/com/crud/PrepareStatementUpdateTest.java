package com.crud;

import com.util.JDBCUtil;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Properties;

/**
 * @author John
 * @create 2021-09-1322:34
 * 使用PrepareStatement来替换Statement，实现对数据表的增删改查操作
 * 增删改，查
 */

public class PrepareStatementUpdateTest {

//    通用的增删改
    public void update(String sql, Object ...args) throws Exception{    //sql中展位符的个数与可变形参的长度相同
//        1.获取数据库的连接
        Connection conn = JDBCUtil.getConnection();
//        2.预编译sql语句，返回preparedStatement的实例
        PreparedStatement ps = conn.prepareStatement(sql);
//          3.填充占位符
        for (int i = 0; i < args.length; i++) {
            ps.setObject(i+1, args[i]); //参数从1开始，数组从0开始
        }
//        4.执行
        ps.execute();
//        5.关闭资源
        JDBCUtil.closeResource(conn, ps);
    }
//    修改customers表的一条记录
    @Test
    public void testUpdate() {
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            //1.获取数据库的连接
            conn = JDBCUtil.getConnection();
            //ctrl+alt+v
            //2.预编译sql语句，返回PreparedStatement的实例
            String sql = "update customers set name = ? where id = ?";
            ps = conn.prepareStatement(sql);
            //3.填充占位符
            ps.setObject(1, "莫扎特");
            ps.setObject(2, 18);

            //4.执行
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
            //ctrl + alt + t try-catch 自动补齐
        }finally {
        //5.资源关闭
        JDBCUtil.closeResource(conn, ps);

        }
    }

    @Test
    public void testInsert() {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
//    向customers表中添加一条记录
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
            conn = DriverManager.getConnection(url, user, password);
            System.out.println(conn);
//        4.预编译sql语句，返回preparedstatement实例
            String sql = "insert into customers(name, email, birth)values(?, ?, ?)";
            ps = conn.prepareStatement(sql);

//        5.填充占位符
            ps.setString(1, "哪吒");
            ps.setString(2, "neza@gmail.com");
            SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd");
            java.util.Date date= sdf.parse("1000-01-01");
            ps.setDate(3, new Date(date.getTime()));

//        6.执行操作
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            //        7.资源关闭
            try {
                if(ps != null)
                ps.close();
                if(conn != null)
                    conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }



    }
}
