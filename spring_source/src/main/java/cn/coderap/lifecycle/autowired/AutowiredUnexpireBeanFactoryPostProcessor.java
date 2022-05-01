package cn.coderap.lifecycle.autowired;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

@Slf4j
public class AutowiredUnexpireBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    public AutowiredUnexpireBeanFactoryPostProcessor() {
        log.info(">>>AutowiredUnexpireBeanFactoryPostProcessor的构造方法");
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        log.info(">>>调用BeanFactoryPostProcessor的postProcessBeanFactory方法");
    }
}
