package cn.coderap.aop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Service1 {

    private static final Logger log = LoggerFactory.getLogger(Service1.class);

    public void foo() {
        log.info("foo()");
    }

    public static void bar() {
        log.info("bar()");
    }

}
