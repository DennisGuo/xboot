package cn.ghx.xboot.menu;

import cn.ghx.xboot.TestBase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;

class MenuServiceTest extends TestBase {

    @Autowired
    private MenuService service;
    @Test
    void getMenuTree() {
        print(service.getMenuTree(null));
    }
}