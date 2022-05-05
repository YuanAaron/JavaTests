package cn.coderap.lifecycle.scope;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

@Scope("session")
@Component
@Slf4j
public class BeanForSession {

    @PreDestroy
    public void preDestroy() {
        log.info("@PreDestroy");
    }
}
