package cn.ghx.xboot.user.vo;

import lombok.Data;

@Data
public class LoginVo {
    /**
     * 用户名
     */
    private String username;
    /**
     * MD5加密后的密文
     */
    private String password;

    /**
     * 验证码
     */
    private String code;
    /**
     * 随机字符串
     */
    private String random;
}
