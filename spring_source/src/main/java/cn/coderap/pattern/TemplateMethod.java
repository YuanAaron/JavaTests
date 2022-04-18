package cn.coderap.pattern;

import java.util.ArrayList;
import java.util.List;

/**
 * 模版方法
 */
public class TemplateMethod {
    public static void main(String[] args) {
        MyBeanFactory beanFactory = new MyBeanFactory();
        beanFactory.addBeanPostProcessor(new MyAutowiredAnnotationBeanPostProcessor());
        beanFactory.addBeanPostProcessor(new MyCommonAnnotationBeanPostProcessor());
        beanFactory.getBean();
    }

    static class MyBeanFactory {
        private List<MyBeanPostProcessor> list = new ArrayList<>();

        public void addBeanPostProcessor(MyBeanPostProcessor beanPostProcessor) {
            list.add(beanPostProcessor);
        }

        public Object getBean() {
            Object bean = new Object();
            // 静
            System.out.println("实例化：" + bean);
            System.out.println("依赖注入：" + bean);
            // 动：对@Autowired、@Resource注解进行解析
            for (MyBeanPostProcessor beanPostProcessor: list) {
                beanPostProcessor.inject(bean);
            }
            // 静
            System.out.println("初始化：" + bean);
            return bean;
        }
    }

    interface MyBeanPostProcessor {
        void inject(Object bean);
    }

    static class MyAutowiredAnnotationBeanPostProcessor implements MyBeanPostProcessor{

        @Override
        public void inject(Object bean) {
            System.out.println("解析@Autowired注解");
        }
    }

    static class MyCommonAnnotationBeanPostProcessor implements MyBeanPostProcessor{

        @Override
        public void inject(Object bean) {
            System.out.println("解析@Resource注解");
        }
    }
}
