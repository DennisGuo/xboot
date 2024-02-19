import { defineStore } from "pinia";
import { ref } from "vue";
import { KEY_AC_TOKEN, KEY_RF_TOKEN, KEY_EX_TIME } from "../common/const";
import * as UserApi from "../api/user";
import router from "@/router";
// 匹配views里面所有的.vue文件
const modules = import.meta.glob("@/pages/**/*.vue");

export const useGlobalStore = defineStore("global", () => {
  const config = window._CONFIG || {};
  const site = config.site || {};

  // 侧边栏是否收起
  const siderCollapsed = ref(false);
  // 当前登录用户
  const me = ref(null);
  // 当前菜单目录
  const meMenu = ref([]);

  /**
   * 保持登录token
   * @param {*} res
   */
  const saveToken = (res) => {
    const { accessToken, refreshToken, expiredIn } = res;
    window.localStorage.setItem(KEY_AC_TOKEN, accessToken);
    window.localStorage.setItem(KEY_RF_TOKEN, refreshToken);
    window.localStorage.setItem(
      KEY_EX_TIME,
      new Date().getTime() + expiredIn * 1000
    );
  };

  /**
   * 获取当前用户
   * @returns
   */
  const getMe = async () => {
    if (me.value) {
      return me.value;
    } else {
      const res = await UserApi.getMe();
      if (res.data) {
        me.value = res.data;
        return res.data;
      } else {
        return null;
      }
    }
  };

  /**
   * 登录用户的菜单
   * @returns
   */
  const getMeMenu = async () => {
    if (meMenu.value && meMenu.length > 0) {
      return meMenu.value;
    } else {
      const res = await UserApi.getMeMenu();
      if (res.data) {
        meMenu.value = res.data;
        return res.data;
      } else {
        return [];
      }
    }
  };

  /**
   * 登录成功之后，根据配置权限生成路由
   */
  const loadUserRouter = () => {
    return new Promise(async (resolve, reject) => {
      try {
        const me = await getMe();
        const menus = await getMeMenu();
        let redirect = undefined;
        if (me && me.role && me.role.homePage) {
          redirect = me.role.homePage;
        }

        let children = [];
        parseRouter(menus, children);

        const root = {
          path: "/",
          name: "admin",
          redirect,
          component: () => import("@/layout/admin/admin.vue"),
          children,
          meta: { requiresAuth: true }
        };
        router.addRoute(root);
        // router.push({ path: '/' })
        resolve({ path: "/" });
      } catch (e) {
        reject(e);
      }
    });
  };
  const parseRouter = (menus, arr) => {
    if (menus.length > 0) {
      const target = menus.filter((i) => i.type == 0 || i.type == 1); // 0: 菜单， 1: 页面， 2: 按钮
      for (let i = 0; i < target.length; i++) {
        const m = target[i];
        const item = {
          path: m.path,
          name: m.code,
          meta: { requiresAuth: true }
        };
        if (m.type == 1) {
          item.component = getComponent(m.component);
        } else if (m.type == 0) {
          item.component = { template: `<router-view/>` };
          if (m.children && m.children.length > 0) {
            const children = [];
            parseRouter(m.children, children);
            if (children.length > 0) {
              item.children = children;
              item.redirect = children[0].path;
            }
          }
        }
        arr.push(item);
      }
    }
  };
  // 获取加载的组件
  const getComponent = (name) => {
    let rs;
    for (const path in modules) {
      const pname = path.split("/src/")[1].split(".vue")[0];
      if (pname == name) {
        rs = () => modules[path]();
      }
    }
    return rs;
  };

  // 退出登录
  const logout = async ()=>{
    await UserApi.logout()
    const ls = window.localStorage
    ls.removeItem(KEY_AC_TOKEN)
    ls.removeItem(KEY_RF_TOKEN)
    ls.removeItem(KEY_EX_TIME)
    router.push({name: 'login'})
  }

  return {
    me,
    meMenu,
    site,
    siderCollapsed,
    saveToken,
    getMe,
    getMeMenu,
    loadUserRouter,
    logout,
  };
});
