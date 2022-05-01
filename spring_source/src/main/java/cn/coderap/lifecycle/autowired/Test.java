package cn.coderap.lifecycle.autowired;

import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.context.annotation.CommonAnnotationBeanPostProcessor;
import org.springframework.context.annotation.ConfigurationClassPostProcessor;
import org.springframework.context.support.GenericApplicationContext;

public class Test {
//    public static void main(String[] args) {
//        GenericApplicationContext context = new GenericApplicationContext();
//
//        context.registerBean(AutowiredExpire.class);
//        context.registerBean(AutowiredAnnotationBeanPostProcessor.class); // 解析@Autowired
//        context.registerBean(CommonAnnotationBeanPostProcessor.class); // 解析@PostConstruct、@PreDestroy
//        context.registerBean(ConfigurationClassPostProcessor.class); // 解析@Configuration、@Bean
//
//        context.refresh();
//        context.close();
//    }

    public static void main(String[] args) {
        GenericApplicationContext context = new GenericApplicationContext();

        context.registerBean(AutowiredUnexpire.class);
        context.registerBean(ConfigurationClassPostProcessor.class); // 解析@Configuration、@Bean

        context.refresh();
        context.close();

    }
}
