package cn.ghx.xboot.setting;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 配置内容
 * @TableName t_setting
 */
@TableName(value ="t_setting")
@Data
public class Setting implements Serializable {
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

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}