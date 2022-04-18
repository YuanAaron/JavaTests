package cn.coderap.lifecycle;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LifeCycleConfig {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(LifeCycleConfig.class);
        LifeCycleBean lifeCycleBean = context.getBean(LifeCycleBean.class);
        System.out.println(lifeCycleBean);
        context.close();
    }

    @Bean(initMethod = "customInit",destroyMethod = "customDestroy")
    public LifeCycleBean lifeCycleBean() {
        LifeCycleBean lifeCycleBean = new LifeCycleBean("示例1");
        return lifeCycleBean;
    }

    @Bean
    public MyBeanFactoryPostProcessor myBeanFactoryPostProcessor() {
        return new MyBeanFactoryPostProcessor();
    }

    @Bean
    public MyBeanPostProcessor myBeanPostProcessor() {
        return new MyBeanPostProcessor();
    }
}
