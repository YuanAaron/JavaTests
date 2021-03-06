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
        Account from = accountDao.queryAccountByCardNo(fromCardNo);
        Account to = accountDao.queryAccountByCardNo(toCardNo);

        from.setMoney(from.getMoney()-money);
        to.setMoney(to.getMoney()+money);

        accountDao.updateAccountByCardNo(from);
//        int a = 1 / 0;
        accountDao.updateAccountByCardNo(to);
    }
}
