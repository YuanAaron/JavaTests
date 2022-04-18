package cn.coderap.listener.eventlistener;

import cn.coderap.listener.MyService;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
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
        context.getBean(MyService.class).register();
        context.close();
    }

    // 方法的回调在Bean生命周期的初始化后阶段之后执行
    @Bean
    public SmartInitializingSingleton smartInitializingSingleton(ConfigurableApplicationContext context) {
        return new SmartInitializingSingleton() {
            @Override
            public void afterSingletonsInstantiated() {
                for (String name : context.getBeanDefinitionNames()) {
                    Object bean = context.getBean(name);
                    // 遍历bean中的所有方法
                    for (Method method : bean.getClass().getMethods()) {
                        // 如果该方法上有@MyEventListener,就new一个ApplicationListener放到context中
                        if (method.isAnnotationPresent(MyEventListener.class)) {
                            // 适配器模式
                            ApplicationListener applicationListener = new ApplicationListener() {
                                @Override
                                public void onApplicationEvent(ApplicationEvent event) {
                                    System.out.println(">>>" + event);
                                    // 报错：method.invoke(bean, event);
                                    // 原因：新添加的这个ApplicationListener可以监听任何ApplicationEvent事件（这点从onApplicationEvent方法的参数可知），
                                    // 当发送的事件为UserRegisterEvent1类型时，不会报错，否则会报错。比如context.close()会发送ContextClosedEvent事件，
                                    // 在调用EmailApplicationListener1的bbb方法时，传递的参数是ContextClosedEvent类型，因此报错。
                                    // 解决方案：当发送的事件类型和@MyEventListener方法的参数类型一致时，再调用method.invoke(bean, event)方法

                                    // 监听器方法需要的事件类型
                                    Class<?> eventType = method.getParameterTypes()[0];
                                    // 如果event.getClass可以赋值给eventType
                                    if (eventType.isAssignableFrom(event.getClass())) {
                                        try {
                                            method.invoke(bean, event);
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            };
                            context.addApplicationListener(applicationListener);
                        }
                    }
                }
            }
        };
    }

    @Bean
    public MyService myService1() {
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
