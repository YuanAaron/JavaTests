package cn.coderap.postprocessor;

import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.ConfigurationClassPostProcessor;
import org.springframework.context.support.GenericApplicationContext;

import java.io.IOException;

/**
 * 常用的BeanFactory后置处理器
 */
public class BeanFactoryPostProcessorImpl {
    public static void main(String[] args) throws IOException {
        // GenericApplicationContext 是一个 【纯净】的容器
        GenericApplicationContext context = new GenericApplicationContext();

        // 用原始方法注册三个bean
        context.registerBean("beanFactoryPostProcessorConfig", BeanFactoryPostProcessorConfig.class);

        // 添加BeanFactory后置处理器
//        context.registerBean(ConfigurationClassPostProcessor.class); // 解析@Configuration、@Bean、@ComponentScan、@Import、@ImportResource(后两个这里不再举例)
//        context.registerBean(MapperScannerConfigurer.class, bd -> {
//            bd.getPropertyValues().add("basePackage","cn.coderap.component.mapper");
//        }); // 现在很少用，一般跟Spring Boot整合时自动配置MybatisAutoConfiguration类用到它或@MapperScan底层用到它（实践发现这段代码作用相当于@MapperScan）

        // ----------------------注释掉ConfigurationClassPostProcessor和MapperScannerConfigurer，探究两者的实现原理---------------
        // 1. 解析@ComponentScan
        context.registerBean(ComponentScanPostProcessor.class);
        // 2. 解析@Bean
        context.registerBean(AtBeanPostProcessor.class);
        // 3. 扫描basePackage下的所有Mapper
        context.registerBean(MapperScanPostProcessor.class);

        // 初始化容器：执行BeanFactory后置处理器，添加（执行）bean后置处理器，初始化所有单例
        context.refresh();

        for (String beanDefinitionName : context.getBeanDefinitionNames()) {
            System.out.println(beanDefinitionName);
        }

        // 销毁容器
        context.close();
    }
}
