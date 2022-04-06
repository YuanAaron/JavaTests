package cn.coderap.event;

/**
 * 下雪事件
 */
public class SnowEvent extends WeatherEvent{
    @Override
    public String getWeatherEvent() {
        return "snow";
    }
}
