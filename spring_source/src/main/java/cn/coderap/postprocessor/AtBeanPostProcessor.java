package cn.coderap.postprocessor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.type.MethodMetadata;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;

import java.io.IOException;
import java.util.Set;

public class AtBeanPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        try {
            CachingMetadataReaderFactory factory = new CachingMetadataReaderFactory(); // 用于读取每个resource上的元信息
            MetadataReader reader = factory.getMetadataReader(new ClassPathResource("cn/coderap/postprocessor/BeanFactoryPostProcessorConfig.class"));
            Set<MethodMetadata> methods = reader.getAnnotationMetadata().getAnnotatedMethods(Bean.class.getName()); // 获取@Bean标注的方法信息
            for (MethodMetadata method : methods) {
                // 根据每个方法信息生成对应的BeanDefinition
                BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition();
                builder.setFactoryMethodOnBean(method.getMethodName(),"beanFactoryPostProcessorConfig"); // 设置工厂（beanFactoryPostProcessorConfig实例）的工厂方法（BeanFactoryPostProcessorConfig类中被@Bean标注的方法）
                builder.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_CONSTRUCTOR);// 指定工厂方法参数的自动装配模式，否则无法解析工厂方法参数。需要注意的是构造方法、工厂方法的参数自动装配使用构造注入
                // @Bean注解initMethod属性解析(解析后在日志中可见：com.alibaba.druid.pool.DruidDataSource - {dataSource-1} inited)
                String initMethod = method.getAnnotationAttributes(Bean.class.getName()).get("initMethod").toString();
                if (initMethod.length() > 0) {
                    builder.setInitMethodName(initMethod);
                }
                AbstractBeanDefinition beanDefinition = builder.getBeanDefinition();
                if (configurableListableBeanFactory instanceof DefaultListableBeanFactory) {
                    DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory)configurableListableBeanFactory;
                    beanFactory.registerBeanDefinition(method.getMethodName(),beanDefinition);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
