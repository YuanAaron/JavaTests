package cn.coderap.aop.aop1;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * Cglib代理：
 * 1. 目标类（Target) 是父类型，代理类是子类型
 * 2. 父类不能是final（final的父类不能有子类），父类的方法也不能是final的（通过方法重写实现增强，
 *    而final方法是不能被重写的，如果加了final，不会报错，但是不会进行增强）
 */
public class CglibProxyDemo {

    static class Target {

        public String foo() {
            System.out.println("target: foo");
            return "target: return";
        }
    }

    public static void main(String[] params) {
        Target target = new Target();

        // 第一个参数：代理类的父类
        // 第二个参数：回调，类似于Jdk动态代理中的InvocationHandler
            // 1. 代理对象
            // 2. 正在执行的方法对象
            // 3. 传入的方法参数
            // 4. 方法代理（注意：methodProxy与method的区别与联系）
        Target proxyInstance = (Target) Enhancer.create(Target.class, (MethodInterceptor) (proxy, method, args, methodProxy) -> {
            System.out.println("before...");
//            Object ret = method.invoke(target, args); // 内部用方法反射来调用目标
//            Object ret = methodProxy.invoke(target, args); // 内部没有使用反射（Spring使用的方式）
            Object ret = methodProxy.invokeSuper(proxy, args); // 内部没有使用反射
            System.out.println("after...");
            return ret;
        });

        String ret = proxyInstance.foo();
        System.out.println(ret);
    }
}
