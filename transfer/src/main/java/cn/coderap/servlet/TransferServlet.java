package cn.coderap.servlet;

import cn.coderap.factory.ProxyFactory;
import cn.coderap.service.TransferService;
import cn.coderap.utils.JsonUtil;
import cn.coderap.pojo.Result;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "transferServlet", urlPatterns = "/transferServlet")
public class TransferServlet extends HttpServlet {

    private TransferService transferService;

    @Override
    public void init() throws ServletException {
        //在servlet中获取到Spring的IoC容器
        WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(this.getServletContext());
        ProxyFactory proxyFactory = (ProxyFactory) webApplicationContext.getBean("proxyFactory");
        transferService = (TransferService) proxyFactory.getJdkProxy(webApplicationContext.getBean("transferService"));
    }

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
