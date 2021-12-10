package cn.coderap.factory;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class BeanFactory {

    /**
     * 任务一：读取解析xml，然后通过反射技术实例化对象并存储起来待用（map集合）
     * 任务二：对外提供获取实例对象的接口（根据id获取）
     */

    private static Map<String, Object> map = new HashMap<>();

    static {
        // 任务一：读取解析xml，然后通过反射技术实例化对象并存储起来待用（map集合）
        // 1.加载xml
        InputStream is = BeanFactory.class.getClassLoader().getResourceAsStream("beans.xml");
        // 2.解析xml
        SAXReader saxReader = new SAXReader();
        try {
            Document document = saxReader.read(is);
            Element rootElement = document.getRootElement();
            List<Element>  beanList= rootElement.selectNodes("//bean");
            for (Element element : beanList) {
                // 处理每个bean标签，获取到该标签的id 和 class属性
                String id = element.attributeValue("id"); // accountDao
                String className = element.attributeValue("class"); // cn.coderap.dao.impl.JdbcAccountDaoImpl
                // 通过反射技术实例化对象
                Class<?> cls = Class.forName(className);
                Object object = cls.newInstance();

                //存储到map中待用
                map.put(id, object);
            }

            // 实例化完成后维护对象的依赖关系，检查哪些对象需要传值，然后根据它的配置通过反射调用对应的setXxx方法进行传值
            // 如果一个对象需要传值，那么它对应的bean标签下肯定有property，即有property的bean标签对应的对象就有传值需求
            List<Element> propertyList = rootElement.selectNodes("//property");
            for (Element element : propertyList) {
                // <property name="AccountDao" ref="accountDao"></property>
                String name = element.attributeValue("name");
                String ref = element.attributeValue("ref");

                // 找到当前需要处理依赖关系的bean（当前property标签的父标签）
                Element parent = element.getParent();

                // 调用父标签（bean标签）对象的反射方法传值
                String parentId = parent.attributeValue("id");
                Object parentObject = map.get(parentId);

                // 遍历父标签对象中的所有方法，找到"set"+name方法，然后通过反射调用该方法传值
                Method[] methods = parentObject.getClass().getMethods();
                for (Method method : methods) {
                    if (method.getName().equalsIgnoreCase("set" + name)) {
                        method.invoke(parentObject, map.get(ref));
                    }
                }

                // 把处理之后的parentObject重新放到map中
                map.put(parentId, parentObject);
            }
        } catch (DocumentException | ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    // 任务二：对外提供获取实例对象的接口（根据id获取）
    public static Object getBean(String id) {
        return map.get(id);
    }
}
