package cn.ghx.xboot.user;

import cn.ghx.xboot.common.BaseEntity;
import cn.ghx.xboot.role.Role;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户表
 *
 * @TableName t_user
 */
@EqualsAndHashCode(callSuper = true)
@TableName(value = "t_user")
@Data
@JsonIgnoreProperties(value = {
    "password"
}, allowSetters = true)
public class User extends BaseEntity {


    /**
     * 姓名
     */
    private String name;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 用户名
     */
    private String username;

    /**
     * 备注
     */
    private String remark;

    /**
     * 性别: 0=男,1=女
     */
    private Integer sex;

    /**
     * 密码
     */
    private String password;


}