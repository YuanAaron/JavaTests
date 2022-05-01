package cn.coderap.lifecycle;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class LifeCycleConfig {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(LifeCycleConfig.class);
        LifeCycleBean lifeCycleBean = context.getBean(LifeCycleBean.class);
        System.out.println(lifeCycleBean);
        context.close();
    }

    public LifeCycleConfig() {
        log.info(">>>Java配置类LifeCycleConfig: 构造方法");
    }

    @Bean(initMethod = "customInit",destroyMethod = "customDestroy")
    public LifeCycleBean lifeCycleBean() {
        log.info(">>>Java配置类LifeCycleConfig: LifeCycleBean被Spring管理啦!");
        LifeCycleBean lifeCycleBean = new LifeCycleBean("示例1");
        return lifeCycleBean;
    }

    @Bean
    public MyBeanFactoryPostProcessor myBeanFactoryPostProcessor() {
        log.info(">>>Java配置类LifeCycleConfig: MyBeanFactoryPostProcessor被Spring管理啦!");
        return new MyBeanFactoryPostProcessor();
    }

    @Bean
    public MyBeanPostProcessor myBeanPostProcessor() {
        log.info(">>>Java配置类LifeCycleConfig: MyBeanPostProcessor被Spring管理啦!");
        return new MyBeanPostProcessor();
    }
}
