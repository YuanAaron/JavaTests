package cn.coderap.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public abstract class AbstractWeatherEventMulticaster implements EventMulticaster{

    @Autowired
    List<WeatherListener> listeners;

    // 模版方法
    @Override
    public void multicast(WeatherEvent event) {
        doStart();
        for (WeatherListener listener : listeners) {
            listener.onWeatherEvent(event);
        }
        doEnd();
    }

    protected abstract void doEnd();
    protected abstract void doStart();

    @Override
    public void addListener(WeatherListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(WeatherListener listener) {
        listeners.remove(listener);
    }
    
}
