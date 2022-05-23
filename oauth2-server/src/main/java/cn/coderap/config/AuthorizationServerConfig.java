package cn.coderap.config;

import cn.coderap.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;

/**
 * 认证服务器
 * 1. 授权码模式
 * 1.1 浏览器中输入：http://localhost:9001/oauth/authorize?response_type=code&client_id=admin&redirect_uri=https://www.baidu.com&scope=all，然后允许，确认授权；
 * 1.2 postman(POST): http://localhost:9401/oauth/token，Basic认证中用户名和密码分别为admin和admin123456，body参数grant_type=authorization_code、code=1.1中得到的授权码、redirect_uri=https://www.baidu.com
 * 1.3 postman(GET): 127.0.0.1:9001/user/getCurrentUser，Header中添加Authorization=Bearer 1.2中得到的access_token
 *
 * 2. 密码模式
 * 2.1 postman(POST): http://localhost:9001/oauth/token，Basic认证中用户名和密码分别为admin和admin123456，body参数username=zhangsan、password=123456、grant_type=password
 * 2.2 postman(GET): 127.0.0.1:9001/user/getCurrentUser，Header中添加Authorization=Bearer 2.1中得到的access_token
 */
@EnableAuthorizationServer
@Configuration
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                //配置client_id
                .withClient("admin")
                //配置client_secret
                .secret(passwordEncoder.encode("admin123456"))
                //配置grant_type，表示授权类型
                .authorizedGrantTypes("authorization_code","password", "refresh_token")
                //配置申请的权限范围
                .scopes("all")
                //配置访问token的有效期
                .accessTokenValiditySeconds(3600)
                //配置刷新token的有效期
                .refreshTokenValiditySeconds(86400)
                //配置redirect_uri，用于授权成功后跳转
                .redirectUris("https://www.baidu.com");
    }

    /**
     * 使用密码模式需要配置
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.authenticationManager(authenticationManager)
                .userDetailsService(userDetailsServiceImpl);
    }
}
