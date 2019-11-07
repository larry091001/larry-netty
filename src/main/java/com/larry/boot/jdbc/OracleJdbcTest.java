package com.larry.boot.jdbc;

import oracle.jdbc.driver.OracleDriver;
import java.sql.*;
import java.util.Properties;

/**
 * @version V1.0
 * @Author: Larry(PC)
 * @Email: zhang_ying@suixingpay.com
 * @phone: 13552892515
 * 创建日期：2019/10/22 17:31
 */
public class OracleJdbcTest {
    public static void main(String[] args) {
        Connection connect = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            //第一步：注册驱动
            //第一种方式：类加载(常用)
            //Class.forName("oracle.jdbc.OracleDriver");

            //第二种方式：利用Driver对象
            Driver driver = new OracleDriver();
            DriverManager.deregisterDriver(driver);


            //第二步：获取连接
            //第一种方式：利用DriverManager（常用）
            //connect = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE", "你的oracle数据库用户名", "用户名密码");

            //第二种方式：直接使用Driver
            Properties pro = new Properties();
            pro.put("user", "ogg");
            pro.put("password", "ogg");
            connect = driver.connect("jdbc:oracle:thin:@127.0.0.1:1521:source", pro);

            //测试connect正确与否
            System.out.println(connect);


            //第三步：获取执行sql语句对象
            //第一种方式:statement
            //statement = connect.createStatement();
            long startTime = System.currentTimeMillis();
            System.out.println("开始时间：" + startTime);
            //第二种方式：PreStatement
            PreparedStatement preState = connect.prepareStatement("select A.* from ALL_tab_columns A");


            //第四步：执行sql语句
            //第一种方式：
            //resultSet = statement.executeQuery("select  * from tb1_dept");

            //第二种方式：
            //preState.setInt(1, 2);//1是指sql语句中第一个？,  2是指第一个？的values值
            resultSet = preState.executeQuery();        //执行查询语句
            //查询任何语句，如果有结果集，返回true，没有的话返回false,注意如果是插入一条数据的话，虽然是没有结果集，返回false，但是却能成功的插入一条数据
           // boolean execute = preState.execute();
//            System.out.println(execute);

            int count=0;
            //第五步：处理结果集
            while (resultSet.next())
            {
                String name = resultSet.getString("OWNER");
                String city = resultSet.getString("TABLE_NAME");
                count++;
                System.out.println(count + " " +name+"   "+city);  //打印输出结果集
            }
            long endTime = System.currentTimeMillis() - startTime;
            System.out.println("结束时间：" + endTime);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            //第六步：关闭资源
            try {
                if (resultSet!=null) resultSet.close();
                if (statement!=null) statement.close();
                if (connect!=null) connect.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
