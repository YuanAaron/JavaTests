package cn.coderap.aop.aop4;

import cn.coderap.aop.aop4.CglibProxyDemo.*;
import org.springframework.cglib.proxy.MethodInterceptor;

import java.lang.reflect.Method;
import java.lang.reflect.UndeclaredThrowableException;

public class Target$$EnhancerByCGLIB extends Target {

    static Method foo0;
    static Method foo1;
    static Method foo2;
    static {
        try {
            foo0 = Target.class.getMethod("foo", null);
            foo1 = Target.class.getMethod("foo", int.class);
            foo2 = Target.class.getMethod("foo", long.class);
        } catch (NoSuchMethodException e) {
            throw new NoSuchMethodError(e.getMessage());
        }
    }

    private MethodInterceptor methodInterceptor;

    public void setMethodInterceptor(MethodInterceptor methodInterceptor) {
        this.methodInterceptor = methodInterceptor;
    }

    @Override
    public String foo() {
        try {
            return (String) methodInterceptor.intercept(this, foo0, new Object[0], null);
        } catch (RuntimeException | Error e) {
            throw e;
        } catch (Throwable e) {
            throw new UndeclaredThrowableException(e);
        }
    }

    @Override
    public String foo(int i) {
        try {
            return (String) methodInterceptor.intercept(this, foo1, new Object[]{i}, null);
        } catch (RuntimeException | Error e) {
            throw e;
        } catch (Throwable e) {
            throw new UndeclaredThrowableException(e);
        }
    }

    @Override
    public String foo(long i) {
        try {
            return (String) methodInterceptor.intercept(this, foo2, new Object[]{i}, null);
        } catch (RuntimeException | Error e) {
            throw e;
        } catch (Throwable e) {
            throw new UndeclaredThrowableException(e);
        }
    }
}
