package cn.coderap.initializer;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import java.util.HashMap;
import java.util.Map;

@Order(2)
public class SecondInitializer implements ApplicationContextInitializer {
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        Map<String, Object> map = new HashMap<>();
        map.put("key2", "value2");
        MapPropertySource mapPropertySource = new MapPropertySource("secondInitializer", map);
        environment.getPropertySources().addLast(mapPropertySource);
        System.out.println("RUN SecondInitializer");
    }
}
