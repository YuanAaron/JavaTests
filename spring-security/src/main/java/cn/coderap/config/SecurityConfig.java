package cn.coderap.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import java.util.Map;

@Slf4j
//@EnableWebSecurity
@EnableWebSecurity(debug = true) // 输出额外的日志信息（在日志中搜索security debugger即可）
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests(req -> req.anyRequest().authenticated())
                .formLogin(form -> form.loginPage("/login")
                        .usernameParameter("username1")
                        .loginProcessingUrl("/login") // 对应表单中的action
                        // 前后端分离项目不用后端转发/跳转，所以不用successForwardUrl()
                        .successHandler(jsonAuthenticationSuccessHandler())
                        .failureHandler(jsonAuthenticationFailureHandler())
                        .permitAll())
                .httpBasic(Customizer.withDefaults())
                .csrf(Customizer.withDefaults())
                .logout(logout -> logout.logoutUrl("/perform_logout") // 对应表单中的action
                        .logoutSuccessHandler(jsonLogoutSuccessHandler()))
                .rememberMe(rememberMe -> rememberMe.tokenValiditySeconds(30*24*3600).rememberMeCookieName("someKeyToRemember"));
    }

    private LogoutSuccessHandler jsonLogoutSuccessHandler() {
        return (req, res, auth) -> {
            if (auth != null && auth.getDetails() != null) {
                req.getSession().invalidate();
            }
            res.setStatus(HttpStatus.OK.value());
            res.getWriter().println();
            log.debug("成功登出");
        };
    }

    private AuthenticationFailureHandler jsonAuthenticationFailureHandler() {
        return (req, res, exp) -> {
            ObjectMapper objectMapper = new ObjectMapper();
            res.setStatus(HttpStatus.UNAUTHORIZED.value());
            res.setContentType(MediaType.APPLICATION_JSON_VALUE);
            res.setCharacterEncoding("UTF-8");
            var errData = Map.of(
                    "title","认证失败",
                    "details",exp.getMessage()
            );
            res.getWriter().println(objectMapper.writeValueAsString(errData));
        };
    }

    // 抽取出来
    private AuthenticationSuccessHandler jsonAuthenticationSuccessHandler() {
        return (req, res, auth) -> {
            ObjectMapper objectMapper = new ObjectMapper(); // 可以将一个对象转换成json字符串
            res.setStatus(HttpStatus.OK.value());
            res.getWriter().println(objectMapper.writeValueAsString(auth));
            log.debug("认证成功");
        };
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // 该请求路径禁止启动security filter chain，即不进行安全检查，一般用于忽略静态资源
        web.ignoring().mvcMatchers("/public/**")
                // 忽略常见静态资源的位置，一般js、css、图片等存放在固定位置
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user")
                .password(passwordEncoder().encode("123456"))
                .roles("ADMIN");
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
