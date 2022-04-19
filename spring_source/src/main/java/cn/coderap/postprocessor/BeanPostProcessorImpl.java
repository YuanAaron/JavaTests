package cn.coderap.postprocessor;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesBindingPostProcessor;
import org.springframework.context.annotation.CommonAnnotationBeanPostProcessor;
import org.springframework.context.annotation.ContextAnnotationAutowireCandidateResolver;
import org.springframework.context.support.GenericApplicationContext;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

/**
 * 常见的Bean后置处理器
 */
@Slf4j
public class BeanPostProcessorImpl {

    public static void main(String[] args) {
        // GenericApplicationContext 是一个 【纯净】的容器
        // 与AnnotationConfigApplicationContext相比：创建时就添加并调用各种Bean后置处理器和BeanFactory后置处理器
        // 与DefaultListableBeanFactory相比：两者创建时添加Bean后置处理器和BeanFactory后置处理器，如果人为添加这些后置处理器后，
        // GenericApplicationContext提供了refresh方法来调用这些后置处理器，而不用手动去调用
        // 注意：refresh方法会执行BeanFactory后置处理器、添加（执行）Bean后置处理器，也会初始化所有单例bean。
        GenericApplicationContext context = new GenericApplicationContext();

        // 用原始方法注册三个bean
        context.registerBean("bean7", Bean7.class);
        context.registerBean("bean8", Bean8.class);
        context.registerBean("bean9", Bean9.class);
        context.registerBean("bean10", Bean10.class);

        // 添加Bean后置处理器
        context.getDefaultListableBeanFactory().setAutowireCandidateResolver(new ContextAnnotationAutowireCandidateResolver()); //@Value注解需要，不懂！！！
        context.registerBean(AutowiredAnnotationBeanPostProcessor.class); //解析@Autowired和@Value注解
        context.registerBean(CommonAnnotationBeanPostProcessor.class); // 解析@Resource、@PostConstruct、@PreDestroy
        ConfigurationPropertiesBindingPostProcessor.register(context.getDefaultListableBeanFactory()); // 解析@ConfigurationProperties

        // 初始化容器：执行BeanFactory后置处理器，添加（执行）bean后置处理器，初始化所有单例
        context.refresh();

        System.out.println(context.getBean(Bean10.class));

        // 销毁容器
        context.close();

    }

    /**
     * 用于演示各种注解的解析对应的BeanPostProcessor
     */
    static class Bean7 {
        private Bean8 bean8;
        private String home;
        private Bean9 bean9;
        @Autowired
        private Bean10 bean10;

        public Bean7() {
            System.out.println("构造Bean7...");
        }

        @Autowired
        public void setBean8(Bean8 bean8) {
            System.out.println("@Autowired 生效：" + bean8);
            this.bean8 = bean8;
        }

        /**
         * @Autowired注解加在方法上是对方法的参数进行注入，这样可以在注入时打印日志信息，值得注意的是：
         * 1. 当参数为类似Bean8时，就是普通的依赖注入；
         * 2. 当参数为String类型时，不会将其当成一个bean进行依赖注入，这时可以使用@Value将其当成一个值注入，即从配置文件去找键值信息，
         * 所以对于String类型，@Autowired和@Value要配合使用
         */
        @Autowired
        public void setHome(@Value("${JAVA_HOME}") String home) {
            System.out.println("@Value 生效：" + home);
            this.home = home;
        }

        @Resource
        public void setBean9(Bean9 bean9) {
            System.out.println("@Resource 生效：" + bean9);
            this.bean9 = bean9;
        }

        @PostConstruct
        public void init() {
            System.out.println("@PostConstruct 生效");
        }

        @PreDestroy
        public void destroy() {
            System.out.println("@PreDestroy 生效");
        }

        @Override
        public String toString() {
            return "Bean7{" +
                    "bean8=" + bean8 +
                    ", home='" + home + '\'' +
                    ", bean9=" + bean9 +
                    ", bean10=" + bean10 +
                    '}';
        }
    }

    static class Bean8 {
        public Bean8() {
            System.out.println("构造Bean8...");
        }
    }
    static class Bean9 {
        public Bean9() {
            System.out.println("构造Bean9...");
        }
    }

    @ConfigurationProperties(prefix = "java")
    @Data
     static class Bean10 {
        private String home;
        private String version;
    }
}
