package cn.coderap.component.test;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Bean14 {
    public Bean14() {
        log.info("Bean14 被 Spring 管理啦！");
    }
}
