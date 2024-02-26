package cn.ghx.xboot.user.vo;

import lombok.Data;

@Data
public class ChangePasswordVo {
    private String oldPwd;
    private String newPwd;
}
