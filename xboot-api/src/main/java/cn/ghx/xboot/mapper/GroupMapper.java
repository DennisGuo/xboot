package cn.ghx.xboot.mapper;

import cn.ghx.xboot.group.Group;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author ghx
* @createDate 2024-02-23 15:14:54
*/
public interface GroupMapper extends BaseMapper<Group> {

    Boolean addUser(@Param("id") String id, @Param("userIds") List<String> userIds);

    Boolean removeUser(@Param("id")String id, @Param("userIds") List<String> userIds);
}




