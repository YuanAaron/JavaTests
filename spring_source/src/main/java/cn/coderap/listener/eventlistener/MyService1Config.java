package cn.coderap.listener.eventlistener;

import cn.coderap.listener.MyService;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;

/**
 * 自定义@MyEventListener原理: @EventListener本质还是要被转换成ApplicationListener接口，
 * 因此我的操作是将所有被@MyEventListener修饰的方法的调用放到ApplicationListener接口的回调方法中，这里就是适配器模式的应用。
 */
@Configuration
public class MyService1Config {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MyService1Config.class);
        // 先以EmailApplicationListener1为例解析@MyEventListener
        EmailApplicationListener1 bean = context.getBean(EmailApplicationListener1.class);
        // 遍历bean中的所有方法
        for (Method method : bean.getClass().getMethods()) {
            // 如果该方法上有@MyEventListener,就new一个ApplicationListener放到context中
            if (method.isAnnotationPresent(MyEventListener.class)) {
                // 适配器模式
                ApplicationListener applicationListener = new ApplicationListener() {
                    @Override
                    public void onApplicationEvent(ApplicationEvent event) {
                        try {
                            method.invoke(bean, event);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                context.addApplicationListener(applicationListener);
            }
        }
        context.getBean(MyService.class).register();
        context.close();
    }

    @Bean
    public MyService myService() {
        return new MyService();
    }

    @Bean
    public EmailApplicationListener1 emailApplicationListener1() {
        return new EmailApplicationListener1();
    }

    @Bean
    public SmsApplicationListener1 smsApplicationListener1() {
        return new SmsApplicationListener1();
    }
}