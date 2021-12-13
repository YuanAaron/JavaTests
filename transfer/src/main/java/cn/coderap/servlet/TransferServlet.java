package cn.coderap.servlet;

import cn.coderap.factory.BeanFactory;
import cn.coderap.factory.ProxyFactory;
import cn.coderap.service.TransferService;
import cn.coderap.service.impl.TransferServiceImpl;
import cn.coderap.utils.JsonUtil;
import cn.coderap.pojo.Result;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "transferServlet", urlPatterns = "/transferServlet")
public class TransferServlet extends HttpServlet {

    //1. 实例化service层对象
//    private TransferService transferService = new TransferServiceImpl();
//    private TransferService transferService = (TransferService) BeanFactory.getBean("transferService");

    // 从代理工厂获取代理对象
    private Object obj = BeanFactory.getBean("transferService");
    private TransferService transferService = (TransferService) ProxyFactory.getInstance().getJdkProxy(obj);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //设置请求体的字符编码
        req.setCharacterEncoding("UTF-8");

        String fromCardNo = req.getParameter("fromCardNo");
        String toCardNo = req.getParameter("toCardNo");
        String money = req.getParameter("money");

        Result result = new Result();

        try {
            //2.调用service层方法
            transferService.transfer(fromCardNo, toCardNo, Integer.parseInt(money));
            result.setStatus("200");
        } catch (Exception e) {
            e.printStackTrace();
            result.setStatus("201");
            result.setMsg(e.toString());
        }

        //响应
        resp.setContentType("application/json;charset=UTF-8");
        resp.getWriter().print(JsonUtil.object2Json(result));

    }
}
