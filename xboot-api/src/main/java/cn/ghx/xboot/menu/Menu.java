package cn.ghx.xboot.menu;

import cn.ghx.xboot.common.BaseEntity;
import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 菜单
 * @TableName t_menu
 */
@EqualsAndHashCode(callSuper = true)
@TableName(value ="t_menu")
@Data
public class Menu extends BaseEntity {


    /**
     * 名称
     */
    private String name;

    /**
     * 类型：0=模块,1=菜单,2=页面,3=按钮
     */
    private Integer type;

    /**
     * 路径
     */
    private String path;

    /**
     * 编码
     */
    private String code;

    /**
     * 打开页面方式: self,blank
     */
    private String target;

    /**
     * 前端组件路径
     */
    private String component;

    /**
     * 上级ID
     */
    @TableField(updateStrategy = FieldStrategy.ALWAYS)
    private String pid;

    /**
     * 排序值，ASC
     */
    private Integer sort=0;

    /**
     * 图标的名称
     */
    private String icon;
    /**
     * 是否隐藏
     */
    private Boolean hidden;

    /**
     * 子菜单
     */
    @TableField(exist = false)
    private List<Menu> children;

    /**
     * 上级名称
     */
    @TableField(exist = false)
    private String pName;
}