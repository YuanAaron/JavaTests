## 过滤器Filter

任何Spring Web应用本质上只是一个Servlet，Security Filter在HTTP请求到达Controller之前过滤每个传入的HTTP请求。

![image-20220527143537263](https://cdn.jsdelivr.net/gh/YuanAaron/BlogImage@master//2022/image-20220527143537263.png)

## 过滤器示例

![image-20220527142615365](https://cdn.jsdelivr.net/gh/YuanAaron/BlogImage@master//2022/image-20220527142615365.png)

Spring Security Filter Chain

![image-20220527144923844](https://cdn.jsdelivr.net/gh/YuanAaron/BlogImage@master//2022/image-20220527144923844.png)

## 常见的内建过滤器

空项目，只有一个SecurityConfig启动，会启动一个DefaultSecurityFilterChain，具体包括：

+ org.springframework.security.web.context.request.async.WebAsyncManagerIntegrationFilter
+ org.springframework.security.web.context.SecurityContextPersistenceFilter
+ org.springframework.security.web.header.HeaderWriterFilter
+ org.springframework.security.web.csrf.CsrfFilter
+ org.springframework.security.web.authentication.logout.LogoutFilter
+ org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
+ org.springframework.security.web.authentication.ui.DefaultLoginPageGeneratingFilter
+ org.springframework.security.web.authentication.ui.DefaultLogoutPageGeneratingFilter
+ org.springframework.security.web.authentication.www.BasicAuthenticationFilter
+ org.springframework.security.web.savedrequest.RequestCacheAwareFilter
+ org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestFilter
+ org.springframework.security.web.authentication.AnonymousAuthenticationFilter
+ org.springframework.security.web.session.SessionManagementFilter
+ org.springframework.security.web.access.ExceptionTranslationFilter
+ org.springframework.security.web.access.intercept.FilterSecurityInterceptor

![image-20220527154221577](https://cdn.jsdelivr.net/gh/YuanAaron/BlogImage@master//2022/image-20220527154221577.png)

## HTTP 

### HTTP 请求

![image-20220529215508753](https://cdn.jsdelivr.net/gh/YuanAaron/BlogImage@master//2022/image-20220529215508753.png)

### HTTP 响应

![image-20220529215616483](https://cdn.jsdelivr.net/gh/YuanAaron/BlogImage@master//2022/image-20220529215616483.png)

#### HTTP 响应状态码

1. [信息响应](https://developer.mozilla.org/zh-CN/docs/Web/HTTP/status#信息响应) (`100`–`199`)
2. [成功响应](https://developer.mozilla.org/zh-CN/docs/Web/HTTP/status#成功响应) (`200`–`299`)
3. [重定向消息](https://developer.mozilla.org/zh-CN/docs/Web/HTTP/status#重定向消息) (`300`–`399`)
4. [客户端错误](https://developer.mozilla.org/zh-CN/docs/Web/HTTP/status#客户端错误响应) (`400`–`499`)
5. [服务端错误](https://developer.mozilla.org/zh-CN/docs/Web/HTTP/status#服务端错误响应) (`500`–`599`)

其中，认证失败返回401，权限不足时返回403，方法访问有问题时返回405。

### HTTP Basic Auth 认证流程

![WeChat54ea01074f32b3b2a03bda7e1fc145c3](https://cdn.jsdelivr.net/gh/YuanAaron/BlogImage@master//2022/WeChat54ea01074f32b3b2a03bda7e1fc145c3.png)

## 安全配置

```java
//@EnableWebSecurity
@EnableWebSecurity(debug = true) // 输出额外的日志信息（在日志中搜索security debugger即可）
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
//                .antMatchers("/api/**")
                .anyRequest()
                .authenticated()
                .and()
//                .formLogin()
                .formLogin().disable()
//                .and()
                .httpBasic(Customizer.withDefaults())
//                .httpBasic()
//                .and()
                .csrf().disable(); // post请求需要关闭csrf

//        http.authorizeRequests(req -> req.antMatchers("/api/**").authenticated())
//                .formLogin(form -> form.disable())
//                .httpBasic(Customizer.withDefaults())
//                .csrf(csrf -> csrf.disable());
    }
  
    @Override
    public void configure(WebSecurity web) throws Exception {
      // 该请求路径禁止启动security filter chain，即不进行安全检查，一般用于忽略静态资源
      web.ignoring().mvcMatchers("/public/**");
    }
}
```

## 禁止日志输出初始密码

正常情况下，日志中会输出默认用户user的初始密码，通过在yml中配置用户名和密码，就可以禁止输出该密码。

```yml
spring:
  security:
    user:
      name: user
      password: 123456
      roles: ADMIN
```

## 定制登录页面

参考定制登录页的代码

## csrf logout rememberMe

