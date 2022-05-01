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

/**
 * Aware接口：用于注入一些与容器相关的信息，比如：
 *  1. BeanNameAware: 注入bean的名字
 *  2. BeanFactoryAware：注入BeanFactory容器
 *  3. ApplicationContextAware：注入ApplicationContext容器
 *  4. EmbeddedValueResolverAware: ${}
 *
 * InitializingBean接口：添加初始化方法
 *
 * 疑问：上面2、3、4的功能用@Autowired就能实现，为哈还需要Aware接口呢？
 * 原因：@Autowired的解析要用到bean后处理器，属于扩展功能（在某些情况下，扩展功能会失效，而内置功能不会失效）；
 * 而Aware接口属于内置功能，不加任何扩展Spring就能识别。
 * 情况一：当使用GenericApplicationContext，由于其未添加AutowiredAnnotationBeanPostProcessor（bean后置处理器），
 * @Autowired 无法被解析，因此就会失效，而内置功能不会失效。
 * 情况二：即使正确的加入了一些后处理器，Java配置类在添加了自定义的BeanFactory后处理器后，用传统接口方式的注入仍然成功，但是@Autowired方式注入失败。
 * （原因：Java配置类在不包含BeanFactoryPostProcessor的情况下，先执行BeanFactoryPostProcessor，再注册BeanPostProcessor，最后实例化、初始化；当Java配置类包含
 * BeanFactoryPostProcessor的情况下，要创建其中的BeanFactoryPostProcessor必须提前创建Java配置类，而在创建Java配置类时BeanPostProcessor还未准备好，导致@Autowired等注解失效。
 * 解决方案：使用内置的Aware接口和InitializingBean接口）
 */
@Slf4j
public class LifeCycleBean implements BeanNameAware, BeanFactoryAware,
        ApplicationContextAware, InitializingBean, DisposableBean, SmartInitializingSingleton {

    private String example;

    public LifeCycleBean(String example) {
        log.info(">>>LifeCycleBean的构造方法: {}",example);
        this.example = example;
    }

    @Autowired
    public void autowired(@Value("${JAVA_HOME}") String home) {
        log.info(">>>依赖注入：{}",home);
    }

    @Override
    public void setBeanName(String name) {
        log.info(">>>setBeanName: 当前bean的名字是 " + name);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        log.info(">>>setBeanFactory");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        log.info(">>>setApplicationContext: 当前bean所在的容器是 " + applicationContext);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info(">>>afterPropertiesSet: 当前bean的初始化");
    }

    @PostConstruct
    public void postConstruct() {
        log.info(">>>@PostConstruct");
    }

    @Override
    public void destroy() throws Exception {
        log.info(">>>destroy");
    }

    @PreDestroy
    public void preDestroy() {
        log.info(">>>@PreDestroy");
    }

    public void customInit() {
        log.info(">>>custom init");
    }

    public void customDestroy() {
        log.info(">>>custom destroy");
    }

    @Override
    public void afterSingletonsInstantiated() {
        log.info(">>>afterSingletonsInstantiated");
    }

    // ----------------set和toString方法------------
    public void setExample(String example) {
        log.info(">>>将LifeCycle实例的example属性值从'示例1'修改为'示例2'");
        this.example = example;
    }

    @Override
    public String toString() {
        return ">>>LifeCycleBean{" +
                "example='" + example + '\'' +
                '}';
    }
}
