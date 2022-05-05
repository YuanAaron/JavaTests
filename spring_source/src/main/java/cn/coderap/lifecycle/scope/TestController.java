package cn.coderap.lifecycle.scope;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
public class TestController {

    @Lazy // singleton（TestController）使用其他域都要加@Lazy
    @Autowired
    private BeanForRequest beanForRequest;
    @Lazy
    @Autowired
    private BeanForSession beanForSession;
    @Lazy
    @Autowired
    private BeanForApplication beanForApplication;

    @GetMapping(value = "/get")
    public String test(HttpServletRequest request, HttpSession session) {
        ServletContext sc = request.getServletContext();
        String sb = "<ul>" +
                        "<li>" + "request scope: " + beanForRequest + "</li>" +
                        "<li>" + "session scope: " + beanForSession + "</li>" +
                        "<li>" + "application scope: " + beanForApplication + "</li>" +
                    "</ul>";
        return sb;
    }
}
