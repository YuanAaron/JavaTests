package cn.coderap.factory;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
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
        } catch (DocumentException | ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    // 任务二：对外提供获取实例对象的接口（根据id获取）
    public static Object getBean(String id) {
        return map.get(id);
    }
}
