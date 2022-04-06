package cn.coderap.event;

/**
 * 下雨事件
 */
public class RainEvent extends WeatherEvent{
    @Override
    public String getWeatherEvent() {
        return "rain";
    }
}
