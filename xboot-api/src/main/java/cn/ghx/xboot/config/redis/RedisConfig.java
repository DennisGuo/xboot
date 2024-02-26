package cn.ghx.xboot.config.redis;

import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;


@Slf4j
@Configuration
public class RedisConfig {

    @Bean
    public RedissonClient redissonClient(RedisParam param) {
        Config config = new Config();
        String addr = "redis://%s:%s".formatted(param.getHost(), param.getPort());
        if (StringUtils.hasText(param.getPassword())) {
            addr = "redis://%s@%s:%s".formatted(param.getPassword(), param.getHost(), param.getPort());
        }

        log.info("redis link: {}", addr);
        config.useSingleServer()
                .setAddress(addr)
                .setDatabase(param.getDatabase());

        return Redisson.create(config);
    }
}
