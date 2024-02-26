package cn.ghx.xboot.attach;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 附件
 * @TableName t_attachment
 */
@TableName(value ="t_attachment")
@Data
public class Attachment implements Serializable {
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
     * 相对路径
     */
    private String path;

    /**
     * MIME类型
     */
    private String mime;

    /**
     * 文件大小
     */
    private Long fileSize;

    /**
     * 文件名称
     */
    private String name;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}