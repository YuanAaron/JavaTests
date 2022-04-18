package cn.coderap.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

//@Slf4j
//public class SmsApplicationListener {
//
//    @EventListener
//    public void aaa(UserRegisterEvent event) {
//        log.info("发送短信");
//    }
//}

@Slf4j
public class SmsApplicationListener implements ApplicationListener<UserRegisterEvent> {

    @Override
    public void onApplicationEvent(UserRegisterEvent event) {
        log.info("发送短信");
    }
}
