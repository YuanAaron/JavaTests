package cn.coderap.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect // 注意此注解并未被Spring管理
@Slf4j
public class Aspect2 {

    @Before("execution(* cn.coderap.aop.Service2.*())")
    public void before() {
        log.info("before()");
    }
}
