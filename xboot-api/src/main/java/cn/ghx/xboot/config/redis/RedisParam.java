package cn.ghx.xboot.config.redis;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "redis")
public class RedisParam {
    private String host = "localhost";
    private Integer port = 6379;
    private Integer database = 0;
    private String password;
}
