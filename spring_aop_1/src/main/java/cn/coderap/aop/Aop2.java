package cn.coderap.aop;

import java.io.IOException;

/**
 * 通过添加VM参数-javaagent实现AOP（类加载阶段，不推荐）,原理是在类加载阶段修改class字节码，对代码进行增强。
 *
 * 具体操作：
 * 添加VM参数：-javaagent:/Users/user/.m2/repository/org/aspectj/aspectjweaver/1.9.5/aspectjweaver-1.9.5.jar
 *
 * 该AOP实现的优势：
 * 可以突破代理的限制，这里进行了bar的增强，因为在类加载阶段直接修改了Service2的字节码。
 * 如果使用代理，目标类（Service2)的一个方法（foo）调用了自己的另外一个方法（bar)，内部的这个方法（bar) 是不会走代理增强的，因为该方法是通过this调用的。
 *
 * 打开编译后Service2.class文件，双击以后idea会反编译该字节码文件，可以看到foo()、bar()方法体中并没有添加增强的的代码，所以就不是编译时增强了，
 * 而是类加载的时候增强的，增强后的Service2类可以借助阿里巴巴的Arthas工具：
 * 1. 进入Arthas的bin目录
 * 2. java -jar arthas-boot.jar
 * 3. 在输出的进程列表中找到我们要连接的进程，输入对应的序号
 * 4. 连接上以后会打印Arthas的logo（Arthas功能很多，连接上后可以通过help查看）
 * 5. 最后，输入jad cn.coderap.aop.Service2反编译内存中的Service2类
 */
public class Aop2 {
    public static void main(String[] args) throws IOException {
        new Service2().foo();
        System.in.read();
    }
}
