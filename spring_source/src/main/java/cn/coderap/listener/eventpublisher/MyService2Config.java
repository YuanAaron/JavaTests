package cn.coderap.listener.eventpublisher;

import cn.coderap.listener.EmailApplicationListener;
import cn.coderap.listener.MyService;
import cn.coderap.listener.SmsApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ApplicationEventMulticaster;

@Configuration
public class MyService2Config {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MyService2Config.class);
        context.getBean(MyService.class).register();
        context.close();
    }

    @Bean
    public ApplicationEventMulticaster applicationEventMulticaster(ConfigurableApplicationContext context) {
        MyEventMulticaster myEventMulticaster = new MyEventMulticaster();
        myEventMulticaster.setContext(context);
        return myEventMulticaster;
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
