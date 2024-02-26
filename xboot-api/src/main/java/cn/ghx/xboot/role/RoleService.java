package cn.ghx.xboot.role;

import cn.ghx.xboot.mapper.RoleMapper;
import cn.ghx.xboot.user.UserService;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
* @author ghx
* @createDate 2024-01-16 17:52:38
*/
@Service
public class RoleService extends ServiceImpl<RoleMapper, Role>{

    private final UserService userService;

    @Lazy
    public RoleService(UserService userService) {
        this.userService = userService;
    }

    public Page<Role> query(String keyword, Integer page, Integer size) {
        LambdaQueryChainWrapper<Role> qw = lambdaQuery();
        if(StringUtils.hasText(keyword)){
            qw.and(w->{
                w.like(Role::getName,keyword).or()
                        .like(Role::getCode,keyword);
            });
        }
        return qw.page(Page.of(page,size));
    }

    public Boolean removeRoleUser(String id, List<String> userIds) {
        return baseMapper.removeRoleUser(id,userIds);
    }

    @Transactional
    public Boolean saveRoleMenu(String id, List<String> menuIds) {
        baseMapper.removeRoleMenu(id);
        if(!menuIds.isEmpty()){
            baseMapper.saveRoleMenuBatch(id,menuIds);
        }
        return true;
    }

    public Boolean addRoleUser(String id, List<String> userIds) {
        return baseMapper.addRoleUser(id,userIds);
    }

    public List<String> getRoleMenuIds(String id) {
        return baseMapper.getRoleMenuIds(id);
    }
}




