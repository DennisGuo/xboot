package cn.ghx.xboot.user.group;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.ghx.xboot.mapper.UserGroupMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
* @author ghx
* @createDate 2024-02-18 11:09:39
*/
@Service
public class UserGroupService extends ServiceImpl<UserGroupMapper, UserGroup> {

    public Page<UserGroup> query(String keyword, Integer page, Integer size) {
        LambdaQueryChainWrapper<UserGroup> qw = lambdaQuery();
        if(StringUtils.hasText(keyword)){
            qw.and(w->{
               w.like(UserGroup::getName,keyword);
            });
        }
        return qw.page(Page.of(page,size));
    }
}




