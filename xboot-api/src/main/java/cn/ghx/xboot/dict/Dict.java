package cn.ghx.xboot.dict;

import cn.ghx.xboot.common.BaseEntity;
import cn.ghx.xboot.dict.value.DictValue;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 字典表
 * @TableName t_dict
 */
@EqualsAndHashCode(callSuper = true)
@TableName(value ="t_dict")
@Data
public class Dict extends BaseEntity {


    /**
     * 编码
     */
    private String code;

    /**
     * 字典名称
     */
    private String name;

    /**
     * 字典值
     */
    @TableField(exist = false)
    private List<DictValue> values;
}