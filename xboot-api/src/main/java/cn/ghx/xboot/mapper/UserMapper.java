package cn.ghx.xboot.mapper;

import cn.ghx.xboot.menu.Menu;
import cn.ghx.xboot.user.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author ghx
*/
public interface UserMapper extends BaseMapper<User> {

    List<Menu> getUserMenu(@Param("userId") String userId);
}




