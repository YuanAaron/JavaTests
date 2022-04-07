package cn.coderap.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WeatherRunListener {

    @Autowired
    private WeatherEventMulticaster weatherEventMulticaster;

    public void snow() {
        this.weatherEventMulticaster.multicast(new SnowEvent());
    }

    public void rain() {
        this.weatherEventMulticaster.multicast(new RainEvent());
    }
}
