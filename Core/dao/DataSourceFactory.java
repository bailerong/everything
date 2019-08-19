package com.lele.everything.Core.dao;
import com.alibaba.druid.pool.DruidDataSource;
import org.h2.Driver;
import javax.sql.DataSource;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DataSourceFactory {

    //这是我们的数据源，使用工厂模式+单例模式。
    //拒绝用户对其的随意访问
    private static volatile DruidDataSource dataSource;
//私有构造方法
    private DataSourceFactory(DruidDataSource dataSource) {
        this.dataSource=dataSource;
    }
//单例模式
    public static DataSource dataSource() {
        if (dataSource == null) {
            //double check
            synchronized (DataSourceFactory.class) {
                if (dataSource == null) {
                    dataSource = new DruidDataSource();
                    dataSource.setDriverClassName("org.h2.Driver");
                    //采用的是H2的嵌入式数据库，数据库以本地文件的方式存储，
                    // 只需要提供url接口
                    //如何获取当前工程路径
                    String workdir = System.getProperty("user.dir");
                    dataSource.setUrl("jdbc:h2:" + workdir + File.separator + "everything");
                }
            }
        }
        return dataSource;
    }

    public static void initDatabase() {
        //获取数据源
        //获取Sql语句
        //try-with-resources

        try (InputStream in = DataSourceFactory.class.getClassLoader().
                getResourceAsStream("everything.sql");) {
            if (in == null) {
                //数据库的初始化是必须要进
