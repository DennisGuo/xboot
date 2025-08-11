package cn.ghx.xboot.user;

import cn.ghx.xboot.TestBase;
import cn.ghx.xboot.user.vo.UserSaveVo;
import cn.hutool.crypto.SecureUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

class UserServiceTest extends TestBase {

    @Autowired
    private UserService service;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void save(){
        UserSaveVo user =new UserSaveVo();
        user.setName("管理员");
        user.setPassword("qwer1234");
        user.setUsername("admin");
        Boolean rs =service.saveItem(user);
        Assertions.assertTrue(rs);
    }

    @Test
    public void md5(){
        String md5 = SecureUtil.md5("qwer1234");
        print(md5);
        print(passwordEncoder.encode(md5));
    }

    @Test
    void login() {
    }

    @Test
    void findByUsername() {
    }

    @Test
    void logout() {
    }

    @Test
    void saveItem() {
    }

    @Test
    void refresh() {
    }

    @Test
    void findByToken() {
    }

    @Test
    void getMeMenu() {
    }

    @Test
    void query() {
        print(service.query(null,null,"1760953247121846274",1,10));
    }

    @Test
    void createCaptcha() {
    }

    @Test
    void changeMePassword() {
    }
}