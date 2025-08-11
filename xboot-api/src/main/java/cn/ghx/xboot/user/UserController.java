package cn.ghx.xboot.user;

import cn.ghx.xboot.common.BaseContext;
import cn.ghx.xboot.common.Result;
import cn.ghx.xboot.menu.Menu;
import cn.ghx.xboot.role.Role;
import cn.ghx.xboot.user.dto.CaptchaDto;
import cn.ghx.xboot.user.dto.LoginDto;
import cn.ghx.xboot.user.dto.UserDto;
import cn.ghx.xboot.user.vo.ChangePasswordVo;
import cn.ghx.xboot.user.vo.LoginVo;
import cn.ghx.xboot.user.vo.UserSaveVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    public static final String GET_ME = "/me";
    public static final String DELETE = "/{id}";
    public static final String PUT_ME_PASSWORD= "/me/password";
    public static final String GET_CAPTCHA = "/captcha";
    public static final String POST_LOGIN = "/login";
    public static final String POST_TOKEN_REFRESH = "/token/refresh";
    public static final String GET_LOGOUT = "/logout";
    public static final String GET_ME_MENU = "/me/menu";
    public static final String GET_ME_ROLES = "/me/roles";
    public static final String GET_LIST = "/list";
    public static final String GET_ROLES = "/roles";
    public static final String POST = "";

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }
    @GetMapping(GET_ROLES)
    public  Result<List<Role>> getRoles(@RequestParam String id) {
        return Result.success(service.getUserRoles(id));
    }
    @DeleteMapping(DELETE)
    public Result<Boolean> remove(@PathVariable String id){
        return Result.success(service.removeById(id));
    }
    @PutMapping(PUT_ME_PASSWORD)
    public Result<Boolean> changePassword(@RequestBody ChangePasswordVo vo){
        return Result.success(service.changeMePassword(vo));
    }

    @PostMapping(POST)
    public Result<Boolean> save(@RequestBody UserSaveVo item){
        return Result.success(service.saveItem(item));
    }
    @GetMapping(GET_CAPTCHA)
    public Result<CaptchaDto> getCaptcha(){
        return Result.success(service.createCaptcha());
    }

    @GetMapping(GET_LIST)
    public Result<Page<UserDto>> getList(
            @RequestParam(required = false) String keyword ,
            @RequestParam(required = false) String roleId ,
            @RequestParam(required = false) String groupId ,
            @RequestParam(required = false,defaultValue = "1") Integer page ,
            @RequestParam(required = false,defaultValue = "10") Integer size){
        return Result.success(service.query(keyword,roleId,groupId,page,size));
    }
    @GetMapping(GET_ME_MENU)
    public Result<List<Menu>> getMeMenu(@RequestParam(required = false) Boolean tree){
        return Result.success(service.getMeMenu(tree));
    }
    @GetMapping(GET_ME_ROLES)
    public Result<List<Role>> getMeRoles() {
        return Result.success(service.getMeRoles());
    }
    @GetMapping(GET_ME)
    public Result<User> getMe(){
        return Result.success(BaseContext.getUser());
    }

    @GetMapping(GET_LOGOUT)
    public Result<Boolean> logout(@RequestHeader(HttpHeaders.AUTHORIZATION) String header){
        String token = null;
        if (StringUtils.hasText(header) && header.startsWith("Bearer ")) {
            token =  header.substring("Bearer ".length());
        }
        return Result.success(service.logout(token));
    }
    @PostMapping(POST_LOGIN)
    public Result<LoginDto> login(@RequestBody LoginVo vo){
        return Result.success(service.login(vo));
    }

    @PostMapping(POST_TOKEN_REFRESH)
    public Result<LoginDto> refreshToken(String token){
        return Result.success(service.refresh(token));
    }
}
