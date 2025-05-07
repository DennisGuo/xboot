package cn.ghx.xboot.config.mp;

import cn.ghx.xboot.common.BaseContext;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;

import java.time.Instant;
import java.time.LocalDateTime;

/**
 * @date: 2025年02月20日 18:02
 */
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        if (metaObject.hasGetter("createTime")) {
            this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now());
        }
        if (BaseContext.getUserId() != null && metaObject.hasGetter("createBy")) {
            this.strictInsertFill(metaObject, "createBy", String.class, BaseContext.getUserId());
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        if (metaObject.hasGetter("updateTime")) {
            this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now());
        }
        if (BaseContext.getUserId() != null && metaObject.hasGetter("updateBy")) {
            this.setFieldValByName("updateBy", BaseContext.getUserId(), metaObject);
        }
    }
}
