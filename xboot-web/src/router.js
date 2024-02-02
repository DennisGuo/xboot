import { createRouter, createWebHistory } from "vue-router";

const routes = [
      {
        path: "/",
        name: "login",
        component: () => import("./pages/login.vue")
      }
];

const router = createRouter({
  // 4. Provide the history implementation to use. We are using the hash history for simplicity here.
  history: createWebHistory(),
  routes // short for `routes: routes`
});

export default router;
