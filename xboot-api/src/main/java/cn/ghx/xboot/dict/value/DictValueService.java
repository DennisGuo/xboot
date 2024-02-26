package cn.ghx.xboot.dict.value;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.ghx.xboot.mapper.DictValueMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author ghx
* @createDate 2024-01-16 17:50:30
*/
@Service
public class DictValueService extends ServiceImpl<DictValueMapper, DictValue>{

    public List<DictValue> getByDictId(String dictId) {
        return lambdaQuery().eq(DictValue::getDictId,dictId).orderByAsc(DictValue::getSort).list();
    }
}




