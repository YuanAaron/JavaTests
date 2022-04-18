package cn.coderap.lifecycle;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        log.info("调用BeanFactoryPostProcessor的postProcessBeanFactory方法");
        BeanDefinition bd = beanFactory.getBeanDefinition("lifeCycleBean");
        bd.getPropertyValues().addPropertyValue("example","示例2");
    }
}
