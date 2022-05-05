package cn.coderap.lifecycle.singletonAndPrototype;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 * singleton注入其他scope类型的bean是有问题的，这里以singleton（E）注入prototype（F）类型为例：
 * 我们期望的e.getF1()的结果是不同对象，但是实际结果是相同对象，即单例中注入多例时，多例的配置失效问题。
 *
 * 原因：对于单例对象来说，依赖注入仅发生了一次，后续再没有用到多例的F，因此E用的始终是第一次依赖注入的F。
 *
 * 解决方法：
 * 1. 使用@Lazy生成代理（注入的是代理对象）：代理对象（F1的子类）虽然还是同一个，但每次使用代理对象的任意方法时（这里调用代理对象的toString方法），由代理对象在方法内创建新的F对象。
 * 2. 在目标类(F2）的scope注解中添加proxyMode属性：原理也是生成一个代理对象。
 * 3. 注入一个泛型为F3的ObjectFactory：通过工厂识别到F3是一个多例，这样每次获取时就创建一个F3。
 * 4. 注入ApplicationContext容器：context.getBean(F4.class)
 */
@ComponentScan("cn.coderap.lifecycle.singletonAndPrototype")
@Slf4j
public class SAPApplication {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SAPApplication.class);
        E e = context.getBean(E.class);

        log.info("{}",e.getF1().getClass()); // F1$$EnhancerBySpringCGLIB$$b870e45d 被Spring Cglib增强后的代理对象
        log.info("{}",e.getF1()); // 打印日志会调用F1的toString方法
        log.info("{}",e.getF1());

        log.info("{}",e.getF2().getClass()); // F2$$EnhancerBySpringCGLIB$$1185e47 被Spring Cglib增强后的代理对象
        log.info("{}",e.getF2());
        log.info("{}",e.getF2());

        log.info("{}",e.getF3());
        log.info("{}",e.getF3());

        log.info("{}",e.getF4());
        log.info("{}",e.getF4());
    }
}
