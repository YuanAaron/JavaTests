package cn.coderap.postprocessor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.stereotype.Component;

public class ComponentScanPostProcessor implements BeanFactoryPostProcessor{
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        try {
            // BeanFactoryPostProcessorConfig上是否有@ComponentScan
            ComponentScan componentScan = AnnotationUtils.findAnnotation(BeanFactoryPostProcessorConfig.class, ComponentScan.class);
            if (componentScan != null) {
                // basePackages可能是多个，遍历
                for (String basePackage : componentScan.basePackages()) {
                    System.out.println(basePackage);
                    // cn.coderap.component转成classpath*:cn/coderap/component/**/*.class
                    String path = "classpath*:" + basePackage.replace(".", "/") + "/**/*.class";
                    Resource[] resources = new PathMatchingResourcePatternResolver().getResources(path);
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
                            if (configurableListableBeanFactory instanceof DefaultListableBeanFactory) {
                                DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) configurableListableBeanFactory;
                                String beanName = beanNameGenerator.generateBeanName(beanDefinition, beanFactory);
                                beanFactory.registerBeanDefinition(beanName,beanDefinition);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
