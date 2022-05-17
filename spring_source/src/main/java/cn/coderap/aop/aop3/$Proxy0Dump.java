package cn.coderap.aop.aop3;

import org.springframework.asm.*;

/**
 * 通过ASM API生成class字节码：
 * 如果你对ASM API熟悉，当然可以直接用ASM API生成class字节码（Jdk动态代理就是这样生成的），但我这里是
 * 手写$Proxy0代理类，然后借助插件asm bytecode viewer插件生成
 */
public class $Proxy0Dump implements Opcodes {

    /**
     * 通过ASM API生成class字节码
     */
    public static byte[] dump() throws Exception {
        ClassWriter classWriter = new ClassWriter(0);
        FieldVisitor fieldVisitor;
        MethodVisitor methodVisitor;
        AnnotationVisitor annotationVisitor0;

        classWriter.visit(V11, ACC_PUBLIC | ACC_SUPER, "cn/coderap/aop/aop3/$Proxy0", null, "java/lang/reflect/Proxy", new String[]{"cn/coderap/aop/aop3/Foo"});

        classWriter.visitSource("$Proxy0.java", null);

        {
            fieldVisitor = classWriter.visitField(ACC_PRIVATE | ACC_STATIC, "foo", "Ljava/lang/reflect/Method;", null, null);
            fieldVisitor.visitEnd();
        }
        {
            methodVisitor = classWriter.visitMethod(ACC_PUBLIC, "<init>", "(Ljava/lang/reflect/InvocationHandler;)V", null, null);
            methodVisitor.visitParameter("h", 0);
            methodVisitor.visitCode();
            Label label0 = new Label();
            methodVisitor.visitLabel(label0);
            methodVisitor.visitLineNumber(22, label0);
            methodVisitor.visitVarInsn(ALOAD, 0);
            methodVisitor.visitVarInsn(ALOAD, 1);
            methodVisitor.visitMethodInsn(INVOKESPECIAL, "java/lang/reflect/Proxy", "<init>", "(Ljava/lang/reflect/InvocationHandler;)V", false);
            Label label1 = new Label();
            methodVisitor.visitLabel(label1);
            methodVisitor.visitLineNumber(23, label1);
            methodVisitor.visitInsn(RETURN);
            Label label2 = new Label();
            methodVisitor.visitLabel(label2);
            methodVisitor.visitLocalVariable("this", "Lcn/coderap/aop/aop3/$Proxy0;", null, label0, label2, 0);
            methodVisitor.visitLocalVariable("h", "Ljava/lang/reflect/InvocationHandler;", null, label0, label2, 1);
            methodVisitor.visitMaxs(2, 2);
            methodVisitor.visitEnd();
        }
        {
            methodVisitor = classWriter.visitMethod(ACC_PUBLIC, "foo", "()V", null, null);
            methodVisitor.visitCode();
            Label label0 = new Label();
            Label label1 = new Label();
            Label label2 = new Label();
            methodVisitor.visitTryCatchBlock(label0, label1, label2, "java/lang/RuntimeException");
            methodVisitor.visitTryCatchBlock(label0, label1, label2, "java/lang/Error");
            Label label3 = new Label();
            methodVisitor.visitTryCatchBlock(label0, label1, label3, "java/lang/Throwable");
            methodVisitor.visitLabel(label0);
            methodVisitor.visitLineNumber(28, label0);
            methodVisitor.visitVarInsn(ALOAD, 0);
            methodVisitor.visitFieldInsn(GETFIELD, "cn/coderap/aop/aop3/$Proxy0", "h", "Ljava/lang/reflect/InvocationHandler;");
            methodVisitor.visitVarInsn(ALOAD, 0);
            methodVisitor.visitFieldInsn(GETSTATIC, "cn/coderap/aop/aop3/$Proxy0", "foo", "Ljava/lang/reflect/Method;");
            methodVisitor.visitInsn(ACONST_NULL);
            methodVisitor.visitMethodInsn(INVOKEINTERFACE, "java/lang/reflect/InvocationHandler", "invoke", "(Ljava/lang/Object;Ljava/lang/reflect/Method;[Ljava/lang/Object;)Ljava/lang/Object;", true);
            methodVisitor.visitInsn(POP);
            methodVisitor.visitLabel(label1);
            methodVisitor.visitLineNumber(33, label1);
            Label label4 = new Label();
            methodVisitor.visitJumpInsn(GOTO, label4);
            methodVisitor.visitLabel(label2);
            methodVisitor.visitLineNumber(29, label2);
            methodVisitor.visitFrame(Opcodes.F_SAME1, 0, null, 1, new Object[]{"java/lang/Throwable"});
            methodVisitor.visitVarInsn(ASTORE, 1);
            Label label5 = new Label();
            methodVisitor.visitLabel(label5);
            methodVisitor.visitLineNumber(30, label5);
            methodVisitor.visitVarInsn(ALOAD, 1);
            methodVisitor.visitInsn(ATHROW);
            methodVisitor.visitLabel(label3);
            methodVisitor.visitLineNumber(31, label3);
            methodVisitor.visitFrame(Opcodes.F_SAME1, 0, null, 1, new Object[]{"java/lang/Throwable"});
            methodVisitor.visitVarInsn(ASTORE, 1);
            Label label6 = new Label();
            methodVisitor.visitLabel(label6);
            methodVisitor.visitLineNumber(32, label6);
            methodVisitor.visitTypeInsn(NEW, "java/lang/reflect/UndeclaredThrowableException");
            methodVisitor.visitInsn(DUP);
            methodVisitor.visitVarInsn(ALOAD, 1);
            methodVisitor.visitMethodInsn(INVOKESPECIAL, "java/lang/reflect/UndeclaredThrowableException", "<init>", "(Ljava/lang/Throwable;)V", false);
            methodVisitor.visitInsn(ATHROW);
            methodVisitor.visitLabel(label4);
            methodVisitor.visitLineNumber(34, label4);
            methodVisitor.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
            methodVisitor.visitInsn(RETURN);
            Label label7 = new Label();
            methodVisitor.visitLabel(label7);
            methodVisitor.visitLocalVariable("e", "Ljava/lang/Throwable;", null, label5, label3, 1);
            methodVisitor.visitLocalVariable("e", "Ljava/lang/Throwable;", null, label6, label4, 1);
            methodVisitor.visitLocalVariable("this", "Lcn/coderap/aop/aop3/$Proxy0;", null, label0, label7, 0);
            methodVisitor.visitMaxs(4, 2);
            methodVisitor.visitEnd();
        }
        {
            methodVisitor = classWriter.visitMethod(ACC_STATIC, "<clinit>", "()V", null, null);
            methodVisitor.visitCode();
            Label label0 = new Label();
            Label label1 = new Label();
            Label label2 = new Label();
            methodVisitor.visitTryCatchBlock(label0, label1, label2, "java/lang/NoSuchMethodException");
            methodVisitor.visitLabel(label0);
            methodVisitor.visitLineNumber(15, label0);
            methodVisitor.visitLdcInsn(Type.getType("Lcn/coderap/aop/aop3/Foo;"));
            methodVisitor.visitLdcInsn("foo");
            methodVisitor.visitInsn(ICONST_0);
            methodVisitor.visitTypeInsn(ANEWARRAY, "java/lang/Class");
            methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/Class", "getMethod", "(Ljava/lang/String;[Ljava/lang/Class;)Ljava/lang/reflect/Method;", false);
            methodVisitor.visitFieldInsn(PUTSTATIC, "cn/coderap/aop/aop3/$Proxy0", "foo", "Ljava/lang/reflect/Method;");
            methodVisitor.visitLabel(label1);
            methodVisitor.visitLineNumber(18, label1);
            Label label3 = new Label();
            methodVisitor.visitJumpInsn(GOTO, label3);
            methodVisitor.visitLabel(label2);
            methodVisitor.visitLineNumber(16, label2);
            methodVisitor.visitFrame(Opcodes.F_SAME1, 0, null, 1, new Object[]{"java/lang/NoSuchMethodException"});
            methodVisitor.visitVarInsn(ASTORE, 0);
            Label label4 = new Label();
            methodVisitor.visitLabel(label4);
            methodVisitor.visitLineNumber(17, label4);
            methodVisitor.visitTypeInsn(NEW, "java/lang/NoSuchMethodError");
            methodVisitor.visitInsn(DUP);
            methodVisitor.visitVarInsn(ALOAD, 0);
            methodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/lang/NoSuchMethodException", "getMessage", "()Ljava/lang/String;", false);
            methodVisitor.visitMethodInsn(INVOKESPECIAL, "java/lang/NoSuchMethodError", "<init>", "(Ljava/lang/String;)V", false);
            methodVisitor.visitInsn(ATHROW);
            methodVisitor.visitLabel(label3);
            methodVisitor.visitLineNumber(19, label3);
            methodVisitor.visitFrame(Opcodes.F_SAME, 0, null, 0, null);
            methodVisitor.visitInsn(RETURN);
            methodVisitor.visitLocalVariable("e", "Ljava/lang/NoSuchMethodException;", null, label4, label3, 0);
            methodVisitor.visitMaxs(3, 1);
            methodVisitor.visitEnd();
        }
        classWriter.visitEnd();

        return classWriter.toByteArray();
    }
}
