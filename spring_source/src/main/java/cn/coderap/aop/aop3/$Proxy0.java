//package cn.coderap.aop.aop3;
//
//import cn.coderap.aop.aop3.Foo;
//
//import java.lang.reflect.InvocationHandler;
//import java.lang.reflect.Method;
//import java.lang.reflect.Proxy;
//import java.lang.reflect.UndeclaredThrowableException;
//
//public class $Proxy0 extends Proxy implements Foo {
//
//    private static Method foo;
//    static {
//        try {
//            foo = Foo.class.getMethod("foo");
//        } catch (NoSuchMethodException e) {
//            throw new NoSuchMethodError(e.getMessage());
//        }
//    }
//
//    public $Proxy0(InvocationHandler h) {
//        super(h);
//    }
//
//    @Override
//    public void foo() {
//        try {
//            h.invoke(this, foo, null);
//        } catch (RuntimeException | Error e) {
//            throw e;
//        } catch (Throwable e) {
//            throw new UndeclaredThrowableException(e);
//        }
//    }
//}
