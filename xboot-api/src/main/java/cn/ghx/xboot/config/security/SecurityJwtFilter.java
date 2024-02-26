package cn.ghx.xboot.config.security;

import cn.ghx.xboot.common.BaseContext;
import cn.ghx.xboot.user.User;
import cn.ghx.xboot.user.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import java.io.IOException;
import java.util.List;


@Slf4j
@Component
public class SecurityJwtFilter extends OncePerRequestFilter {

    private final UserService userService;
    private final HandlerMappingIntrospector introspector;

    private final SecurityParam securityParam;

    @Lazy
    public SecurityJwtFilter(UserService userService, HandlerMappingIntrospector introspector, SecurityParam securityParam) {
        this.userService = userService;
        this.introspector = introspector;
        this.securityParam = securityParam;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            // 判断
            boolean ignore = handleIgnore(request);
            log.debug("uri: {}, ignore:{}", request.getRequestURI(), ignore);
            if (!ignore) {
                String token = getToken(request);
                if (StringUtils.hasText(token)) {
                    User user = userService.findByToken(token);
                    if (user != null) {
                        BaseContext.setUser(user);
                        // 参考：https://docs.spring.io/spring-security/reference/servlet/authentication/architecture.html
                        SecurityContext context = SecurityContextHolder.createEmptyContext();
                        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, "", List.of());
                        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        context.setAuthentication(authentication);
                        SecurityContextHolder.setContext(context);
                    }
                }
            }
            filterChain.doFilter(request, response);
        }  finally {
            BaseContext.clear();
        }

    }


    private boolean handleIgnore(HttpServletRequest request) {
        MvcRequestMatcher.Builder builder = new MvcRequestMatcher.Builder(introspector);
        for (String path : securityParam.getIgnores()) {
            boolean match = builder.pattern(path).matches(request);
            if (match) return true;
        }
        return false;
    }

    private String getToken(HttpServletRequest request) {

        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(header) && header.startsWith("Bearer ")) {
            return header.substring("Bearer ".length());
        }
        return null;
    }

}
