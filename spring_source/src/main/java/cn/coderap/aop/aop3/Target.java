package cn.coderap.aop.aop3;

public class Target implements Foo{
    @Override
    public void foo() {
        System.out.println("target: foo");
    }
}
