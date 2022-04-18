package cn.coderap.listener.eventlistener;

import cn.coderap.listener.UserRegisterEvent;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EmailApplicationListener1 {

    //@EventListener
    @MyEventListener
    public void bbb(UserRegisterEvent event) {
        log.info("发送邮件");
    }
}
