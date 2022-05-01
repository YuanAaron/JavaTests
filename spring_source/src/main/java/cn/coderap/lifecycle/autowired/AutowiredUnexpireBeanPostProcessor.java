package cn.coderap.lifecycle.autowired;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.DestructionAwareBeanPostProcessor;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;

@Slf4j
public class AutowiredUnexpireBeanPostProcessor implements InstantiationAwareBeanPostProcessor, DestructionAwareBeanPostProcessor {

    public AutowiredUnexpireBeanPostProcessor() {
        log.info(">>>AutowiredUnexpireBeanPostProcessor的构造方法");
    }

    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        if (beanName.equals("autowiredUnexpire")) {
            log.info(">>>实例化之前执行，这里返回的对象会替换原本的 bean");
        }
        return null;
    }

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        if (beanName.equals("autowiredUnexpire")) {
            log.info(">>>实例化之后执行，这里如果返回 false 会跳过依赖注入阶段");
//            return false;
        }
        return true;
    }

    @Override
    public PropertyValues postProcessProperties(PropertyValues pvs, Object bean, String beanName) throws BeansException {
        if (beanName.equals("autowiredUnexpire")) {
            log.info(">>>依赖注入（属性赋值）之前执行，如 @Autowired、@Value、@Resource");
        }
        return pvs;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (beanName.equals("autowiredUnexpire")) {
            log.info(">>>初始化之前执行，这里返回的对象会替换掉原本的 bean，如 @PostConstruct、@ConfigurationProperties");
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (beanName.equals("autowiredUnexpire")) {
            log.info(">>>初始化之后执行,这里返回的对象会替换掉原本的 bean，如代理增强");
        }
        return bean;
    }

    @Override
    public void postProcessBeforeDestruction(Object bean, String beanName) throws BeansException {
        if (beanName.equals("autowiredUnexpire")) {
            log.info(">>>销毁之前执行，如 @PreDestroy");
        }
    }
}
