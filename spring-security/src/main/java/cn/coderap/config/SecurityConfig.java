package cn.coderap.config;

import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

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
