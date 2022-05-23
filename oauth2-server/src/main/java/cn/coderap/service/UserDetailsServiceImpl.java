package cn.coderap.service;

import cn.coderap.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * 加载用户信息
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private List<User> userList;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void initData() {
        userList = new ArrayList<>();
        String password = passwordEncoder.encode("123456");
        userList.add(new User("zhangsan",password, AuthorityUtils.commaSeparatedStringToAuthorityList("admin")));
        userList.add(new User("lisi",password, AuthorityUtils.commaSeparatedStringToAuthorityList("client")));
        userList.add(new User("wangwu",password, AuthorityUtils.commaSeparatedStringToAuthorityList("client")));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User res = null;
        for (User user : userList) {
            if (user.getUsername().equals(username)) {
                res = user;
                break;
            }
        }
        if (res != null) {
            return res;
        } else {
            throw new UsernameNotFoundException("用户名或密码错误！");
        }
    }
}
