package cn.coderap.aop.aop2;

/**
 * 模拟Jdk动态代理代理类源码
 */
public class $Proxy0 implements JdkProxyDemo.Foo {

    @Override
    public void foo() {
        // 1.功能增强
        System.out.println("before...");
        // 2.调用目标
        new JdkProxyDemo.Target().foo();
    }
}
