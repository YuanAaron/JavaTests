package cn.coderap.utils;

import java.sql.SQLException;

/**
 * 事务管理器：负责手动事务的开启、提交/回滚
 */
public class TransactionManager {

    private ConnectionUtil connectionUtil;

    public void setConnectionUtil(ConnectionUtil connectionUtil) {
        this.connectionUtil = connectionUtil;
    }

    public void beginTransaction() throws SQLException {
        connectionUtil.getCurThreadConn().setAutoCommit(false);
    }

    public void commit() throws SQLException {
        connectionUtil.getCurThreadConn().commit();
    }

    public void rollback() throws SQLException {
        connectionUtil.getCurThreadConn().rollback();
    }
}
