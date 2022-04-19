package cn.coderap.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Bean5 {

    public Bean5() {
        log.info("Bean5 被 Spring 管理啦！");
    }
}
