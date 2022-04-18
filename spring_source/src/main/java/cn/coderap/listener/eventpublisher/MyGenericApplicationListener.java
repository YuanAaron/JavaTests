package cn.coderap.listener.eventpublisher;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.GenericApplicationListener;
import org.springframework.core.ResolvableType;

public class MyGenericApplicationListener implements GenericApplicationListener {

    private ApplicationListener listener;
    private ResolvableType type; // 支持的事件类型

    public MyGenericApplicationListener(ApplicationListener listener, ResolvableType type) {
        this.listener = listener;
        this.type = type;
    }

    /**
     * 是否支持某事件类型
     * evenType：传入的真实事件类型
     */
    @Override
    public boolean supportsEventType(ResolvableType eventType) {
        return type.isAssignableFrom(eventType);
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        // 调用原始 ApplicationListener类 的onApplicationEvent方法
        listener.onApplicationEvent(event);
    }
}
