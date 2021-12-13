package cn.coderap.factory;

import cn.coderap.utils.TransactionManager;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyFactory {

    private TransactionManager transactionManager;

    public void setTransactionManager(TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    /**
     * Jdk动态代理
     */
    public Object getJdkProxy(Object obj) {
        return Proxy.newProxyInstance(obj.getClass().getClassLoader(), obj.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Object retVal = null;
                try {
                    // 开启事务
                    transactionManager.beginTransaction();
                    retVal = method.invoke(obj, args);

                    //提交事务
                    transactionManager.commit();
                } catch (Exception e) {
                    e.printStackTrace();
                    // 回滚事务
                    transactionManager.rollback();
                    // 抛出异常便于上层的servlet捕获以返回201
                    throw e;
                }

                return retVal;
            }
        });
    }

    /**
     * Cglib动态代理
     * @param obj
     * @return
     */
    public Object getCglibProxy(Object obj) {
        return Enhancer.create(obj.getClass(), new MethodInterceptor() {
            @Override
            public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
                Object retVal = null;
                try {
                    // 开启事务(关闭事务的自动提交)
                    transactionManager.beginTransaction();
                    retVal = method.invoke(obj, args);

                    //提交事务
                    transactionManager.commit();
                } catch (Exception e) {
                    // 回滚事务
                    transactionManager.rollback();
                    // 抛出异常便于上层的servlet捕获以返回201
                    throw e;
                }

                return retVal;
            }
        });
    }
}
