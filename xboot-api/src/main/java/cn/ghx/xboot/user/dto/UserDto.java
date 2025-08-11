package cn.ghx.xboot.user.dto;

import cn.ghx.xboot.role.Role;
import cn.ghx.xboot.user.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserDto extends User {
    private List<Role> roles;
}
