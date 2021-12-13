package cn.coderap.utils;

import java.sql.SQLException;

/**
 * 事务管理器：负责手动事务的开启、提交/回滚
 */
public class TransactionManager {
    private TransactionManager() {}

    private static TransactionManager transactionManager = new TransactionManager();

    public static TransactionManager getInstance() {
        return transactionManager;
    }

    public void beginTransaction() throws SQLException {
        ConnectionUtil.getInstance().getCurThreadConn().setAutoCommit(false);
    }

    public void commit() throws SQLException {
        ConnectionUtil.getInstance().getCurThreadConn().commit();
    }

    public void rollback() throws SQLException {
        ConnectionUtil.getInstance().getCurThreadConn().rollback();
    }
}
