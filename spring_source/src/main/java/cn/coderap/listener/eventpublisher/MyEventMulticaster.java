package cn.coderap.listener.eventpublisher;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.core.ResolvableType;

public class MyEventMulticaster implements ApplicationEventMulticaster {

    private ConfigurableApplicationContext context;

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
        System.out.println(listenerBeanName);
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

    @Override
    public void multicastEvent(ApplicationEvent event, ResolvableType eventType) {

    }

    @Override
    public void multicastEvent(ApplicationEvent event) {

    }
}
