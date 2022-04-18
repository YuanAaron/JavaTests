package cn.coderap.listener.eventpublisher;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.core.ResolvableType;

import java.util.ArrayList;
import java.util.List;

public class MyEventMulticaster implements ApplicationEventMulticaster {

    private ConfigurableApplicationContext context;
    private List<ApplicationListener> listeners = new ArrayList<>();

    public void setContext(ConfigurableApplicationContext context) {
        this.context = context;
    }

    @Override
    public void addApplicationListener(ApplicationListener<?> listener) {
        System.out.println();
    }

    // 收集监听器：事件广播器需要知道所有的事件监听器
    @Override
    public void addApplicationListenerBean(String listenerBeanName) {
        ApplicationListener listener = context.getBean(listenerBeanName, ApplicationListener.class);
        listeners.add(listener);
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
        for (ApplicationListener listener : listeners) {
            // 报错：Exception in thread "main" java.lang.ClassCastException: org.springframework.context.event.ContextRefreshedEvent cannot be cast to cn.coderap.listener.UserRegisterEvent
            listener.onApplicationEvent(event);
        }
    }

    @Override
    public void multicastEvent(ApplicationEvent event) {

    }
}
