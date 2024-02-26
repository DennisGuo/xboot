package cn.ghx.xboot.setting;

import cn.ghx.xboot.common.Result;
import cn.ghx.xboot.menu.Menu;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/setting")
public class SettingController {

    public static final String POST = "";
    public static final String GET_LIST = "/list";
    public static final String DELETE = "/{id}";

    private final SettingService service;

    public SettingController(SettingService service) {
        this.service = service;
    }

    @DeleteMapping(DELETE)
    public Result<Boolean> delete(@PathVariable String id){
        return Result.success(service.removeById(id));
    }

    @PostMapping(POST)
    public Result<Boolean> save(@RequestBody Setting item){
        return Result.success(service.saveOrUpdate(item));
    }

    @GetMapping(GET_LIST)
    public Result<Page<Setting>> getList(
            @RequestParam(required = false) String keyword ,
            @RequestParam(required = false,defaultValue = "1") Integer page ,
                                         @RequestParam(required = false,defaultValue = "10") Integer size){
        return Result.success(service.query(keyword,page,size));
    }
}
