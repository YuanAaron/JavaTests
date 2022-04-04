package cn.coderap.controller;

import cn.coderap.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private TestService testService;

    @RequestMapping("/test1")
    public String test1() {
        return testService.test1();
    }

    @RequestMapping("/test2")
    public String test2() {
        return testService.test2();
    }
}
