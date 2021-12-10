package cn.coderap.service.impl;

import cn.coderap.dao.AccountDao;
import cn.coderap.dao.impl.JdbcAccountDaoImpl;
import cn.coderap.factory.BeanFactory;
import cn.coderap.pojo.Account;
import cn.coderap.service.TransferService;

public class TransferServiceImpl implements TransferService {

//    private AccountDao accountDao = new JdbcAccountDaoImpl();
    private AccountDao accountDao = (AccountDao) BeanFactory.getBean("accountDao");

    @Override
    public void transfer(String fromCardNo, String toCardNo, int money) throws Exception {
        Account from = accountDao.queryAccountByCardNo(fromCardNo);
        Account to = accountDao.queryAccountByCardNo(toCardNo);

        from.setMoney(from.getMoney()-money);
        to.setMoney(to.getMoney()+money);

        accountDao.updateAccountByCardNo(from);
        accountDao.updateAccountByCardNo(to);
    }
}
