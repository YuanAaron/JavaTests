package cn.coderap.lifecycle.autowired;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;

/**
 * @Autowired失效时的修正方案
 */
@Slf4j
public class AutowiredUnexpire implements ApplicationContextAware, InitializingBean, DisposableBean{

    @Bean
    public AutowiredUnexpireBeanFactoryPostProcessor autowiredUnexpireBeanFactoryPostProcessor() {
        log.info(">>>Java配置类AutowiredUnexpire: AutowiredUnexpireBeanFactoryPostProcessor被Spring管理啦!");
        return new AutowiredUnexpireBeanFactoryPostProcessor();
    }

    @Bean
    public AutowiredUnexpireBeanPostProcessor autowiredUnexpireBeanPostProcessor() {
        log.info(">>>Java配置类AutowiredUnexpire: AutowiredUnexpireBeanFactoryPostProcessor被Spring管理啦!");
        return new AutowiredUnexpireBeanPostProcessor();
    }

    public AutowiredUnexpire() {
        log.info(">>>Java配置类AutowiredUnexpire: 构造方法");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        log.info(">>>setApplicationContext: 当前bean所在的容器是 " + applicationContext);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        log.info(">>>afterPropertiesSet: 当前bean的初始化");
    }

    @Override
    public void destroy() throws Exception {
        log.info(">>>destroy");
    }
}
