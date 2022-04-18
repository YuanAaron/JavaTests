package cn.coderap.lifecycle;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Slf4j
//@Component
public class LifeCycleBean implements BeanNameAware, BeanFactoryAware,
        ApplicationContextAware, InitializingBean, DisposableBean, SmartInitializingSingleton {

    private String example;

    public LifeCycleBean(String example) {
        log.info("构造方法: {}",example);
        this.example = example;
    }

    @Autowired
    public void autowired(@Value("${JAVA_HOME}") String home) {
        log.info("依赖注入：{}",home);
    }

    @Override
    public void setBeanName(String name) {
        log.info("setBeanName");
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        log.info("setBeanFactory");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        log.info("setApplicationContext");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("afterPropertiesSet");
    }

    @PostConstruct
    public void postConstruct() {
        log.info("@PostConstruct");
    }

    @Override
    public void destroy() throws Exception {
        log.info("destroy");
    }

    @PreDestroy
    public void preDestroy() {
        log.info("@PreDestroy");
    }

    public void customInit() {
        log.info("custom init");
    }

    public void customDestroy() {
        log.info("custom destroy");
    }

    @Override
    public void afterSingletonsInstantiated() {
        log.info("afterSingletonsInstantiated");
    }

    // ----------------set和toString方法------------
    public void setExample(String example) {
        log.info("将LifeCycle实例的example属性值从'示例1'修改为'示例2'");
        this.example = example;
    }

    @Override
    public String toString() {
        return "LifeCycleBean{" +
                "example='" + example + '\'' +
                '}';
    }
}
