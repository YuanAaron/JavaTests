package cn.coderap.postprocessor;

import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ConfigurationClassPostProcessor;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.stereotype.Component;

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
        // BeanFactoryPostProcessorConfig上是否有@ComponentScan
        ComponentScan componentScan = AnnotationUtils.findAnnotation(BeanFactoryPostProcessorConfig.class, ComponentScan.class);
        if (componentScan != null) {
            // basePackages可能是多个，遍历
            for (String basePackage : componentScan.basePackages()) {
                System.out.println(basePackage);
                // cn.coderap.component转成classpath*:cn/coderap/component/**/*.class
                String path = "classpath*:" + basePackage.replace(".", "/") + "/**/*.class";
                Resource[] resources = context.getResources(path);
                CachingMetadataReaderFactory factory = new CachingMetadataReaderFactory(); // 用于读取每个resource上的元信息
                BeanNameGenerator beanNameGenerator = new AnnotationBeanNameGenerator(); // 用于生成beanName
                for (Resource resource : resources) {
                    MetadataReader reader = factory.getMetadataReader(resource);
                    String className = reader.getClassMetadata().getClassName();
                    AnnotationMetadata annotationMetadata = reader.getAnnotationMetadata();
                    // 如果直接或间接加了@Component
                    if (annotationMetadata.hasAnnotation(Component.class.getName())
                            || annotationMetadata.hasMetaAnnotation(Component.class.getName())) {
                        // 根据className生成beanDefinition
                        AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.genericBeanDefinition(className).getBeanDefinition();
                        // 根据beanDefinition生成beanName
                        String beanName = beanNameGenerator.generateBeanName(beanDefinition, context);
                        context.registerBeanDefinition(beanName,beanDefinition);
                    }
                }
            }
        }

        // 初始化容器：执行BeanFactory后置处理器，添加（执行）bean后置处理器，初始化所有单例
        context.refresh();

        for (String beanDefinitionName : context.getBeanDefinitionNames()) {
            System.out.println(beanDefinitionName);
        }

        // 销毁容器
        context.close();
    }
}
