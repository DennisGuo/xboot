package cn.ghx.xboot.user.group;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 应急小组
 * @TableName t_group
 */
@TableName(value ="t_group")
@Data
public class UserGroup implements Serializable {
    /**
     * id
     */
    @TableId
    private String id;

    /**
     * 名称
     */
    private String name;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 上级小组ID
     */
    private String parentId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}