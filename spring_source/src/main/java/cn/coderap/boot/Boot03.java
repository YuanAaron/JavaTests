package cn.coderap.boot;

import cn.coderap.component.Bean13;
import lombok.Data;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.boot.*;
import org.springframework.boot.context.config.ConfigFileApplicationListener;
import org.springframework.boot.context.event.EventPublishingRunListener;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.source.ConfigurationPropertySources;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.reactive.context.AnnotationConfigReactiveWebServerApplicationContext;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.annotation.*;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.env.PropertySource;
import org.springframework.core.env.SimpleCommandLinePropertySource;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.ResourcePropertySource;

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

        StandardEnvironment env = new StandardEnvironment(); // 这里只会涉及到systemProperties和systemEnvironment
        env.getPropertySources().addLast(new ResourcePropertySource(new ClassPathResource("step3.properties")));
        System.out.println(">>>3.添加Environment命令行参数");
        env.getPropertySources().addFirst(new SimpleCommandLinePropertySource(args));
        System.out.println(">>>4.ConfigurationPropertySources处理");
        // 将step3.properties中的各种命名形式统一成中间横杠分隔，如果没有这个，user.middle-name和user.last-name将取不到值
        ConfigurationPropertySources.attach(env);
        System.out.println(env.getProperty("user.first-name"));
        System.out.println(env.getProperty("user.middle-name"));
        System.out.println(env.getProperty("user.last-name"));
        // 各种源中变量的优先级（前面有就用前面的，前面没有用后面的）：
        // 先通过ConfigurationPropertySources.attach(env);添加的源configurationProperties，
        // 再通过addFirst添加的源commandLineArgs(可以通过Program parameter自定义配置)，再systemProperties(可以通过VM options自定义配置)，
        // 再systemEnvironment，再通过addLast添加的源class path resource [step3.properties]，
        // 最后是通过EnvironmentPostProcessor后置处理器添加的applicationConfig（application.yml对应的源是通过这一步添加到env中的）
        for (PropertySource<?> propertySource : env.getPropertySources()) {
            System.out.println(propertySource);
        }
        System.out.println(">>>5. 通过ConfigFileApplicationListener进行env后处理增强");
        // 通过EnvironmentPostProcessor对env进行后置增强，application.yml对应的源是通过这一步添加到env中的
        System.out.println(env.getProperty("server.port"));
//        ConfigFileApplicationListener listener = new ConfigFileApplicationListener();
//        listener.postProcessEnvironment(env, springApplication);
        // ConfigFileApplicationListener监听器监听到该事件后，回调监听器的方法
        EventPublishingRunListener eventPublishingRunListener = new EventPublishingRunListener(springApplication, args);
        eventPublishingRunListener.environmentPrepared(env);
        System.out.println("增强后的源...");
        for (PropertySource<?> propertySource : env.getPropertySources()) {
            System.out.println(propertySource);
        }
        System.out.println(env.getProperty("server.port"));

        System.out.println(">>>6.绑定spring.main前缀的key value到SpringApplication对象");
        // @ConfigurationProperties的底层实现原理
        // 绑定到一个还未创建的对象上
//        User user = Binder.get(env).bind("user", User.class).get();
//        System.out.println(user);
        // 绑定到一个已创建的对象上
//        User user = new User();
//        Binder.get(env).bind("user", Bindable.ofInstance(user));
//        System.out.println(user);
        env.getPropertySources().addLast(new ResourcePropertySource(new ClassPathResource("step6.properties")));
        Binder.get(env).bind("spring.main", Bindable.ofInstance(springApplication));

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

    @Data
    static class User {
        private String firstName;
        private String middleName;
        private String lastName;
    }

}
