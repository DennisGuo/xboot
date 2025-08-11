package cn.huitao.forest.uav.prevention.user.vo;

import cn.huitao.forest.uav.prevention.user.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserSaveVo extends User {
    /**
     * 角色ID列表
     */
    private List<String> roleIds;
}
