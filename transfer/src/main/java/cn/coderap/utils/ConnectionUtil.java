package cn.coderap.utils;

import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionUtil {

    private ConnectionUtil() {}
    private static ConnectionUtil connectionUtil = new ConnectionUtil();
    public static ConnectionUtil getInstance() {
        return connectionUtil;
    }

    // ThreadLocal是用于存储当前线程独自维护的数据的载体
    public static ThreadLocal<Connection> threadLocal = new ThreadLocal<>();

    /**
     * 从当前线程获取连接
     * @return
     */
    public static Connection getCurThreadConn() throws SQLException {
        // 当前线程是否已经绑定连接，如果没有，需要从连接池获取一个连接并绑定到当前线程
        Connection conn = threadLocal.get();
        if (conn == null) {
            // 从线程池获取连接
            conn = DruidUtil.getInstance().getConnection();
            // 绑定到当前线程
            threadLocal.set(conn);
        }
        return conn;
    }
}


