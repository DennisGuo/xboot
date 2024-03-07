package cn.ghx.xboot.dict;

import cn.ghx.xboot.common.Result;
import cn.ghx.xboot.setting.Setting;
import cn.ghx.xboot.setting.SettingService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dict")
public class DictController {

    public static final String POST = "";
    public static final String GET_LIST = "/list";
    public static final String DELETE = "/{id}";
    public static final String GET_BY_CODE = "/{code}";

    private final DictService service;

    public DictController(DictService service) {
        this.service = service;
    }
    @GetMapping(GET_BY_CODE)
    public Result<Dict> getByCode(@PathVariable String code){
        return Result.success(service.getByCode(code));
    }


    @DeleteMapping(DELETE)
    public Result<Boolean> delete(@PathVariable String id){
        return Result.success(service.removeById(id));
    }

    @PostMapping(POST)
    public Result<Boolean> save(@RequestBody Dict item){
        return Result.success(service.saveItem(item));
    }

    @GetMapping(GET_LIST)
    public Result<Page<Dict>> getList(
            @RequestParam(required = false) String keyword ,
            @RequestParam(required = false,defaultValue = "1") Integer page ,
            @RequestParam(required = false,defaultValue = "10") Integer size){
        return Result.success(service.query(keyword,page,size));
    }
}
