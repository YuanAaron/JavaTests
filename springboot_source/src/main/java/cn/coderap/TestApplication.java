package cn.coderap;

import cn.coderap.initializer.SecondInitializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TestApplication {
    public static void main(String[] args) {
//        SpringApplication.run(TestApplication.class, args);
        SpringApplication springApplication = new SpringApplication(TestApplication.class);
        springApplication.addInitializers(new SecondInitializer());
        springApplication.run(args);
    }
}
