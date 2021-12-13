package cn.coderap.service.impl;

import cn.coderap.dao.AccountDao;
import cn.coderap.pojo.Account;
import cn.coderap.service.TransferService;
import cn.coderap.utils.TransactionManager;

public class TransferServiceImpl implements TransferService {

    private AccountDao accountDao;

    public void setAccountDao(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @Override
    public void transfer(String fromCardNo, String toCardNo, int money) throws Exception {
        try {
            // 开启事务(关闭事务的自动提交)
            TransactionManager.getInstance().beginTransaction();

            Account from = accountDao.queryAccountByCardNo(fromCardNo);
            Account to = accountDao.queryAccountByCardNo(toCardNo);

            from.setMoney(from.getMoney()-money);
            to.setMoney(to.getMoney()+money);

            accountDao.updateAccountByCardNo(from);
//            int a = 1 / 0;
            accountDao.updateAccountByCardNo(to);

            // 提交事务
            TransactionManager.getInstance().commit();
        } catch (Exception e) {
            // 回滚事务
            TransactionManager.getInstance().rollback();
            // 抛出异常便于上层的servlet捕获以返回201
            throw e;
        }
    }
}
