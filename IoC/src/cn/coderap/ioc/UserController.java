package cn.coderap.ioc;

public class UserController {

    private UserService userService;

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public String getUserName(Long id) {
        return userService.getUserName(id);
    }

}