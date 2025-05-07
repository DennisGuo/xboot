package cn.ghx.xboot.user;

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

/**
 * 用户表
 *
 * @TableName t_user
 */
@TableName(value = "t_user")
@Data
@JsonIgnoreProperties(value = {
    "password"
}, allowSetters = true)
public class User implements Serializable {
    /**
     * id
     */
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 创建时间
     */
    private Date createTime;

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