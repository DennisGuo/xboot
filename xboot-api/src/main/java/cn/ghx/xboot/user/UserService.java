package cn.ghx.xboot.user;

import cn.ghx.xboot.common.BaseContext;
import cn.ghx.xboot.config.security.SecurityJwtParam;
import cn.ghx.xboot.mapper.UserMapper;
import cn.ghx.xboot.menu.Menu;
import cn.ghx.xboot.menu.MenuService;
import cn.ghx.xboot.role.Role;
import cn.ghx.xboot.role.RoleService;
import cn.ghx.xboot.user.dto.CaptchaDto;
import cn.ghx.xboot.user.dto.LoginDto;
import cn.ghx.xboot.user.dto.UserDto;
import cn.ghx.xboot.user.vo.ChangePasswordVo;
import cn.ghx.xboot.user.vo.LoginVo;
import cn.ghx.xboot.user.vo.UserSaveVo;
import cn.hutool.captcha.AbstractCaptcha;
import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author ghx
 * @createDate 2024-01-16 17:35:52
 */
@Service
@Slf4j
public class UserService extends ServiceImpl<UserMapper, User> {

    private static final String REDIS_REFRESH_TOKEN = "user_refresh_token";
    private static final String REDIS_BLOCK_TOKEN = "user_block_token";
    private static final String REDIS_CAPTCHA = "user_captcha";

    @Value("${app.captcha.expired}")
    private Integer captchaExpired;

    private final PasswordEncoder passwordEncoder;
    private final SecurityJwtParam jwtParam;
    private final RedissonClient redisClient;
    private final RoleService roleService;

    @Lazy
    public UserService(PasswordEncoder passwordEncoder,
                       RedissonClient redisClient,
                       RoleService roleService,
                       SecurityJwtParam jwtParam) {
        this.passwordEncoder = passwordEncoder;
        this.redisClient = redisClient;
        this.jwtParam = jwtParam;
        this.roleService = roleService;
    }

    /**
     * 登录
     *
     * @param vo
     * @return
     */
    public LoginDto login(LoginVo vo) {
        log.debug("登录: {} ", vo.toString());
        RMapCache<Object, Object> mapCache = redisClient.getMapCache(REDIS_CAPTCHA);
        String random = vo.getRandom();
        try {
            // 验证码
            String codeErr = "验证码无效";
            Assert.hasText(random, codeErr);
            String code = (String) mapCache.get(random);
            log.debug("验证码：{}", code);
            Assert.isTrue(StringUtils.hasText(code) && code.equalsIgnoreCase(vo.getCode()), codeErr);
            // 用户密码
            String msg = "用户名或密码错误";
            Assert.hasText(vo.getUsername(), msg);
            Assert.hasText(vo.getPassword(), msg);
            User user = findByUsername(vo.getUsername());
            Assert.notNull(user, msg);
            String md5 = vo.getPassword();
            boolean match = passwordEncoder.matches(md5, user.getPassword());
            Assert.isTrue(match, msg);
            return getUserLoginToken(user);
        } finally {
            mapCache.remove(random);
        }
    }

    private LoginDto getUserLoginToken(User user) {
        // token
        Map<String, Object> payload = new HashMap<>() {{
            put("id", user.getId());
            put("username", user.getUsername());
            put("expired", new Date().getTime() + jwtParam.getExpired() * 1000);
        }};

        String token = JWTUtil.createToken(payload, jwtParam.getSecret().getBytes(StandardCharsets.UTF_8));
        String refreshToken = IdUtil.fastSimpleUUID();

        // redis
        redisClient.getMap(REDIS_REFRESH_TOKEN).put(refreshToken, user.getId());

        return new LoginDto(token, refreshToken, jwtParam.getExpired());
    }

    public User findByUsername(String username) {
        return lambdaQuery().eq(User::getUsername, username).one();
    }

    /**
     * 退出登录，写入黑名单block token list
     *
     * @param token
     * @return
     */
    public Boolean logout(String token) {
        if (StringUtils.hasText(token)) {
            JWT jwt = JWTUtil.parseToken(token);
            JSONObject payloads = jwt.getPayloads();
            Long expired = payloads.getLong("expired");
            long ttl = expired - new Date().getTime();
            log.debug("退出登录：ttl = {}", ttl);
            if (ttl > 0) {
                redisClient.getSetCache(REDIS_BLOCK_TOKEN).add(token, ttl, TimeUnit.MILLISECONDS);
            }
        }
        return true;
    }

    /**
     * 更新或者创建用户
     *
     * @param user
     * @return
     */
    /**
     * 更新或者创建用户
     *
     * @param user
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveItem(UserSaveVo user) {
        boolean rs = true;
        if (StringUtils.hasText(user.getId())) {
            // 更新
            rs = saveOrUpdate(user);
        } else {
            // 创建
            String pwd = user.getPassword();
            Assert.hasText(user.getUsername(), "用户名不能为空");
            Assert.hasText(pwd, "密码不能为空");
            String md5 = SecureUtil.md5(pwd);
            user.setPassword(passwordEncoder.encode(md5));
            rs = save(user);
        }
        // 存储角色
        List<String> roleIds = user.getRoleIds();
        if(CollUtil.isNotEmpty(roleIds)) {
            rs = setUserRoles(user.getId(), roleIds);
        }

        return rs;
    }

    public Boolean setUserRoles(String id, List<String> roleIds) {
        removeUserRoles(id);
        return baseMapper.saveUserRoles(id,roleIds, BaseContext.getUserId());
    }

    public Boolean removeUserRoles(String id) {
        return baseMapper.removeUserRoles(id);
    }

    /**
     * 刷新token
     *
     * @param refreshToken
     * @return
     */
    public LoginDto refresh(String refreshToken) {
        String userId = (String) redisClient.getMap(REDIS_REFRESH_TOKEN).get(refreshToken);
        Assert.hasText(userId, "无效的refreshToken");
        User user = getById(userId);
        Assert.notNull(user, "无效用户");
        return getUserLoginToken(user);
    }

    /**
     * 通过access_token获取用户对象
     *
     * @param token
     * @return
     */
    public User findByToken(String token) {
        JWT jwt = JWTUtil.parseToken(token);
        JSONObject payloads = jwt.getPayloads();
        String userId = payloads.getStr("id");
//        String username = payloads.getStr("username");
        Long expired = payloads.getLong("expired");
        Assert.hasText(userId, "无效token");
        Assert.isTrue(expired > new Date().getTime(), "token已失效");

        // 黑名单拦截
        boolean blocked = redisClient.getSetCache(REDIS_BLOCK_TOKEN).contains(token);
        Assert.isTrue(!blocked, "token已失效");

        return getById(userId);
    }

    /**
     * 获取当前用户的的菜单
     *
     * @param tree 是否返回树形结构
     * @return
     */
    public List<Menu> getMeMenu(Boolean tree) {
        String userId = BaseContext.getUserId();
        Assert.hasText(userId, "无效用户");
        List<Menu> list = baseMapper.getUserMenu(userId);

        if (tree) {
            return MenuService.wrapTree(list, null,null);
        }
        return list;
    }


    public Page<UserDto> query(String keyword,
                               String roleId,
                               String groupId,
                               Integer page, Integer size) {

        Page<User> res =  baseMapper.query(keyword,roleId,groupId,Page.of(page,size));
        Page<UserDto> rs = Page.of(page,size);
        rs.setRecords(wrapDto(res.getRecords()));
        rs.setTotal(res.getTotal());
        return rs;
    }

    private List<UserDto> wrapDto(List<User> records) {
        return records.stream().map(u->{
            UserDto dto = new UserDto();
            BeanUtil.copyProperties(u,dto, CopyOptions.create().setIgnoreNullValue(true));
            dto.setRoles(getUserRoles(u.getId()));
            return dto;
        }).collect(Collectors.toList());
    }

    public CaptchaDto createCaptcha() {
        String random = IdUtil.fastSimpleUUID();
        // ShearCaptcha captcha = CaptchaUtil.createShearCaptcha(200, 100, 4, 4);
        AbstractCaptcha captcha = CaptchaUtil.createGifCaptcha(240, 100, 4);
        captcha.createCode();
        String image = captcha.getImageBase64Data();
        String code = captcha.getCode();

        // 写入缓存中
        redisClient.getMapCache(REDIS_CAPTCHA).put(random, code, captchaExpired, TimeUnit.SECONDS);
        log.debug("验证码，random={},code={}", random, code);
        return new CaptchaDto(random, image);
    }

    /**
     * 修改当前用户的密码
     *
     * @param vo
     * @return
     */
    public Boolean changeMePassword(ChangePasswordVo vo) {
        User me = BaseContext.getUser();
        boolean match = me != null && passwordEncoder.matches(vo.getOldPwd(), me.getPassword());
        Assert.isTrue(match, "旧密码错误");

        me.setPassword(passwordEncoder.encode(vo.getNewPwd()));
        return saveOrUpdate(me);

    }

    public List<Role> getMeRoles() {
        String userId = BaseContext.getUserId();
        Assert.hasText(userId, "无效用户");
        return baseMapper.getUserRoles(userId);
    }
    public List<Role> getUserRoles(String id) {
        return baseMapper.getUserRoles(id);
    }

}




