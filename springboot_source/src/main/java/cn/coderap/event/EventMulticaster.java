package cn.coderap.event;

public interface EventMulticaster {

    void multicast(WeatherEvent event);

    void addListener(WeatherListener listener);
    void removeListener(WeatherListener listener);
}
