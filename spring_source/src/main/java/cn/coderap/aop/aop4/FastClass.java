package cn.coderap.aop.aop4;

import org.springframework.cglib.core.Signature;

/**
 * 1. 该类的实现类用于避免反射调用，也是直接动态生成字节码
 * 2. MethodProxy.invoke和MethodProxy.invokeSuper会调用FastClass的这两个实现类的invoke方法，但是由于我这里并没有真正的继承cglib
 * 包中的FastClass，这是模拟了这两个实现类的实现原理，因此无法将Target$$EnhancerByCGLIB代理类和Target$$FastClassByCGLIB(或
 * Target$$EnhancerByCGLIB$$FastClassByCGLIB）这两个FastClass进行联调
 */
abstract public class FastClass {

    /**
     * 以Target为例，事先对其中的方法进行编号，这样就可以根据签名信息获取Target中方法的编号
     * @param signature: 包括方法名、参数、返回值
     */
    public abstract int getIndex(Signature signature);

    /**
     * 以Target为例，根据getIndex返回的方法编号，正常调用Target对象的对应方法
     */
    public abstract Object invoke(int index, Object obj, Object[] args);
}
