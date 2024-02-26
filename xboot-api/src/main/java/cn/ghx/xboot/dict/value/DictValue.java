package cn.ghx.xboot.dict.value;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 字典项表
 * @TableName t_dict_value
 */
@TableName(value ="t_dict_value")
@Data
public class DictValue implements Serializable {
    /**
     * id
     */
    @TableId
    private String id;

    /**
     * 创建时间
     */
    private Date createTime;

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