package cn.coderap.postprocessor;

import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.*;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;

import java.io.IOException;

public class MapperScanPostProcessor implements BeanDefinitionRegistryPostProcessor {
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        try {
            String basePackage = "cn.coderap.component.mapper";
            // cn.coderap.component.mapper转成classpath*:cn/coderap/component/mapper/**/*.class
            String path = "classpath*:" + basePackage.replace(".", "/") + "/**/*.class";
            Resource[] resources = new PathMatchingResourcePatternResolver().getResources(path);
            CachingMetadataReaderFactory factory = new CachingMetadataReaderFactory(); // 用于读取每个resource上的元信息
            BeanNameGenerator beanNameGenerator = new AnnotationBeanNameGenerator(); // 用于生成beanName
            for (Resource resource : resources) {
                MetadataReader reader = factory.getMetadataReader(resource);
                ClassMetadata classMetadata = reader.getClassMetadata();
                if (classMetadata.isInterface()) {
                    // 由前面单个Mapper接口的扫描，即Mapper接口是如何变成Spring管理的对象的，这里应该传入Mapper接口MapperFactoryBean，而非Mapper接口
                    AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder
                            .genericBeanDefinition(MapperFactoryBean.class)
                            .addConstructorArgValue(classMetadata.getClassName()) // Mapper1.class全限定名
                            .setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE) // 按类型自动注入SqlSessionFactory，不懂阿！！！
                            .getBeanDefinition();
                    // 问题：如果直接用上面的beanDefinition生成beanName，生成的所有beanName都是mapperFactoryBean，这是不对的，
                    // 且后面生成的会替换前面生成的，即最终只有一个mapperFactoryBean，这明显也是不对的
                    // 解决：专门生成一个用于生成名字的beanDefinition
                    AbstractBeanDefinition beanDefinitionForBeanName = BeanDefinitionBuilder.genericBeanDefinition(classMetadata.getClassName()).getBeanDefinition();
                    // 根据beanDefinition生成beanName
                    String beanName = beanNameGenerator.generateBeanName(beanDefinitionForBeanName, registry);
                    // 其实这里注册的是Mapper工厂而非Mapper，但是名字需要特殊处理
                    registry.registerBeanDefinition(beanName,beanDefinition);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

    }
}
