package cn.coderap.listener;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class MyServiceConfig {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MyServiceConfig.class);
        context.getBean(MyService.class).register();
        context.close();
    }

    // 配置有线程池的SimpleApplicationEventMulticaster类型的bean会覆盖掉正常情况下未配置线程迟的该bean
    @Bean
    public SimpleApplicationEventMulticaster applicationEventMulticaster(@Qualifier("taskExecutor") ThreadPoolTaskExecutor taskExecutor) {
        SimpleApplicationEventMulticaster multicaster = new SimpleApplicationEventMulticaster();
        // 配置线程池
        multicaster.setTaskExecutor(taskExecutor);
        return multicaster;
    }


    @Bean
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(3);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(100);
        return executor;
    }

    @Bean
    public MyService myService() {
        return new MyService();
    }

    @Bean
    public EmailApplicationListener emailApplicationListener() {
        return new EmailApplicationListener();
    }

    @Bean
    public SmsApplicationListener smsApplicationListener() {
        return new SmsApplicationListener();
    }


}
