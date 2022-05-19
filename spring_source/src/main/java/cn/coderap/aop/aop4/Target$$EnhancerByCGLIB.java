package cn.coderap.aop.aop4;

import cn.coderap.aop.aop4.CglibProxyDemo.*;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.lang.reflect.UndeclaredThrowableException;

/**
 * 类名是自己定义的
 */
public class Target$$EnhancerByCGLIB extends Target {

    static Method foo0;
    static Method foo1;
    static Method foo2;
    static MethodProxy foo0Proxy;
    static MethodProxy foo1Proxy;
    static MethodProxy foo2Proxy;
    static {
        try {
            foo0 = Target.class.getMethod("foo", null);
            foo1 = Target.class.getMethod("foo", int.class);
            foo2 = Target.class.getMethod("foo", long.class);
            /**
             * 第一个参数：目标类型（父类类型）
             * 第二个参数：代理类型（子类类型）
             * 第三个参数：以()Ljava/lang/String;为例，其中（）表示无参数，Ljava/lang/String;表示返回值类型
             * 第四个参数：带增强功能的方法名
             * 第五个参数：带原始功能的方法名
             */
            foo0Proxy = MethodProxy.create(Target.class, Target$$EnhancerByCGLIB.class, "()Ljava/lang/String;", "foo", "fooSuper");
            foo1Proxy = MethodProxy.create(Target.class, Target$$EnhancerByCGLIB.class, "(I)Ljava/lang/String;", "foo", "fooSuper");
            foo2Proxy = MethodProxy.create(Target.class, Target$$EnhancerByCGLIB.class, "(J)Ljava/lang/String;", "foo", "fooSuper");
        } catch (NoSuchMethodException e) {
            throw new NoSuchMethodError(e.getMessage());
        }
    }

    private MethodInterceptor methodInterceptor;

    public void setMethodInterceptor(MethodInterceptor methodInterceptor) {
        this.methodInterceptor = methodInterceptor;
    }

    // -----------------------------带原始功能的方法---------------------
    public String fooSuper() {
        return super.foo();
    }

    public String fooSuper(int i) {
        return super.foo(i);
    }

    public String fooSuper(long i) {
        return super.foo(i);
    }

    // -----------------------------带增强功能的方法---------------------
    @Override
    public String foo() {
        try {
            return (String) methodInterceptor.intercept(this, foo0, new Object[0], foo0Proxy);
        } catch (RuntimeException | Error e) {
            throw e;
        } catch (Throwable e) {
            throw new UndeclaredThrowableException(e);
        }
    }

    @Override
    public String foo(int i) {
        try {
            return (String) methodInterceptor.intercept(this, foo1, new Object[]{i}, foo1Proxy);
        } catch (RuntimeException | Error e) {
            throw e;
        } catch (Throwable e) {
            throw new UndeclaredThrowableException(e);
        }
    }

    @Override
    public String foo(long i) {
        try {
            return (String) methodInterceptor.intercept(this, foo2, new Object[]{i}, foo2Proxy);
        } catch (RuntimeException | Error e) {
            throw e;
        } catch (Throwable e) {
            throw new UndeclaredThrowableException(e);
        }
    }
}
