package cn.coderap.aop;

/**
 * 用aspectj编译器实现AOP（编译阶段，不推荐），原理是改写了目标类（Service1）的class文件
 * 具体操作：
 * 1. 在pom.xml中添加aspectj-maven-plugin插件
 * 2. 通过maven->Lifecycle->compile进行编译，这样才能调用aspectj编译器
 * 3. 最后直接运行即可（注意：使用idea需要勾选Do not build before run，否则会重新编译，覆盖已做的aspectj编译)。
 *
 * 该AOP实现的优势：
 * 可以突破代理的限制，由于代理增强的本质是方法重写，这样静态方法无法被增强，但aspectj编译器可以实现静态方法的增强。
 *
 */
public class Aop1 {
    public static void main(String[] args) {
        new Service1().foo();
        Service1.bar();
    }
}
