package cn.ghx.xboot.user.group;

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
 * 应急小组
 * @TableName t_group
 */
@EqualsAndHashCode(callSuper = true)
@TableName(value ="t_group")
@Data
public class UserGroup extends BaseEntity {


    /**
     * 名称
     */
    private String name;

    /**
     * 备注
     */
    private String remark;


    /**
     * 上级小组ID
     */
    private String parentId;
}