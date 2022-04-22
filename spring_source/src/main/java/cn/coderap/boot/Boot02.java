package cn.coderap.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.support.SpringFactoriesLoader;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

public class Boot02 {
    public static void main(String[] args) throws Exception {
        SpringApplication springApplication = new SpringApplication();
        // 添加app监听器
        springApplication.addListeners(event -> System.out.println(event.getClass()));

        // 获取事件发布器SpringApplicationRunListeners的实现类的名字
        List<String> names = SpringFactoriesLoader.loadFactoryNames(SpringApplicationRunListener.class, Boot02.class.getClassLoader());
        List<SpringApplicationRunListener> publishers = new ArrayList<>();
        for (String name : names) {
            // 根据事件发布器实现类的名字创建实例对象
            Class<?> clazz = Class.forName(name);
            Constructor<?> constructor = clazz.getConstructor(SpringApplication.class, String[].class);
            publishers.add((SpringApplicationRunListener) constructor.newInstance(springApplication, args));
        }

        for (SpringApplicationRunListener publisher : publishers) {
            // 发布事件(注意：事件的参数仅仅为了防止语法报错)
            publisher.starting(); // Spring Boot开启启动
            publisher.environmentPrepared(new StandardEnvironment()); // 环境信息准备完毕
            GenericApplicationContext context = new GenericApplicationContext();
            publisher.contextPrepared(context); // Spring容器创建完成、初始化器执行完毕
            publisher.contextLoaded(context); // 各种来源的BeanDefinition都加载完毕
            context.refresh(); // 添加各种Bean后置处理器，实例化所有的单例Bean
            publisher.started(context); //Spring容器初始化完成（refresh完毕）
            publisher.running(context); // Spring Boot启动完成

            publisher.failed(context,new Exception("出错了!")); // Spring Boot启动出错
        }

    }
}
