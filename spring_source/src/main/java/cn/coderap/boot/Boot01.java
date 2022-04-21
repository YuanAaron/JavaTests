package cn.coderap.boot;

import cn.coderap.component.Bean13;
import cn.coderap.lifecycle.MyBeanPostProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.GenericApplicationContext;

import java.lang.reflect.Method;
import java.util.Set;

/**
 * SpringApplication构造方法
 */
@Configuration
public class Boot01 {
    public static void main(String[] args) throws Exception {
        System.out.println("1. 获取 BeanDefinition 源");
        // 1.1 来自启动类（主源）
        SpringApplication springApplication = new SpringApplication(Boot01.class);
        // 1.2 来自xml的Spring配置文件
        springApplication.setSources(Set.of("classpath:beans02.xml"));
        System.out.println("2. 推断应用类型");
        // 根据类路径下的jar推断是非web、servlet、reactive中的哪一种应用类型，以创建对应的ApplicationContext
        // 2.1 如果存在"org.springframework.web.reactive.DispatcherHandler"，但不存在"org.springframework.web.servlet.DispatcherServlet"，则为Reactive；
        // 2.2 否则，如果{ "javax.servlet.Servlet","org.springframework.web.context.ConfigurableWebApplicationContext" }中有一个不存在，就不是web应用；
        // 2.3 否则，就是Servlet。
        Method method = WebApplicationType.class.getDeclaredMethod("deduceFromClasspath"); // 包级私有，所以需要反射
        method.setAccessible(true);
        System.out.println("\t应用类型为: " + method.invoke(null));
        System.out.println("3. ApplicationContext 初始化器");
        // 初始化器用于对ApplicationContext进行扩展（调用发生在ApplicationContext创建之后，但refreshContext之前）
        springApplication.addInitializers(applicationContext -> {
            if (applicationContext instanceof GenericApplicationContext) {
                GenericApplicationContext genericApplicationContext = (GenericApplicationContext) applicationContext;
                genericApplicationContext.registerBean("bean13", Bean13.class);
            }
        });
        System.out.println("4. 监听器与事件");
        // 用于监听Spring Boot启动过程中的重要事件(由于该监听器可以监听任何ApplicationEvent事件，因此run方法发布事件都会回调这里的OnApplicationEvent方法)
        springApplication.addListeners(event -> System.out.println("\t事件为: " + event.getClass()));
        System.out.println("5. 主类推断");
        // 推断处运行main方法的类，比如这里的Boot01
        method = SpringApplication.class.getDeclaredMethod("deduceMainApplicationClass");
        method.setAccessible(true);
        System.out.println("\t主类为：" + method.invoke(springApplication));

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

    @Bean
    public MyBeanPostProcessor myBeanPostProcessor() {
        return new MyBeanPostProcessor();
    }
}
