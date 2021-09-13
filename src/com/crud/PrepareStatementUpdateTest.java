package com.crud;

import com.zs.ConnectionTest;
import org.junit.Test;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

/**
 * @author John
 * @create 2021-09-1322:34
 * 使用PrepareStatement来替换Statement，实现对数据表的增删改查操作
 * 增删改，查
 */

public class PrepareStatementUpdateTest {
    @Test
    public void testInsert() throws Exception {

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
        Connection conn = DriverManager.getConnection(url, user, password);
        System.out.println(conn);
    }
}
