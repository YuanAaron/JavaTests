package cn.coderap.aop.aop2;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class JdkProxyDemo {

    interface Foo {
        void foo();
        String bar();
    }

    static class Target implements Foo {
        @Override
        public void foo() {
            System.out.println("target: foo");
        }

        @Override
        public String bar() {
            System.out.println("target: bar");
            return "target: return";
        }
    }

    interface InvocationHandler {
        Object invoke(Method method, Object[] args) throws Throwable;
    }

    public static void main(String[] args) {
        Target target = new Target();

        $Proxy0 proxy = new $Proxy0(new InvocationHandler() {
            @Override
            public Object invoke(Method method, Object[] args) throws InvocationTargetException, IllegalAccessException {
                // 1.功能增强
                System.out.println("before...");
                // 2.调用目标
                Object ret = method.invoke(target, args);
                return ret;
            }
        });
        proxy.foo();
        System.out.println(proxy.bar());
    }
}
