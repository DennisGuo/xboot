package cn.ghx.xboot.dict;

import cn.ghx.xboot.dict.value.DictValue;
import cn.ghx.xboot.dict.value.DictValueService;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.ghx.xboot.mapper.DictMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ghx
 * @createDate 2024-01-16 17:48:54
 */
@Service
public class DictService extends ServiceImpl<DictMapper, Dict> {

    private final DictValueService valueService;

    public DictService(DictValueService valueService) {
        this.valueService = valueService;
    }

    public Page<Dict> query(String keyword, Integer page, Integer size) {
        LambdaQueryChainWrapper<Dict> qw = lambdaQuery();
        if (StringUtils.hasText(keyword)) {
            qw.and(w -> {
                w.like(Dict::getName, keyword).or().like(Dict::getCode, keyword);
            });
        }
        Page<Dict> rs = qw.page(Page.of(page, size));
        rs.getRecords().forEach(i -> {
            i.setValues(valueService.getByDictId(i.getId()));
        });
        return rs;
    }

    @Transactional
    public Boolean saveItem(Dict item) {
        List<DictValue> oldValues = new ArrayList<>();
        if (StringUtils.hasText(item.getId())) {
            oldValues = valueService.getByDictId(item.getId());
        }
        boolean rs = saveOrUpdate(item);
        List<DictValue> values = item.getValues();
        List<String> toSaveIds = values.stream().map(DictValue::getId).filter(StringUtils::hasText).toList();
        List<String> toRemove = oldValues.stream().map(DictValue::getId).filter(i->!toSaveIds.contains(i)).toList();

        if (!values.isEmpty()) {
            for (DictValue v : values) {
                v.setDictId(item.getId());
                v.setDictCode(item.getCode());
            }
            valueService.saveOrUpdateBatch(values);
        }
        if (!toRemove.isEmpty()) {
            valueService.removeBatchByIds(toRemove);
        }
        return rs;
    }

    public Dict getByCode(String code) {
        Dict item = lambdaQuery().eq(Dict::getCode, code).one();
        Assert.notNull(item,"字典不存在");
        item.setValues(valueService.getByDictId(item.getId()));
        return item;
    }
}




