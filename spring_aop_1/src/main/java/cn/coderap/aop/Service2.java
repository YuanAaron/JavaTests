package cn.coderap.aop;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Service2 {

    public void foo() {
        log.info("foo()");
        bar();
    }

    public void bar() {
        log.info("bar()");
    }

}
