package cn.ghx.xboot.group;

import cn.ghx.xboot.common.Result;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/group")
public class GroupController {
    public static final String POST = "";
    public static final String GET_LIST = "/list";
    public static final String GET = "/{id}";
    public static final String DELETE = "/{id}";
    public static final String GET_TREE = "/tree";
    public static final String POST_USER = "/{id}/user";
    public static final String DELETE_USER = "/{id}/user";
    private final GroupService service;

    public GroupController(GroupService service) {
        this.service = service;
    }

    @GetMapping(GET)
    public Result<Group> getById(@PathVariable String id){
        return Result.success(service.getById(id));
    }
    @DeleteMapping(DELETE_USER)
    public Result<Boolean> removeUser(@PathVariable String id,@RequestParam List<String> userIds){
        return Result.success(service.removeUser(id,userIds));
    }
    @PostMapping(POST_USER)
    public Result<Boolean> addUser(@PathVariable String id,@RequestBody List<String> userIds){
        return Result.success(service.addUser(id,userIds));
    }

    @GetMapping(GET_TREE)
    public Result<List<Group>> getTree(){
        return Result.success(service.getTree());
    }
    @DeleteMapping(DELETE)
    public Result<Boolean> delete(@PathVariable String id){
        return Result.success(service.removeById(id));
    }

    @PostMapping(POST)
    public Result<Boolean> save(@RequestBody Group item){
        return Result.success(service.saveOrUpdate(item));
    }

    @GetMapping(GET_LIST)
    public Result<Page<Group>> getList(
            @RequestParam(required = false) String keyword ,
            @RequestParam(required = false,defaultValue = "1") Integer page ,
            @RequestParam(required = false,defaultValue = "10") Integer size){
        return Result.success(service.query(keyword,page,size));
    }
}
