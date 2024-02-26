package cn.ghx.xboot.group;

import cn.ghx.xboot.mapper.GroupMapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
* @author ghx
* @createDate 2024-02-23 15:14:54
*/
@Service
public class GroupService extends ServiceImpl<GroupMapper, Group>{


    public Page<Group> query(String keyword, Integer page, Integer size) {
        LambdaQueryChainWrapper<Group> qw = lambdaQuery();
        // 参数
        if (StringUtils.hasText(keyword)) {
            qw.and(w -> {
                w.like(Group::getName, keyword).or()
                        .like(Group::getRemark, keyword);
            });
        }
        return qw.page(Page.of(page, size));
    }

    public List<Group> getTree() {
        return wrapTree(list(),null);
    }

    public static List<Group> wrapTree(List<Group> list, String pid) {
        List<Group> level = list.stream().filter(i -> Objects.equals(i.getPid(), pid)).sorted(Comparator.comparingInt(Group::getSort)).toList();
        level.forEach(i -> {
            List<Group> children = wrapTree(list, i.getId());
            i.setChildren(children);
        });
        return level;
    }

    /**
     * 添加用户
     * @param id
     * @param userIds
     * @return
     */
    public Boolean addUser(String id, List<String> userIds) {

        return baseMapper.addUser(id,userIds);
    }

    /**
     * 移除
     * @param id
     * @param userIds
     * @return
     */
    public Boolean removeUser(String id, List<String> userIds) {
        return baseMapper.removeUser(id,userIds);
    }
}




