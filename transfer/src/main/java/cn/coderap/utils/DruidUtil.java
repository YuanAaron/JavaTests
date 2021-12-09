package cn.coderap.utils;

import com.alibaba.druid.pool.DruidDataSource;

public class DruidUtil {

    public static DruidDataSource dataSource = new DruidDataSource();
    static {
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/bank");
        dataSource.setUsername("root");
        dataSource.setPassword("Kexin!00");
    }

    public static DruidDataSource getInstance() {
        return dataSource;
    }
}
