package cn.coderap.listener;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyServiceConfig {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MyServiceConfig.class);
        context.getBean(MyService.class).register();
        context.close();
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
