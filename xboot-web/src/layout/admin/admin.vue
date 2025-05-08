<template>
  <a-layout class="h100">
    <a-layout-header style="padding-left: 5px">
      <AdminHeader />
    </a-layout-header>
    <a-layout>
      <a-layout-sider
        v-model:collapsed="global.siderCollapsed"
        :collapsible="true"
      >
        <AdminSider v-if="global.crtModule" />
      </a-layout-sider>
      <a-layout-content style="overflow-y: auto" class="admin-main">
        <router-view></router-view>
      </a-layout-content>
    </a-layout>
  </a-layout>
</template>

<script setup>
import { useGlobalStore } from "@/store/global";
import AdminHeader from "./components/AdminHeader.vue";
import AdminSider from "./components/AdminSider.vue";

const global = useGlobalStore();
</script>

<style lang="less" scoped>
.admin-main {
  position: relative;
  --size: 18px;
  &::before {
    content: "";
    position: absolute;
    top: 0;
    left: 0;
    width: var(--size);
    height: var(--size);
    background-color: #001529;
  }
  &::after {
    content: "";
    position: absolute;
    top: 0;
    left: 0;
    width: var(--size);
    height: var(--size);
    background-color: #f5f5f5;
    border-radius: var(--size) 0 0 0;
  }
}
</style>
