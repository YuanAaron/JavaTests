package cn.coderap.aop.aop2;

import cn.coderap.aop.aop2.JdkProxyDemo.*;
import java.lang.reflect.Method;
import java.lang.reflect.UndeclaredThrowableException;

/**
 * 模拟Jdk动态代理代理类源码
 */
public class $Proxy0 implements Foo {

    static Method foo;
    static Method bar;

    static {
        try {
            foo = Foo.class.getMethod("foo");
            bar = Foo.class.getMethod("bar");
        } catch (NoSuchMethodException e) {
            throw new NoSuchMethodError(e.getMessage()); // 转换成运行时异常抛出(不是很懂!!!)
        }
    }

    private InvocationHandler h;

    public $Proxy0(InvocationHandler h) {
        this.h = h;
    }

    @Override
    public void foo() {
        try {
            h.invoke(this, foo, new Object[0]);
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
            String ret = (String)h.invoke(this, bar, new Object[0]);
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
