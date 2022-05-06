package cn.coderap.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect // 注意此注解并未被Spring管理
public class Aspect1 {

    private static final Logger log = LoggerFactory.getLogger(Service1.class);

    @Before("execution(* cn.coderap.aop.Service1.*())")
    public void before() {
        log.info("before()");
    }
}
