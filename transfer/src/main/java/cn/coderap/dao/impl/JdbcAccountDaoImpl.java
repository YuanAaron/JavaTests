package cn.coderap.dao.impl;

import cn.coderap.dao.AccountDao;
import cn.coderap.pojo.Account;
import cn.coderap.utils.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class JdbcAccountDaoImpl implements AccountDao {
    @Override
    public Account queryAccountByCardNo(String cardNo) throws Exception {
        //从连接池获取连接
        //改造为：从当前线程中获取绑定的Connection连接
//        Connection conn = DruidUtil.getInstance().getConnection();
        Connection conn = ConnectionUtil.getInstance().getCurThreadConn();
        String sql = "select * from account where cardNo = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setString(1, cardNo);
        ResultSet resultSet = preparedStatement.executeQuery();

        Account account = new Account();
        while (resultSet.next()) {
            account.setName(resultSet.getString("name"));
            account.setCardNo(resultSet.getString("cardNo"));
            account.setMoney(resultSet.getInt("money"));
        }

        resultSet.close();
        preparedStatement.close();
//        conn.close();

        return account;
    }

    @Override
    public int updateAccountByCardNo(Account account) throws Exception{
        //从连接池获取连接
//        Connection conn = DruidUtil.getInstance().getConnection();
        Connection conn = ConnectionUtil.getInstance().getCurThreadConn();
        String sql = "update account set money = ? where cardNo = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setInt(1, account.getMoney());
        preparedStatement.setString(2, account.getCardNo());
        int i = preparedStatement.executeUpdate();

        preparedStatement.close();
//        conn.close();
        return i;
    }
}
