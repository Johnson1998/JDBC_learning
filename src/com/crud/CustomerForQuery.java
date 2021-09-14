package com.crud;

import com.bean.Customer;
import com.util.JDBCUtil;
import org.junit.Test;

import java.awt.*;
import java.lang.reflect.Field;
import java.sql.*;

/**
 * @author John
 * @create 2021-09-1420:35
 */


public class CustomerForQuery {
    @Test
    public void testQueryForCustomers() {
        String sql = "select id, name, birth, email from customers where id =?";
        Customer customer = queryForCustomers(sql, 13);
        System.out.println(customer);
    }
    //    通用查询工作
    public Customer queryForCustomers(String sql, Object ...args) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            connection = JDBCUtil.getConnection();

            ps = connection.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i+1, args[i]);
            }
            rs = ps.executeQuery();
//        获取结果集的元数据
            ResultSetMetaData rsmd = rs.getMetaData();
//        通过ResultSetMetaData获取结果集中的列数
            int columnCount = rsmd.getColumnCount();
            if (rs.next()){
                Customer cust = new Customer();
                for (int i = 0; i < columnCount; i++) {

                    Object columnValues = rs.getObject(i + 1);

    //                获取每个列的列名
                    String columnName = rsmd.getColumnName(i + 1);
    //                给cust对象指定的columnName属性，赋值为columValue，通过反射
                    Field field = Customer.class.getDeclaredField(columnName);
                    field.setAccessible(true);
                    field.set(cust, columnValues);
                }
                return cust;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {

        JDBCUtil.closeResource(connection, ps, rs);
        }
        return null;
    }
    @Test
    public void testQuery1() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try {
            conn = JDBCUtil.getConnection();
            String sql = "select id, name, email, birth from customers where id = ?";
            ps = conn.prepareStatement(sql);
            ps.setObject(1, 1);
            //执行,并返回结果集
            resultSet = ps.executeQuery();
            //处理结果集
            if (resultSet.next()){ //判断结果集的下一条是否有数据，如果返回的数据为true，并指正下移，如果为false，则指针不会下移
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                String email = resultSet.getString(3);
                Date birth = resultSet.getDate(4);
                //            方式一：
                //            System.out.println("");
                //            方式二
                //            Object[] data = new Object[]{id, name, email, birth};
                //              方式三
                Customer customer = new Customer(id, name, email, birth);
                System.out.println(customer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            //        关闭资源
            JDBCUtil.closeResource(conn, ps, resultSet);
        }

    }
}

