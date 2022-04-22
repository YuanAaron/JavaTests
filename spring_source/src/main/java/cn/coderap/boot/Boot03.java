package cn.coderap.boot;

import cn.coderap.component.Bean13;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.boot.*;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.reactive.context.AnnotationConfigReactiveWebServerApplicationContext;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.annotation.*;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.io.ClassPathResource;

import java.util.Arrays;

/**
 * 运行参数用于演示runner回调：--server.port=8080 debug
 */
public class Boot03 {
    public static void main(String[] args) throws Exception {
        SpringApplication springApplication = new SpringApplication();
        springApplication.addInitializers(applicationContext -> System.out.println("执行初始化器增强context..."));

        System.out.println(">>>2.封装启动参数 args");
        DefaultApplicationArguments applicationArguments = new DefaultApplicationArguments(args);

        System.out.println(">>>8.创建容器");
        GenericApplicationContext context = createApplicationContext(WebApplicationType.SERVLET);

        System.out.println(">>>9.准备容器");
        // 回调初始化器增强context的功能
        for (ApplicationContextInitializer initializer : springApplication.getInitializers()) {
            initializer.initialize(context);
        }
        System.out.println(">>>10.加载各种来源的BeanDefinition");
        // 注意：源码中是设置springApplication.setSources(Set.of("config.class","beans02.xml","cn.coderap.component.test"));，
        // 然后再选择合适的reader或scanner加载BeanDefinition。
        AnnotatedBeanDefinitionReader annotatedReader = new AnnotatedBeanDefinitionReader(context); // 第一种BeanDefinition来源：配置类
        XmlBeanDefinitionReader xmlReader = new XmlBeanDefinitionReader(context); // 第二种BeanDefinition来源：xml配置文件
        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(context); // 第三种BeanDefinition来源：扫描@Component注解标注的类

        annotatedReader.register(Config.class);
        xmlReader.loadBeanDefinitions(new ClassPathResource("beans02.xml"));
        scanner.scan("cn.coderap.component.test");

        System.out.println(">>>11.refresh容器");
        context.refresh();

        for (String beanDefinitionName : context.getBeanDefinitionNames()) {
            // 显示 beanDefinition 来自于哪个配置类或xml配置文件
            System.out.println("beanDefinitionName: " + beanDefinitionName + ", 来源于: " + context.getBeanDefinition(beanDefinitionName).getResourceDescription());
        }

        System.out.println(">>>12.执行runner");
        for (CommandLineRunner runner : context.getBeansOfType(CommandLineRunner.class).values()) {
            runner.run(args);
        }
        for (ApplicationRunner runner : context.getBeansOfType(ApplicationRunner.class).values()) {
            runner.run(applicationArguments);
        }

    }

    private static GenericApplicationContext createApplicationContext(WebApplicationType type) {
        GenericApplicationContext context;
        switch (type) {
            case REACTIVE:
                context = new AnnotationConfigReactiveWebServerApplicationContext();
                break;
            case NONE:
                context = new AnnotationConfigApplicationContext();
                break;
            default: // Servlet
                context = new AnnotationConfigServletWebServerApplicationContext();
                break;
        }
        return context;
    }

    @Configuration
    static class Config {
        @Bean
        public Bean13 bean13() {
            return new Bean13();
        }

        // 必须有
        @Bean
        public ServletWebServerFactory servletWebServerFactory() {
            return new TomcatServletWebServerFactory();
        }

        // runner: CommandLineRunner
        @Bean
        public CommandLineRunner commandLineRunner() {
            return new CommandLineRunner() {
                @Override
                public void run(String... args) throws Exception {
                    System.out.println("CommandLineRunner..." + Arrays.toString(args));
                }
            };
        }

        // runner: ApplicationRunner
        @Bean
        public ApplicationRunner applicationRunner() {
            return new ApplicationRunner() {
                @Override
                public void run(ApplicationArguments args) throws Exception {
                    System.out.println("ApplicationRunner..." + Arrays.toString(args.getSourceArgs()));
                    for (String optionName : args.getOptionNames()) {
                        System.out.println("optionName: " + args.getOptionNames() + "的值为" + args.getOptionValues(optionName));
                    }
                    System.out.println("nonOptionArgs：" + args.getNonOptionArgs());

                }
            };
        }
    }

}
