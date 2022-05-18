package cn.coderap.aop.aop4;

import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class CglibProxyDemo {

    static class Target {
        public String foo() {
            System.out.println("target foo");
            return "target return";
        }

        public String foo(int i) {
            System.out.println("target foo int");
            return "target return int";
        }

        public String foo(long i) {
            System.out.println("target foo long");
            return "target return long";
        }
    }

    public static void main(String[] params) {
        Target target = new Target();

        Target$$EnhancerByCGLIB proxyInstance = new Target$$EnhancerByCGLIB();
        proxyInstance.setMethodInterceptor(new MethodInterceptor() {
            @Override
            public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
                System.out.println("before...");
                return method.invoke(target, args);
            }
        });
        System.out.println(proxyInstance.foo());
        System.out.println(proxyInstance.foo(1));
        System.out.println(proxyInstance.foo(2L));
    }
}
