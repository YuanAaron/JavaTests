package cn.coderap.listener.eventpublisher;

import cn.coderap.listener.EmailApplicationListener;
import cn.coderap.listener.MyService;
import cn.coderap.listener.SmsApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class MyService2Config {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MyService2Config.class);
        context.getBean(MyService.class).register();
        context.close();
    }

    @Bean
    public ApplicationEventMulticaster applicationEventMulticaster(ConfigurableApplicationContext context, ThreadPoolTaskExecutor executor) {
        MyEventMulticaster myEventMulticaster = new MyEventMulticaster();
        myEventMulticaster.setContext(context);
        // 配置线程池
        myEventMulticaster.setExecutor(executor);
        return myEventMulticaster;
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
