package cn.coderap.aop.aop1;

import java.io.IOException;
import java.lang.reflect.Proxy;

/**
 * Jdk动态代理：
 * 1、代理类proxy和Target之间是兄弟关系(平级)，两者都实现了Foo接口。
 * 2、目标类可以是final修饰的，原因正是第一条
 *
 * 使用Arthas工具查看Jdk动态代理生成的代理类源:
 * 1. 需要知道代理类的类名；
 * 2. 进程不能结束
 *
 * 注意：Jdk动态代理其实并没有生成.java源码，而是直接生成字节码，这里通过Arthas看到的代理类源码是对字节码反编译的结果。
 * 这种直接生成字节码的底层技术叫ASM，即运行期间动态生成字节码，该技术广泛应用于Spring、Jdk等。
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
    public static void main(String[] params) throws IOException {
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
        System.out.println(proxyInstance.getClass().getName()); // Arthas使用条件一
        String ret = proxyInstance.foo();
        System.out.println(ret);
        System.in.read(); //Arthas使用条件二
    }
}
