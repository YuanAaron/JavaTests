package cn.coderap.event;

import org.springframework.stereotype.Component;

@Component
public class WeatherEventMulticaster extends AbstractWeatherEventMulticaster {
    @Override
    protected void doStart() {
        System.out.println("begin broadcast weather event");
    }

    @Override
    protected void doEnd() {
        System.out.println("end broadcast weather event");
    }
}
