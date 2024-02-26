package cn.ghx.xboot.role;

import cn.ghx.xboot.common.Result;
import cn.ghx.xboot.setting.Setting;
import cn.ghx.xboot.setting.SettingService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {
    public static final String POST = "";
    public static final String POST_ROLE_MENU = "/{id}/menu";
    public static final String GET_ROLE_MENU_IDS = "/{id}/menu/ids";
    public static final String POST_ROLE_USER = "/{id}/user";
    public static final String DELETE_ROLE_USER = "/{id}/user";
    public static final String GET_LIST = "/list";
    public static final String DELETE = "/{id}";

    private final RoleService service;

    public RoleController(RoleService service) {
        this.service = service;
    }

    @GetMapping(GET_ROLE_MENU_IDS)
    public Result<List<String>> getRoleMenuIds(@PathVariable String id){
        return Result.success(service.getRoleMenuIds(id));
    } @PostMapping(POST_ROLE_MENU)
    public Result<Boolean> saveRoleMenu(@PathVariable String id, @RequestBody List<String> menuIds){
        return Result.success(service.saveRoleMenu(id,menuIds));
    }
    @PostMapping(POST_ROLE_USER)
    public Result<Boolean> addRoleUser(@PathVariable String id, @RequestBody List<String> userIds){
        return Result.success(service.addRoleUser(id,userIds));
    }
    @DeleteMapping(DELETE_ROLE_USER)
    public Result<Boolean> removeRoleUser(@PathVariable String id, @RequestParam List<String> userIds){
        return Result.success(service.removeRoleUser(id,userIds));
    }

    @DeleteMapping(DELETE)
    public Result<Boolean> delete(@PathVariable String id){
        return Result.success(service.removeById(id));
    }

    @PostMapping(POST)
    public Result<Boolean> save(@RequestBody  Role item){
        return Result.success(service.saveOrUpdate(item));
    }

    @GetMapping(GET_LIST)
    public Result<Page< Role>> getList(
            @RequestParam(required = false) String keyword ,
            @RequestParam(required = false,defaultValue = "1") Integer page ,
            @RequestParam(required = false,defaultValue = "10") Integer size){
        return Result.success(service.query(keyword,page,size));
    }
}
