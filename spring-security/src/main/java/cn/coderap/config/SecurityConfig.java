package cn.coderap.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
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
        http.authorizeRequests(req -> req.anyRequest().authenticated())
                .formLogin(form -> form.loginPage("/login")
                        .usernameParameter("username1")
                        .loginProcessingUrl("/login") // 对应表单中的action
                        .permitAll())
                .httpBasic(Customizer.withDefaults())
                .csrf(Customizer.withDefaults())
                .logout(logout -> logout.logoutUrl("/perform_logout")); // 对应表单中的action
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // 该请求路径禁止启动security filter chain，即不进行安全检查，一般用于忽略静态资源
        web.ignoring().mvcMatchers("/public/**")
                // 忽略常见静态资源的位置，一般js、css、图片等存放在固定位置
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }
}
