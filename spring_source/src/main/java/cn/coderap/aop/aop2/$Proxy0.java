package cn.coderap.aop.aop2;

import java.lang.reflect.Method;
import java.lang.reflect.UndeclaredThrowableException;

/**
 * 模拟Jdk动态代理代理类源码
 */
public class $Proxy0 implements JdkProxyDemo.Foo {

    private JdkProxyDemo.InvocationHandler h;

    public $Proxy0(JdkProxyDemo.InvocationHandler h) {
        this.h = h;
    }

    @Override
    public void foo() {
        try {
            Method foo = JdkProxyDemo.Foo.class.getMethod("foo");
            h.invoke(foo, new Object[0]);
        } catch (RuntimeException | Error e) {
            //运行时异常
            throw e;
        } catch (Throwable e) {
            // 检查异常
            throw new UndeclaredThrowableException(e); // 转换成运行时异常抛出(不是很懂!!!)
        }
    }

    @Override
    public String bar() {
        try {
            Method bar = JdkProxyDemo.Foo.class.getMethod("bar");
            String ret = (String)h.invoke(bar, new Object[0]);
            return ret;
        } catch (RuntimeException | Error e) {
            //运行时异常
            throw e;
        } catch (Throwable e) {
            // 检查异常
            throw new UndeclaredThrowableException(e); // 转换成运行时异常抛出(不是很懂!!!)
        }
    }
}
