package cn.ghx.xboot.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {
    /**
     * 访问TOKEN
     */
    private String accessToken;
    /**
     * 刷新TOKEN
     */
    private String refreshToken;
    /**
     * 过期时间，单位秒
     */
    private Integer expiredIn;
}
