package cn.coderap.listener.eventlistener;

import cn.coderap.listener.UserRegisterEvent;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SmsApplicationListener1 {

//    @EventListener
    @MyEventListener
    public void aaa(UserRegisterEvent event) {
        log.info("发送短信");
    }
}

