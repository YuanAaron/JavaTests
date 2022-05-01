package cn.coderap.lifecycle.autowired;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * 用于@Autowired失效分析
 */
@Configuration
@Slf4j
public class AutowiredExpire {

    @Bean
    public AutowiredExpireBeanFactoryPostProcessor autowiredExpireBeanFactoryPostProcessor() {
        log.info(">>>Java配置类AutowiredExpire: AutowiredExpireBeanFactoryPostProcessor被Spring管理啦!");
        return new AutowiredExpireBeanFactoryPostProcessor();
    }

    @Bean
    public AutowiredExpireBeanPostProcessor autowiredExpireBeanPostProcessor() {
        log.info(">>>Java配置类AutowiredExpire: AutowiredExpireBeanFactoryPostProcessor被Spring管理啦!");
        return new AutowiredExpireBeanPostProcessor();
    }

    public AutowiredExpire() {
        log.info(">>>Java配置类AutowiredExpire: 构造方法");
    }

    @Autowired
    public void autowiredApplicationContext(ApplicationContext applicationContext) {
        log.info(">>>依赖注入：ApplicationContext");
    }

    @PostConstruct
    public void postConstruct() {
        log.info(">>>@PostConstruct");
    }


    @PreDestroy
    public void preDestroy() {
        log.info(">>>@PreDestroy");
    }
}
