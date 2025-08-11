package cn.huitao.forest.uav.prevention.user.dto;

import cn.huitao.forest.uav.prevention.role.Role;
import cn.huitao.forest.uav.prevention.user.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserDto extends User {
    private List<Role> roles;
}
