package cn.ghx.xboot.user.group;

import cn.ghx.xboot.common.Result;
import cn.ghx.xboot.dict.Dict;
import cn.ghx.xboot.dict.DictService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/group")
public class UserGroupController {
    public static final String POST = "";
    public static final String GET_LIST = "/list";
    public static final String DELETE = "/{id}";

    private final UserGroupService service;

    public UserGroupController(UserGroupService service) {
        this.service = service;
    }

    @DeleteMapping(DELETE)
    public Result<Boolean> delete(@PathVariable String id){
        return Result.success(service.removeById(id));
    }

    @PostMapping(POST)
    public Result<Boolean> save(@RequestBody  UserGroup item){
        return Result.success(service.saveOrUpdate(item));
    }

    @GetMapping(GET_LIST)
    public Result<Page< UserGroup>> getList(
            @RequestParam(required = false) String keyword ,
            @RequestParam(required = false,defaultValue = "1") Integer page ,
            @RequestParam(required = false,defaultValue = "10") Integer size){
        return Result.success(service.query(keyword,page,size));
    }
}
