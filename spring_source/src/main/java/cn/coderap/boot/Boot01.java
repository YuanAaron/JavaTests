package cn.coderap.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Set;

/**
 * SpringApplication构造方法
 */
@Configuration
public class Boot01 {
    public static void main(String[] args) {
        System.out.println("1. 获取 BeanDefinition 源");
        // 1.1 来自启动类（主源）
        SpringApplication springApplication = new SpringApplication(Boot01.class);
        // 1.2 来自xml的Spring配置文件
        springApplication.setSources(Set.of("classpath:beans02.xml"));
        System.out.println("2. 推断应用类型");
        // 根据类路径下的jar推断是非web、servlet、reactive中的哪一种应用类型，以创建对应的ApplicationContext
        System.out.println("3. ApplicationContext 初始化器");
        // 初始化器用于对ApplicationContext进行扩展
        System.out.println("4. 监听器与事件");
        // 用于监听Spring Boot启动过程中的重要事件
        System.out.println("5. 主类推断");
        // 推断处运行main方法的类，比如这里的Boot01

        ConfigurableApplicationContext context = springApplication.run(args);

        for (String beanDefinitionName : context.getBeanDefinitionNames()) {
            // 显示 beanDefinition 来自于哪个配置类或xml配置文件
            System.out.println("beanDefinitionName: " + beanDefinitionName + ", 来源于: " + context.getBeanFactory().getBeanDefinition(beanDefinitionName).getResourceDescription());
        }

        context.close();
    }

    // 第一个报错：Unable to start ServletWebServerApplicationContext due to missing ServletWebServerFactory bean
    // 原因：根据类路径下的特殊类推断为基于servlet的web应用程序，这样就会选择ServletWebServerApplicationContext，
    // 而该ApplicationContext的实现需要ServletWebServerFactory，否则无法启动web服务器
    @Bean
    public ServletWebServerFactory servletWebServerFactory() {
        return new TomcatServletWebServerFactory();
    }
}
