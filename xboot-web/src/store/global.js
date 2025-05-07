import { defineStore } from "pinia";
import { ref } from "vue";
import { KEY_AC_TOKEN, KEY_RF_TOKEN, KEY_EX_TIME } from "@/common/const";
import * as UserApi from "@/api/user";
import { listSetting } from "@/api/setting"
import router from "@/router";
import { loopTree, saveToken } from '@/mixin'
import PQueue from 'p-queue';

// 匹配views里面所有的.vue文件
const modules = import.meta.glob("@/pages/**/*.vue");

export const useGlobalStore = defineStore("global", () => {

  // 侧边栏是否收起
  const siderCollapsed = ref(false);
  // 当前登录用户
  const me = ref(null);
  // 当前菜单目录
  const meMenu = ref([]);
  // 当前用户角色
  const meRoles = ref([]);
  // 所有配置
  const settings = ref([]);
  // 当前模块
  const crtModule = ref(null);

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
  const meMenuQueue = new PQueue({ concurrency: 1 });
  const getMeMenu = async (refresh = false) => {
    return await meMenuQueue.add(async () => {
      if (meMenu.value.length == 0 || refresh) {
        const res = await UserApi.getMeMenu();
        meMenu.value = res?.data || [];
      }
      return meMenu.value
    })
  };

  /**
   * 获取当前用户的角色
   * @param {*} refresh 是否刷新
   * @returns 
   */
  const getMeRoles = async (refresh = false) => {
    if (meRoles.value.length == 0 || refresh) {
      const res = await UserApi.getMeRoles();
      meRoles.value = res?.data || [];
    }
    return meRoles.value;
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
        const roles = await getMeRoles();
        if (me && roles.length > 0) {
          const arr = roles.filter(i => i.homePage)
          if (arr.length > 0) {
            redirect = arr[0].homePage
          } else if (menus.length > 0) {
            const men = menus[0].path
            if (men) {
              redirect = men
            }
          }
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
      const target = menus.filter((i) => i.type == 0 || i.type == 1 || i.type == 2); // 0: 模块，1: 菜单 2: 页面，3: 按钮
      for (let i = 0; i < target.length; i++) {
        const m = target[i];
        const item = {
          path: m.path,
          name: m.code,
          meta: { requiresAuth: true }
        };
        if (m.type == 2 || m.component) {
          const cpath = m.component || `pages/${m.path}`
          item.component = getComponent(cpath);
        } else if (m.type == 0 || m.type == 1) {
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
  const logout = async () => {
    await UserApi.logout()
    const ls = window.localStorage
    ls.removeItem(KEY_AC_TOKEN)
    ls.removeItem(KEY_RF_TOKEN)
    ls.removeItem(KEY_EX_TIME)
    me.value = null
    router.push({ name: 'login' })
  }

  // 获取所有配置

  const getSettings = () => {
    return new Promise(resolve => {
      if (settings.value.length > 0) {
        resolve(settings.value)
      } else {
        listSetting({ size: 1000 }).then(res => {
          settings.value = res.data.records || [];
          resolve(settings.value)
        })
      }
    })
  }
  // 根据code获取配置
  const getSetting = codes => {
    return new Promise(resolve => {
      getSettings().then(values => {
        const arr = values.filter(item => {
          if (typeof codes == 'string') {
            return item.code == codes
          } else {
            return codes.indexOf(item.code) >= 0
          }
        })
        if (typeof codes == 'string') {
          const rs = arr.length > 0 ? arr[0] : null
          resolve(rs)
        } else {
          resolve(arr)
        }
      })
    })
  }

  /**
   * 判断是否具有这个key的权限
   * @param {*} key 
   * @returns 
   */
  const hasAuth = async (key) => {

    const codes = []

    const menus = await getMeMenu()
    loopTree(menus, m => codes.push(m.code))

    return codes.includes(key)
  }


  return {
    me,
    meMenu,
    siderCollapsed,
    crtModule,
    saveToken,
    getMe,
    getMeMenu,
    loadUserRouter,
    logout,
    getSetting,
    getSettings,
    hasAuth,
  };
});
