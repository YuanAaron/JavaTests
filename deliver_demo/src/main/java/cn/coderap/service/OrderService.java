package cn.coderap.service;

import cn.coderap.pojo.Order;
import cn.coderap.util.TimeWatchUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrderService {

    @Autowired
    private LogisticsService logisticsService;

    public Boolean deliver(Long id) {
        TimeWatchUtil timeWatch = new TimeWatchUtil(log);

        // 检查权限
        checkPrivilege();
        timeWatch.log("check privilege");

        // 检查订单是否存在
        Order order = checkOrder(id);
        timeWatch.log("check order");

        // 更新订单状态为发货中
        updateOrder(order);
        timeWatch.log("update status to delivering");

        // 远程调用物流接口发货
        logisticsService.deliver(order);
        timeWatch.log("remote call logistics deliver api");

        // 更新订单状态为发货完成
        updateOrder(order);
        timeWatch.log("update status to delivered");

        timeWatch.logTotal("finished one order delivering");

        return true;
    }

    private void updateOrder(Order order) {
        TimeWatchUtil.mockElapse(5, 20);
    }

    private Order checkOrder(Long id) {
        Order order = new Order();
        order.setId(id);

        TimeWatchUtil.mockElapse(15, 100);

        return order;
    }

    private void checkPrivilege() {
        TimeWatchUtil.mockElapse(10, 200);
    }
}