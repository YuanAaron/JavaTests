package cn.coderap.event;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractWeatherEventMulticaster implements EventMulticaster{

    List<WeatherListener> listeners = new ArrayList<>();

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
