package cn.coderap.aop.aop2;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class JdkProxyDemo {

    interface Foo {
        void foo();
        void bar();
    }

    static class Target implements Foo {
        @Override
        public void foo() {
            System.out.println("target: foo");
        }

        @Override
        public void bar() {
            System.out.println("target: bar");
        }
    }

    interface InvocationHandler {
        void invoke(Method method, Object[] args) throws Throwable;
    }

    public static void main(String[] args) {
        Target target = new Target();

        $Proxy0 proxy = new $Proxy0(new InvocationHandler() {
            @Override
            public void invoke(Method method, Object[] args) throws InvocationTargetException, IllegalAccessException {
                // 1.功能增强
                System.out.println("before...");
                // 2.调用目标
                method.invoke(target, args);
            }
        });
        proxy.foo();
        proxy.bar();
    }
}
