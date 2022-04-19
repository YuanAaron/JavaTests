package cn.coderap.postprocessor;

import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.annotation.InjectionMetadata;
import org.springframework.beans.factory.config.DependencyDescriptor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.ContextAnnotationAutowireCandidateResolver;
import cn.coderap.postprocessor.BeanPostProcessorImpl.*;
import org.springframework.core.MethodParameter;
import org.springframework.core.env.StandardEnvironment;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * AutowiredAnnotationPostProcessor 运行分析
 */
public class AutowiredSource {
    public static void main(String[] args) throws Throwable {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        // 两者的区别参考registerSingleton的注释：
        // registerSingleton(String beanName, Object singletonObject); // 给定的实例已经初始化完成，不会再执行任何初始化回调（尤其不会回调InitializingBean's afterPropertiesSet），也不会执行任何销毁回调（比如DisposableBean's destroy）
        // registerBeanDefinition(String beanName, BeanDefinition beanDefinition); //如果bean想要执行initialization或destruction的回调，就注册一个bean的定义而不是一个已经创建的bean
        beanFactory.registerSingleton("bean8",new Bean8());
        beanFactory.registerSingleton("bean9",new Bean9());
        beanFactory.registerSingleton("bean10",new Bean10());
        beanFactory.setAutowireCandidateResolver(new ContextAnnotationAutowireCandidateResolver()); // @Value解析器
        beanFactory.addEmbeddedValueResolver(new StandardEnvironment()::resolvePlaceholders); // ${}解析器 （这里的参数是函数式接口，因此可以用::）

        Bean7 bean7 = new Bean7(); // 对象实例被创建，但属性还没有被赋值
        // 属性赋值（依赖注入）
        AutowiredAnnotationBeanPostProcessor beanPostProcessor = new AutowiredAnnotationBeanPostProcessor(); // @Autowired
        beanPostProcessor.setBeanFactory(beanFactory); //@Autowired是根据类型从BeanFactory中找bean
        System.out.println(bean7);
//        beanPostProcessor.postProcessProperties(null, bean7,"bean7"); //非null表示不使用自动注入方式，而是人为指定每个属性的值
//        System.out.println(bean7);

        // 结论：从代码的执行结果可知，beanPostProcessor.postProcessProperties方法完成了依赖注入，因此在依赖注入阶段会解析@Autowired、@Value。

        // ---------------------注释掉上面的beanPostProcessor.postProcessProperties方法，探究postProcessProperties方法-------------------
        // 1.查找哪些属性、方法加了@Autowired，这称之为 InjectMetadata
        Method findAutowiredAnnotation = AutowiredAnnotationBeanPostProcessor.class.getDeclaredMethod("findAutowiringMetadata", String.class, Class.class, PropertyValues.class);
        findAutowiredAnnotation.setAccessible(true);
        InjectionMetadata metadata = (InjectionMetadata) findAutowiredAnnotation.invoke(beanPostProcessor, "bean7", Bean7.class, null);// 获取 bean7 上加了@Autowired、@Value的成员变量、方法参数信息
        System.out.println(metadata);
        // 2.调用 InjectMetadata的inject方法 来进行依赖注入，注入时按照类型查找值
//        metadata.inject(bean7, "bean7", null);
        System.out.println(bean7);
        // ----------------------注释掉metadata.inject方法，探究如何按照如何按类型查找值--------------
        // 3.1 @Autowired等注解加在属性上
        Field bean10 = Bean7.class.getDeclaredField("bean10");
        DependencyDescriptor dd = new DependencyDescriptor(bean10, false); // false表示非必须，依赖注入时在容器中找不到bean10不会报错，否则会抛出异常
        Object o = beanFactory.doResolveDependency(dd, null, null, null); // 根据成员变量bean10去容器中找要注入的对象，比较复杂，后续分析

        bean10.setAccessible(true);
        bean10.set(bean7,o);
        System.out.println(bean7); //bean10中的属性都为null是因为没有解析@ConfigurationProperties
        // 3.2 @Autowired等注解加在方法上
        Method setBean8 = Bean7.class.getDeclaredMethod("setBean8", Bean8.class);
        dd = new DependencyDescriptor(new MethodParameter(setBean8, 0), false); // setBean8方法的第1个参数
        o = beanFactory.doResolveDependency(dd, null, null, null);

        setBean8.invoke(bean7, o);
        System.out.println(bean7);

        Method setHome = Bean7.class.getDeclaredMethod("setHome", String.class);
        dd = new DependencyDescriptor(new MethodParameter(setHome, 0), false);
        o = beanFactory.doResolveDependency(dd, null, null, null);

        setHome.invoke(bean7, o);
        System.out.println(bean7);
    }
}
