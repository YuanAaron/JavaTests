package cn.coderap.aop.aop4;

import cn.coderap.aop.aop4.CglibProxyDemo.*;
import org.springframework.cglib.core.Signature;

public class Target$$FastClassByCGLIB extends FastClass {

    static Signature s0 = new Signature("foo","()Ljava/lang/String;");
    static Signature s1 = new Signature("foo","(I)Ljava/lang/String;");
    static Signature s2 = new Signature("foo","(J)Ljava/lang/String;");

    @Override
    public int getIndex(Signature signature) {
        if (s0.equals(signature)) {
            return 0;
        } else if (s1.equals(signature)) {
            return 1;
        } else if (s2.equals(signature)) {
            return 2;
        }
        return -1;
    }

    @Override
    public Object invoke(int index, Object obj, Object[] args) {
        // 事先约定对应关系
        if (index == 0) {
            return ((Target)obj).foo();
        } else if (index == 1) {
            return ((Target)obj).foo((int) args[0]);
        } else if (index == 2) {
            return ((Target)obj).foo((long) args[0]);
        }
        throw new RuntimeException("无此方法");
    }

    public static void main(String[] args) {
        Target$$FastClassByCGLIB fastClass = new Target$$FastClassByCGLIB();
        int index = fastClass.getIndex(new Signature("foo", "()Ljava/lang/String;"));
        System.out.println(fastClass.invoke(index, new Target(), new Object[0]));
        index = fastClass.getIndex(new Signature("foo", "(I)Ljava/lang/String;"));
        System.out.println(fastClass.invoke(index, new Target(), new Object[]{100}));
        index = fastClass.getIndex(new Signature("foo", "(J)Ljava/lang/String;"));
        System.out.println(fastClass.invoke(index, new Target(), new Object[]{100L}));
    }
}
