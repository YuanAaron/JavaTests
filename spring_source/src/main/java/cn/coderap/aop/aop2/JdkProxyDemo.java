package cn.coderap.aop.aop2;

public class JdkProxyDemo {

    interface Foo {
        void foo();
    }

    static class Target implements Foo {
        @Override
        public void foo() {
            System.out.println("target: foo");
        }
    }

    interface InvocationHandler {
        void invoke();
    }

    public static void main(String[] args) {
        $Proxy0 proxy = new $Proxy0(new InvocationHandler() {
            @Override
            public void invoke() {
                // 1.功能增强
                System.out.println("before...");
                // 2.调用目标
                new JdkProxyDemo.Target().foo();
            }
        });
        proxy.foo();
    }
}
