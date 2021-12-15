package cn.coderap.controller;

import cn.coderap.pojo.Result;
import cn.coderap.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/deliver/{id}")
    public Result<Boolean> order(@PathVariable Long id) {
        //放到日志上下文中
        MDC.put("orderId", String.valueOf(id));
        MDC.put("action", "发货");
        log.info("receive one order to deliver");
        return new Result<>(orderService.deliver(id));
    }
}