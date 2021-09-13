package com.zs;

import org.junit.Test;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author John
 * @create 2021-09-138:07
 */
public class ConnectionTest {
    @Test
    public void testConnection1() throws SQLException {
        Driver driver = new com.mysql.cj.jdbc.Driver();
        String url = "jdbc:mysql://localhost:3306/test?serverTimezone=UTC";
//        将用户名密码封装在properties中
        Properties info = new Properties();
        info.setProperty("user", "root");
        info.setProperty("password", "zhangshen1998");
        Connection conn = driver.connect(url, info);
        System.out.println(conn);
    }
    //    方式二: 对方式一的迭代：在如下程序中不出现第三方的api， 是的程序具有更好的移植性
    @Test
    public void testConnection2() throws Exception {
//        1.获取Driver实现类对象，使用反射
        Class clazz = Class.forName("com.mysql.cj.jdbc.Driver");
        Driver driver = (Driver) clazz.getDeclaredConstructor().newInstance();
//            2.提供链接的数据库
        String url = "jdbc:mysql://localhost:3306/test";
//        3.提供链接需要的用户名和密码
        Properties info = new Properties();
        info.setProperty("user", "root");
        info.setProperty("password", "zhangshen1998");
//        4.获取链接
        Connection conn = driver.connect(url, info);
        System.out.println(conn);

    }
    //    方式三：使用DriverManager替换Driver
    @Test
    public void testConnection3() throws Exception {
//        获取Driver实现类对象
        Class clazz = Class.forName("com.mysql.cj.jdbc.Driver");
        Driver driver = (Driver) clazz.getDeclaredConstructor().newInstance();

//        提供三个链接的基本信息
        String url = "jdbc:mysql://localhost:3306/test";
        String user = "root";
        String password = "zhangshen1998";
//        注册驱动
        DriverManager.registerDriver(driver);
//        获取链接
        Connection conn = DriverManager.getConnection(url, user, password);
        System.out.println(conn);
    }
    //    方法四：只是加载驱动不需要显示的注册驱动了
    @Test
    public void testConnection4() throws Exception {

//        加载代码块，执行static代码块
        Class.forName("com.mysql.cj.jdbc.Driver");
//        相较于方式三，可以省略如下操作
//        Driver driver = (Driver) clazz.getDeclaredConstructor().newInstance();
////        注册驱动
//        DriverManager.registerDriver(driver);
/*        为什么能省略呢？以为加载的时候执行了
        static {
            try {
                DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            } catch (SQLException var1) {
                throw new RuntimeException("Can't register driver!");
            }
        }*/
        //        提供三个链接的基本信息
        String url = "jdbc:mysql://localhost:3306/test";
        String user = "root";
        String password = "zhangshen1998";
//        获取链接
        Connection conn = DriverManager.getConnection(url, user, password);
        System.out.println(conn);
    }
//    方法五（final版）,将数据库需要的4个基本信息声明在配置文件中，通过读取配置文件的方式获取连接
    @Test
    public void getConnection5() throws Exception{

//        1.读取配置文件中的4个基本信息
        InputStream is = ConnectionTest.class.getClassLoader().getResourceAsStream("jdbc.properties");

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
