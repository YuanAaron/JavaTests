package cn.coderap.aop.aop3;

import java.io.FileOutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class TestProxy {

    static class MyClassLoader extends ClassLoader {

        /**
         * 将字节数组转化为Class对象
         */
        public Class<?> defineClassForName(String name, byte[] data) {
            return defineClass(name,data, 0,data.length);
        }
    }

    public static void main(String[] args) throws Exception {
        byte[] dump = $Proxy0Dump.dump();
//        FileOutputStream fos = new FileOutputStream("$Proxy0.class");
//        fos.write(dump,0,dump.length);
//        fos.close();

        //不需要将二进制字节码写入到磁盘文件，在内存中加载并使用即可
        // 将字节数组转化为Class对象（老师的写法）
//        ClassLoader classLoader = new ClassLoader() {
//            @Override
//            protected Class<?> findClass(String name) throws ClassNotFoundException {
//                return defineClass(name,dump,0,dump.length);
//            }
//        };
//        Class<?> proxyClass = classLoader.loadClass("cn.coderap.aop.aop3.$Proxy0");

        // 将字节数组转化为Class对象（别人博客中的写法）
        Class<?> proxyClass = new MyClassLoader().defineClassForName("cn.coderap.aop.aop3.$Proxy0", dump);
        Constructor<?> constructor = proxyClass.getConstructor(InvocationHandler.class);
        Target target = new Target();
        Foo proxy = (Foo) constructor.newInstance(new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.println("before...");
                Object ret = method.invoke(target, args);
                return ret;
            }
        });
        proxy.foo();
    }
}
