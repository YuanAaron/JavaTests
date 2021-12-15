package cn.coderap.ioc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用容器的方式管理UserController和UserService
 */
public class ObjectFactory { //相当于BeanFactory

    public static final Map<Class<?>,Object> OBJECT_CONTAINER = new HashMap<>();

    public void init() {
        //获取哪些类需要实例化到容器中
        List<Class<?>> clzList = new ArrayList<>();
        clzList.add(UserController.class); //硬编码1
        clzList.add(UserService.class);

        //实例化这些对象
        for (Class<?> clz : clzList) {
            instantiate(clz);
        }
    }

    private <V> V instantiate(Class<V> clz) {
        // 避免重复实例化
        if (OBJECT_CONTAINER.containsKey(clz)) {
            return getObject(clz);
        }

        if (clz == UserService.class) { //硬编码2：可以用反射优化
            UserService userService = new UserService();
            OBJECT_CONTAINER.put(UserService.class, userService);
            return (V)userService;
        } else if (clz == UserController.class) {
            UserController userController = new UserController();
            OBJECT_CONTAINER.put(UserController.class, userController);

            //注入属性
            UserService userService = getObject(UserService.class);
            if (userService == null) {
                userService = instantiate(UserService.class);
            }
            userController.setUserService(userService);
            return (V)userController;
        }
        throw new UnsupportedOperationException();
    }

    public <V> V getObject(Class<V> clz) {
        return (V)OBJECT_CONTAINER.get(clz);
    }
}