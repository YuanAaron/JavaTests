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

    public static void main(String[] args) {
        $Proxy0 proxy = new $Proxy0();
        proxy.foo();
    }
}
