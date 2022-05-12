package cn.coderap.aop.aop2;

import java.lang.reflect.Method;

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
            h.invoke(foo,new Object[0]);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @Override
    public void bar() {
        try {
            Method bar = JdkProxyDemo.Foo.class.getMethod("bar");
            h.invoke(bar, new Object[0]);
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
}
