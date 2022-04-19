package cn.coderap.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class Bean12 {
    public Bean12() {
        log.info("Bean11 被 Spring 管理啦！");
    }
}
