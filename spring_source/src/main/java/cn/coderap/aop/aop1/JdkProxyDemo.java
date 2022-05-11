package cn.coderap.aop.aop1;

import java.lang.reflect.Proxy;

/**
 * Jdk动态代理：
 * 1、代理类proxy和Target之间是兄弟关系(平级)，两者都实现了Foo接口。
 * 2、目标类可以是final修饰的，原因正是第一条
 */
public class JdkProxyDemo {

    interface Foo {
        String foo();
    }

    static class Target implements Foo {
        @Override
        public String foo() {
            System.out.println("target: foo");
            return "target: return";
        }
    }
    public static void main(String[] params) {
        Target target = new Target();

        // classLoader：用来加载运行期间动态生成的字节码
        // interfaces：代理类要实现的接口
        // proxy：代理对象，method：正在执行的方法对象，args：传过来的方法参数
        ClassLoader classLoader = JdkProxyDemo.class.getClassLoader();
//        Class<?>[] interfaces = Target.class.getInterfaces();
        Class<?>[] interfaces = new Class[] {Foo.class};
        Foo proxyInstance = (Foo) Proxy.newProxyInstance(classLoader, interfaces, (proxy, method, args) -> {
            System.out.println("before...");
            Object ret = method.invoke(target, args);
            System.out.println("after...");
            return ret; // 让代理对象的方法也返回目标对象的方法执行的结果
        });
        String ret = proxyInstance.foo();
        System.out.println(ret);
    }
}
