package cn.ghx.xboot.group;

import cn.ghx.xboot.common.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 用户小组
 * @TableName t_group
 */
@EqualsAndHashCode(callSuper = true)
@TableName(value ="t_group")
@Data
public class Group extends BaseEntity {


    /**
     * 名称
     */
    private String name;
    /**
     * 编码
     */
    private String code;

    /**
     * 备注
     */
    private String remark;


    /**
     * 上级小组ID
     */
    private String pid;

    /**
     * 排序
     */
    private Integer sort;

    @TableField(exist = false)
    private List<Group> children;
}