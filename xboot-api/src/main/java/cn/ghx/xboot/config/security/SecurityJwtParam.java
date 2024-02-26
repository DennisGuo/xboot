package cn.ghx.xboot.config.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "app.jwt")
public class SecurityJwtParam {
    private String secret;
    private Integer expired;
    private Integer expiredRefresh;
}
