package cn.ghx.xboot.setting;

import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.ghx.xboot.mapper.SettingMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author ghx
 * @createDate 2024-01-16 17:53:33
 */
@Service
public class SettingService extends ServiceImpl<SettingMapper, Setting> {

    public Page<Setting> query(String keyword, Integer page, Integer size) {
        LambdaQueryChainWrapper<Setting> qw = lambdaQuery();
        // 参数
        if (StringUtils.hasText(keyword)) {
            qw.and(w -> {
                w.like(Setting::getName, keyword).or()
                        .like(Setting::getCode, keyword).or()
                        .like(Setting::getContent, keyword);
            });
        }
        return qw.page(Page.of(page, size));
    }
}




