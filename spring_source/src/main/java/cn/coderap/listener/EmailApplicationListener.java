package cn.coderap.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
public class EmailApplicationListener {

    @EventListener
    public void bbb(UserRegisterEvent event) {
        log.info("发送邮件");
    }
}

//@Slf4j
//public class EmailApplicationListener implements ApplicationListener<UserRegisterEvent> {
//
//    @Override
//    public void onApplicationEvent(UserRegisterEvent event) {
//        log.info("发送邮件");
//    }
//}
