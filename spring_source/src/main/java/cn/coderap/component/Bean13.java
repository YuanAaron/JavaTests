package cn.coderap.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
public class Bean13 {
    public Bean13() {
        log.info("Bean13 被 Spring 管理啦！");
    }
}
