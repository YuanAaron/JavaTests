package cn.coderap.event;

public class Test {
    public static void main(String[] args) {
        EventMulticaster eventMulticaster = new WeatherEventMulticaster();
        SnowListener snowListener = new SnowListener();
        RainListener rainListener = new RainListener();
        eventMulticaster.addListener(snowListener);
        eventMulticaster.addListener(rainListener);

        eventMulticaster.multicast(new SnowEvent());
        eventMulticaster.multicast(new RainEvent());

        eventMulticaster.removeListener(rainListener);
        eventMulticaster.multicast(new SnowEvent());
        eventMulticaster.multicast(new RainEvent());
    }
}
