package cn.coderap.aop.aop4;

import org.springframework.cglib.core.Signature;

public class Target$$EnhancerByCGLIB$$FastClassByCGLIB extends FastClass {

    static Signature s0 = new Signature("fooSuper","()Ljava/lang/String;");
    static Signature s1 = new Signature("fooSuper","(I)Ljava/lang/String;");
    static Signature s2 = new Signature("fooSuper","(J)Ljava/lang/String;");

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
            return ((Target$$EnhancerByCGLIB)obj).fooSuper();
        } else if (index == 1) {
            return ((Target$$EnhancerByCGLIB)obj).fooSuper((int) args[0]);
        } else if (index == 2) {
            return ((Target$$EnhancerByCGLIB)obj).fooSuper((long) args[0]);
        }
        throw new RuntimeException("无此方法");
    }

    public static void main(String[] args) {
        Target$$EnhancerByCGLIB$$FastClassByCGLIB fastClass = new Target$$EnhancerByCGLIB$$FastClassByCGLIB();
        int index = fastClass.getIndex(new Signature("fooSuper", "()Ljava/lang/String;"));
        System.out.println(fastClass.invoke(index, new Target$$EnhancerByCGLIB(), new Object[0]));
        index = fastClass.getIndex(new Signature("fooSuper", "(I)Ljava/lang/String;"));
        System.out.println(fastClass.invoke(index, new Target$$EnhancerByCGLIB(), new Object[]{100}));
        index = fastClass.getIndex(new Signature("fooSuper", "(J)Ljava/lang/String;"));
        System.out.println(fastClass.invoke(index, new Target$$EnhancerByCGLIB(), new Object[]{100L}));
    }
}
