package cn.ghx.xboot.mapper;

import cn.ghx.xboot.role.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author ghx
* @createDate 2024-01-16 17:52:38
*/
public interface RoleMapper extends BaseMapper<Role> {

    Boolean removeRoleUser(@Param("id") String id,@Param("userIds") List<String> userIds);

    Boolean removeRoleMenu(@Param("id") String id);

    Boolean saveRoleMenu(@Param("id") String id, @Param("menuId")String menuId);
    Boolean saveRoleMenuBatch(@Param("id") String id, @Param("menuIds")List<String> menuIds);

    Boolean addRoleUser(@Param("id") String id, @Param("userIds") List<String> userIds);

    List<String> getRoleMenuIds(@Param("id")String id);
}




