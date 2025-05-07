package cn.ghx.xboot.setting;

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
 * 配置内容
 * @TableName t_setting
 */
@EqualsAndHashCode(callSuper = true)
@TableName(value ="t_setting")
@Data
public class Setting extends BaseEntity {


    /**
     * 编码
     */
    private String code;

    /**
     * 名称
     */
    private String name;

    /**
     * 配置内容
     */
    private String content;

    /**
     * 系统必须
     */
    private Boolean isSys;
}