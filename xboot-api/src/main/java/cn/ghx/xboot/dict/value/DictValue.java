package cn.ghx.xboot.dict.value;

import cn.ghx.xboot.common.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 字典项表
 * @TableName t_dict_value
 */
@EqualsAndHashCode(callSuper = true)
@TableName(value ="t_dict_value")
@Data
public class DictValue extends BaseEntity {


    /**
     * 关联字典ID
     */
    private String dictId;

    /**
     * 关联字典编码
     */
    private String dictCode;

    /**
     * 字典项值编码
     */
    private String code;

    /**
     * 字典项值
     */
    private String value;

    /**
     * 排序 asc
     */
    private Integer sort;
}