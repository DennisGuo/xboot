package cn.ghx.xboot.menu;

import cn.ghx.xboot.user.User;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.ghx.xboot.mapper.MenuMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * @author ghx
 * @createDate 2024-01-16 17:51:52
 */
@Service
public class MenuService extends ServiceImpl<MenuMapper, Menu> {

    public List<Menu> getMenuTree(Integer type) {
        return wrapTree(list(), null,type);
    }

    public static List<Menu> wrapTree(List<Menu> list, String pid,Integer type) {
        List<Menu> level = list.stream().filter(i -> {
            boolean rs = Objects.equals(i.getPid(), pid);

            // 还需要基于type判断
            if(type != null){
                rs = rs && Objects.equals(i.getType(),type);
            }

            return rs;
        }).sorted(Comparator.comparingInt(Menu::getSort)).toList();
        level.forEach(i -> {
            List<Menu> children = wrapTree(list, i.getId(),type);
            i.setChildren(children);
        });
        return level;
    }

    public Boolean saveItem(Menu item) {
        return saveOrUpdate(item);
    }

    public Page<Menu> query(String keyword, Integer page, Integer size) {
        return baseMapper.query(keyword,Page.of(page,size));
    }
}




