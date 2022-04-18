package cn.coderap.listener.eventpublisher;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.event.GenericApplicationListener;
import org.springframework.core.ResolvableType;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

public class MyEventMulticaster implements ApplicationEventMulticaster {

    private List<GenericApplicationListener> listeners = new ArrayList<>();
    private ConfigurableApplicationContext context;
    private ThreadPoolTaskExecutor executor;

    public void setContext(ConfigurableApplicationContext context) {
        this.context = context;
    }

    public void setExecutor(ThreadPoolTaskExecutor executor) {
        this.executor = executor;
    }

    @Override
    public void addApplicationListener(ApplicationListener<?> listener) {
        System.out.println();
    }

    // 收集监听器：事件广播器需要知道所有的事件监听器
    @Override
    public void addApplicationListenerBean(String listenerBeanName) {
        ApplicationListener listener = context.getBean(listenerBeanName, ApplicationListener.class);
        System.out.println(listener);

        // 获取该监听器支持的事件类型
        ResolvableType type = ResolvableType.forClass(listener.getClass()).getInterfaces()[0].getGeneric(0);
        System.out.println("该监听器支持的事件类型为：" + type);
        // 将原始 ApplicationListener 封装为支持事件类型检查的 GenericApplicationListener（后者是前者的子类）
        MyGenericApplicationListener myGenericApplicationListener = new MyGenericApplicationListener(listener, type);

        listeners.add(myGenericApplicationListener);
    }

    @Override
    public void removeApplicationListener(ApplicationListener<?> listener) {

    }

    @Override
    public void removeApplicationListenerBean(String listenerBeanName) {

    }

    @Override
    public void removeAllListeners() {

    }

    // 发布事件
    @Override
    public void multicastEvent(ApplicationEvent event, ResolvableType eventType) {
        for (GenericApplicationListener listener : listeners) {
            // 报错：Exception in thread "main" java.lang.ClassCastException: org.springframework.context.event.ContextRefreshedEvent cannot be cast to cn.coderap.listener.UserRegisterEvent
            // 原因：比如event是容器关闭时发出的ContextRefreshedEvent，它也是ApplicationEvent的子类，但是在调用该方法时，它不能强转为ApplicationEvent的另一个子类UserRegisterEvent
            // 解决方案：以EmailApplicationListener为例，看其接口的泛型UserRegisterEvent是否和传入的真实event类型一致，即后者能否赋值给前者。
            if (listener.supportsEventType(ResolvableType.forClass(event.getClass()))) {
                executor.execute(() -> {
                    listener.onApplicationEvent(event);
                });
            }
        }
    }

    @Override
    public void multicastEvent(ApplicationEvent event) {

    }
}
