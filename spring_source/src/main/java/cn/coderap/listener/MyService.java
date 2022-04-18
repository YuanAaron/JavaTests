package cn.coderap.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Slf4j
public class MyService {

    @Autowired
    private ApplicationEventPublisher publisher; // 用ApplicationContext也可以，因为它实现了该接口

    public void register() {
        log.info("用户注册");
        // 主线业务完成后需要做一些支线业务，可以通过事件解耦
        publisher.publishEvent(new UserRegisterEvent("MyService.register"));
    }
}
