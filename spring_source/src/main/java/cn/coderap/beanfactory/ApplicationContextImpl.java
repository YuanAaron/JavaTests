package cn.coderap.beanfactory;

import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletRegistrationBean;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListenerMethodProcessor;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.mvc.Controller;

public class ApplicationContextImpl {
    public static void main( String[] args ) {
//        testClassPathXmlApplicationContext();
//        testFileSystemXmlApplicationContext();
//        testAnnotationConfigApplicationContext();
        testAnnotationConfigServletWebServerApplicationContext();
    }

    // 较为经典的容器，基于 classpath 下 xml 格式的配置文件的创建
    private static void testClassPathXmlApplicationContext() {
//        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("beans01.xml");

        //-------------------中间代码是new ClassPathXmlApplicationContext("beans01.xml")的部分拆解---------
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        System.out.println("读取之前...");
        for (String name : beanFactory.getBeanDefinitionNames()) {
            System.out.println(name);
        }
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions(new ClassPathResource("beans01.xml"));
        System.out.println("读取之后...");
        for (String name : beanFactory.getBeanDefinitionNames()) {
            System.out.println(name);
        }
        // 如果在bean01.xml添加<context:annotation-config/>就会添加这5个后置处理器
        // 报错：Exception in thread "main" java.lang.IllegalStateException: No ConfigurableListableBeanFactory set
        // 解决方案：只需要调用一下EventListenerMethodProcessor的postProcessBeanFactory方法即可，因为上面的ClassPathXmlApplicationContext创建过程中调用了refresh方法，即调用了BeanFactory后置处理器和Bean后置处理器。
//        beanFactory.getBeansOfType(BeanFactoryPostProcessor.class).values().forEach(beanFactoryPostProcessor -> {
//            if (beanFactoryPostProcessor instanceof  EventListenerMethodProcessor) {
//                beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);
//            }
//        });
        beanFactory.preInstantiateSingletons();
        System.out.println("预先创建了单例");
        System.out.println(beanFactory.getBean(Bean2.class).getBean1());
        //-------------------拆解结束-----------------------------------------------------------------
//        for (String beanDefinitionName : context.getBeanDefinitionNames()) {
//            System.out.println(beanDefinitionName);
//        }
//        //发现没有输出默认的5个后置处理器（2个BeanFactoryPostProcessor，2个BeanPostProcessor，1个其他)，如果在bean01.xml添加<context:annotation-config/>就会添加这5个后置处理器
//        System.out.println("预先创建了单例");
//        System.out.println(context.getBean(Bean2.class).getBean1());
    }

    // 基于磁盘路径下 xml 格式的配置文件来创建
    private static void testFileSystemXmlApplicationContext() {
        // 注意：需要将工作目录改为当前module
        FileSystemXmlApplicationContext context = new FileSystemXmlApplicationContext("src/main/resources/beans01.xml");
        for (String beanDefinitionName : context.getBeanDefinitionNames()) {
            System.out.println(beanDefinitionName);
        }
        System.out.println(context.getBean(Bean2.class).getBean1());
    }

    // 较为经典的容器，基于 java 配置类来创建
    private static void testAnnotationConfigApplicationContext() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        for (String beanDefinitionName : context.getBeanDefinitionNames()) {
            System.out.println(beanDefinitionName);
        }
        System.out.println(context.getBean(Bean2.class).getBean1());
    }

    // 较为经典的容器，基于 java 配置类来创建，用于 web 环境
    private static void testAnnotationConfigServletWebServerApplicationContext() {
        AnnotationConfigServletWebServerApplicationContext context = new AnnotationConfigServletWebServerApplicationContext(WebConfig.class);
    }

    // 前面三个Bean必须有
    @Configuration
    static class WebConfig {

        @Bean
        public ServletWebServerFactory servletWebServerFactory() {
            return new TomcatServletWebServerFactory();
        }

        @Bean
        public DispatcherServlet dispatcherServlet() {
            return new DispatcherServlet();
        }

        // 注册DispatcherServlet到Tomcat服务器
        @Bean
        public DispatcherServletRegistrationBean dispatcherServletRegistrationBean(DispatcherServlet dispatcherServlet) {
            return new DispatcherServletRegistrationBean(dispatcherServlet, "/");
        }

        // 用于浏览器调用演示
        @Bean("/hello")
        public Controller hello() {
            return (request, response) -> {
                response.getWriter().write("hello world!");
                return null;
            };
        }

    }


    @Configuration
    static class Config {

        @Bean
        public Bean1 bean1() {
            return  new Bean1();
        }

        @Bean
        public Bean2 bean2(Bean1 bean1) {
            Bean2 bean2 = new Bean2();
            bean2.setBean1(bean1);
            return  bean2;
        }

    }

    static class Bean1 {

        public Bean1() {
            System.out.println("构造了bean1");
        }
    }

    static class Bean2 {

        private Bean1 bean1;

        public Bean2() {
            System.out.println("构造了bean2");
        }

        public void setBean1(Bean1 bean1) {
            this.bean1 = bean1;
        }

        public Bean1 getBean1() {
            return bean1;
        }
    }
}
