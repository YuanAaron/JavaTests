package cn.coderap.beanfactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.stream.Collectors;

/**
 * DefaultListableBeanFactory的使用
 */
public class BeanFactoryImpl {
    public static void main(String[] args) {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        // 添加BeanDefinition
        AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(BeanConfig.class).setScope("singleton").getBeanDefinition();
        beanFactory.registerBeanDefinition("beanConfig",beanDefinition);
        // 添加一些常用的BeanFactory后置处理器（用于补充BeanDefinition）来解析@Configuration和@Bean注解，Bean后置处理器（用于对bean生命周期的各个阶段提供扩展）来解析@Autowired注解
        // 1. 添加一些常用的BeanFactory后置处理器的BeanDefinition及Bean后者处理器的BeanDefinition（还有其他的）
        AnnotationConfigUtils.registerAnnotationConfigProcessors(beanFactory);
        // 2. 获取每个BeanFactory后置处理器Bean实例，然后调用ConfigurationClassPostProcessor实例的postProcessBeanFactory方法来解析@Configuration和@Bean注解
        // 其中: 前两个是BeanFactoryPostProcessor，中间两个是BeanPostProcessor，最后一个是其他
        // org.springframework.context.annotation.internalConfigurationAnnotationProcessor对应ConfigurationClassPostProcessor(解析@Configuration和@Bean注解)
        //org.springframework.context.event.internalEventListenerProcessor对应EventListenerMethodProcessor

        //org.springframework.context.annotation.internalAutowiredAnnotationProcessor对应AutowiredAnnotationBeanPostProcessor（解析@Autowired和@Value注解）
        //org.springframework.context.annotation.internalCommonAnnotationProcessor对应CommonAnnotationBeanPostProcessor（解析@Resource注解）

        //org.springframework.context.event.internalEventListenerFactory对应DefaultEventListenerFactory
        for (BeanFactoryPostProcessor beanFactoryPostProcessor : beanFactory.getBeansOfType(BeanFactoryPostProcessor.class).values()) {
//            if (beanFactoryPostProcessor instanceof ConfigurationClassPostProcessor) {
            beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);
//            }
        }
        // 3. 调用Bean后置处理器来解析@Autowired注解
        for (BeanPostProcessor beanPostProcessor: beanFactory.getBeansOfType(BeanPostProcessor.class).values().stream().sorted(beanFactory.getDependencyComparator()).collect(Collectors.toList())) {
//            if (beanPostProcessor instanceof AutowiredAnnotationBeanPostProcessor) {
            beanFactory.addBeanPostProcessor(beanPostProcessor);
//            }
        }

        // 获取容器中已注册的BeanDefinition
        for (String beanDefinitionName : beanFactory.getBeanDefinitionNames()) {
            System.out.println(beanDefinitionName);
        }

        // 准备好所有的单例对象，即单例对象在容器启动时就创建而非懒加载
        // 添加该语句会报异常：Exception in thread "main" java.lang.IllegalStateException: No ConfigurableListableBeanFactory set
        // 解决办法是 在BeanFactoryPostProcessor调用时，虽然@Configuration和@Bean注解的解析只需要ConfigurationClassPostProcessor，
        // 但是这里会用到EventListenerMethodProcessor
        beanFactory.preInstantiateSingletons();

        System.out.println(beanFactory.getBean(Bean1.class).getBean2());
        System.out.println(beanFactory.getBean(Bean1.class).getInter());
    }

    @Configuration
    static class BeanConfig {

        @Bean
        public Bean1 bean1() {
            return  new Bean1();
        }

        @Bean
        public Bean2 bean2() {
            return  new Bean2();
        }

        @Bean
        public Bean3 bean3() {
            return  new Bean3();
        }

        @Bean
        public Bean4 bean4() {
            return  new Bean4();
        }
    }

    static class Bean1 {

        @Autowired
        private Bean2 bean2;

        @Autowired
        @Resource(name = "bean4")
        private Inter bean3;

        public Bean1() {
            System.out.println("构造 bean1");
        }

        public Bean2 getBean2() {
            return bean2;
        }

        public Inter getInter() {
            return bean3;
        }
    }

    static class Bean2 {

        public Bean2() {
            System.out.println("构造 bean2");
        }

    }

    interface Inter {

    }

    static class Bean3 implements Inter {

    }

    static class Bean4 implements Inter {

    }
}
