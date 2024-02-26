package cn.ghx.xboot.common;

import cn.hutool.core.util.IdUtil;
import org.junit.jupiter.api.Test;

public class CommonTest {

    @Test
    public void uuid(){
        System.out.println(IdUtil.fastSimpleUUID());
    }
}
