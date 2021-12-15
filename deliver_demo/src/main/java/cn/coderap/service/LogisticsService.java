package cn.coderap.service;

import cn.coderap.pojo.Order;
import cn.coderap.util.TimeWatchUtil;
import org.springframework.stereotype.Service;

@Service
public class LogisticsService {

    /**
     * 发货
     * @param order
     */
    public void deliver(Order order) {
        TimeWatchUtil.mockElapse(100, 5000);
    }
}
