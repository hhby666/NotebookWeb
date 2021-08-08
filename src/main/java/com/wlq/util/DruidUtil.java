package com.wlq.util;


import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

/**
 * program: NotebookWeb
 * <br>description: Druid数据库连接池工具类
 *
 * @author by 王林清 on 2021/8/8
 * @version Java1.9 IntelliJ IDEA
 */

public class DruidUtil {
    private static DataSource dataSource = null;

    static {
        try {
            InputStream in = DruidUtil.class.getClassLoader().getResourceAsStream("druid.properties");
            Properties properties = new Properties();
            properties.load(in);

            //创建数据源 工厂模式 --> 创建
            dataSource = DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取一个Druid连接池中的一个连接
     *
     * @return conn 数据库连接
     * @throws SQLException 数据库异常
     * @author 王林清
     * @since 2021/8/8 20:45
     */
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    /**
     * 销毁查询时用到的数据库资源
     *
     * @param conn 数据库连接资源
     * @param ps   sql预编译资源
     * @param rs   查询到的结果集资源
     * @author 王林清
     * @since 2021/8/8 20:43
     */
    public static void release(Connection conn, PreparedStatement ps, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        release(conn, ps);
    }

    /**
     * 在关闭增删改事务时释放资源的函数，无ResultSet资源
     *
     * @param conn 数据库连接资源
     * @param ps   sql预编译资源
     * @author 王林清
     * @since 2021/8/8 20:41
     */
    public static void release(Connection conn, PreparedStatement ps) {
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}