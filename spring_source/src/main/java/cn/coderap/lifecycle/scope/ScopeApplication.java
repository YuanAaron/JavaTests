package cn.coderap.lifecycle.scope;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * scope类型：
 *      singleton: 每次从容器中获取bean，返回的都是同一个对象。
 *          创建：Spring容器创建时
 *          销毁：Spring容器关闭时
 *      prototype: 每次从容器中获取bean，都会创建新的对象
 *          创建：每次使用时创建
 *          销毁：容器不管理，可以自行调用每个多例对象的销毁方法
 *      request: 每次请求来时创建该bean并放入request域，请求结束时bean的生命周期也随之结束
 *          创建：请求来时创建request域的对象，放入web的request域中
 *          销毁：请求结束时
 *      session（验证时需要使用两个浏览器）: 会话开始bean被创建，会话结束bean被销毁
 *          创建：会话创建时创建session域对象，放入session域中
 *          销毁：会话结束时
 *      application: 应用程序（ServletContext)启动时bean被创建，启用程序关闭时被销毁
 *          创建：ServletContext创建时创建该域对象，放入application域
 *          销毁：Spring没有正确实现
 * 1. 这里演示后三种不常使用的
 * 2. 如果jdk为17，运行时会报错：java.lang.IllegalAccessException: module java.base does not open java.lang to unnamed module；
 *    原因：反射调用jdk中类的方法和成员变量就会报这个错。这里以BeanForRequest为例，由于TestController中的beanForRequest属性加了@Lazy，
 *    这样该属性赋值（注入）的是代理对象（非原始对象），而test方法中会调用该代理对象的toString方法（被代理类并没有重写toString方法），
 *    最终会反射调用到Object的toString方法（jdk中的类的方法）
 *    解决方案：
 *      2.1 换低的jdk版本，比如jdk8（不推荐）
 *      2.2 重写BeanForRequest等三个类的toString方法，这样就会调用自己的toString方法，而不会反射调用jdk中Object的toString方法
 *      2.3 添加VM参数：--add-opens java.base/java.lang=ALL-UNNAMED，即允许通过反射调用jdk中的方法和属性
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class,DruidDataSourceAutoConfigure.class})
public class ScopeApplication {
    public static void main(String[] args) {
        SpringApplication.run(ScopeApplication.class, args);
    }
}
