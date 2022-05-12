package cn.coderap.aop.aop2;

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
        h.invoke();
    }
}
