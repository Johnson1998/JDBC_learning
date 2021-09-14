package com.crud;

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
