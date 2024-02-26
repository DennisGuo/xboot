package cn.ghx.xboot.mapper;

import cn.ghx.xboot.menu.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

/**
* @author ghx
* @createDate 2024-01-16 17:51:52
*/
public interface MenuMapper extends BaseMapper<Menu> {

    Page<Menu> query(@Param("keyword") String keyword, Page<Menu> page);
}




