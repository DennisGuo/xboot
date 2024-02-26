package cn.ghx.xboot.menu;

import cn.ghx.xboot.common.Result;
import cn.ghx.xboot.user.User;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/menu")
public class MenuController {

    public static final String POST = "";
    public static final String GET_TREE = "/tree";
    public static final String GET_LIST = "/list";
    public static final String DELETE = "/{id}";

    private final MenuService service;

    public MenuController(MenuService service) {
        this.service = service;
    }

    @GetMapping(GET_LIST)
    public Result<Page<Menu>> getList(
            @RequestParam(required = false) String keyword ,
            @RequestParam(required = false,defaultValue = "1") Integer page ,
            @RequestParam(required = false,defaultValue = "10") Integer size){
        return Result.success(service.query(keyword,page,size));
    }

    @DeleteMapping(DELETE)
    public Result<Boolean> delete(@PathVariable String id){
        return Result.success(service.removeById(id));
    }

    @PostMapping(POST)
    public Result<Boolean> save(@RequestBody Menu item){
        return Result.success(service.saveItem(item));
    }

    @GetMapping(GET_TREE)
    public Result<List<Menu>> getMenuTree(@RequestParam(required = false) Integer type){
        return Result.success(service.getMenuTree(type));
    }
}
