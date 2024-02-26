package cn.ghx.xboot.config.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Data
@Configuration
@ConfigurationProperties(prefix = "app.security")
public class SecurityParam {
    private List<String> ignores;

    public String[] getIgnoreArray(){
        return ignores.toArray(new String[0]);
    }
}
