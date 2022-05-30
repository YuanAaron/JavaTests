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

## CSRF攻击

![image-20220530155512978](https://cdn.jsdelivr.net/gh/YuanAaron/BlogImage@master//2022/image-20220530155512978.png)

CSRF攻击根源：请求不是从已登录的站点（正常站点）发出的，而是从恶意站点发出。

防止受到CSRF攻击的方式：CSRF Token，即由服务端生成并设置到你的浏览器的Cookie中，客户端每次都会从Cookie中把该token读取出来，服务端要求你的每个请求都要携带这个token，提交到服务端后，服务端会比较该token与服务端session中的token是否一致。

CSRF攻击对于无状态应用来说是无效的，对于session类应用需要预防一下。比如对于我们将要实现的前后端分离，它就是无状态的，因为访问每个API都需要携带token，而恶意网站无法获取token，所以它对CSRF攻击是天然免疫的。

## rememberMe功能

解决session过期后用户仍然无需登录，可以直接访问的问题。

Spring Security提供的rememberMe的原理：使用Cookie存储用户名、过期时间以及一个hash值，其中hash1=md5(用户名+过期时间+密码+key)。当session过期后，如果时间超过了该Cookie中的过期时间，那么就跳转到登录页面；如果没有，根据用户名从数据库中查出米密码，然后服务端按照md5(用户名+过期时间+密码+key)得到一个hash2值，比较提交的hash1和服务端计算得到的hash1，如果两者一致，就无需登录。

具体参考rememberMe代码

## logout

参考登出的代码

