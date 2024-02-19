import { createRouter, createWebHistory } from "vue-router";
import { useGlobalStore } from "@/store/global";

const routes = [
  {
    path: "/login",
    name: "login",
    component: () => import("./pages/login.vue")
  },
  {
    path: "/error",
    name: "error",
    component: () => import("./pages/error.vue")
  }
];

const router = createRouter({
  // 4. Provide the history implementation to use. We are using the hash history for simplicity here.
  history: createWebHistory(),
  routes // short for `routes: routes`
});

router.beforeEach(async (to, from, next) => {
  const auth = to.meta && to.meta.requiresAuth;
  if (to.name == "login" || !auth) {
    next();
  } else {
    const global = useGlobalStore();
    if (global.me) {
      next();
    } else {
      const me = await global.getMe();
      if (me) {
        next();
      } else {
        next({ name: "login" });
      }
    }
  }
});

export default router;
