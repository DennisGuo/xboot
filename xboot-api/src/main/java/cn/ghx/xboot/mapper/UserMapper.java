package cn.ghx.xboot.mapper;

import cn.ghx.xboot.menu.Menu;
import cn.ghx.xboot.role.Role;
import cn.ghx.xboot.user.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author ghx
*/
public interface UserMapper extends BaseMapper<User> {

    List<Menu> getUserMenu(@Param("userId") String userId);

    Page<User> query(@Param("keyword") String keyword,
                     @Param("roleId") String roleId,
                     @Param("groupId") String groupId, Page<User> page);

    List<Role> getUserRoles(@Param("userId")String userId);
}




