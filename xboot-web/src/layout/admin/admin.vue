<template>
  <a-layout class="h100">
    <a-layout-sider :collapsed="global.siderCollapsed">
      <AdminSider />
    </a-layout-sider>
    <a-layout class="h100">
      <a-layout-header style=" padding-left: 5px;">
        <AdminHeader />
      </a-layout-header>
      <a-layout-content style="overflow-y: auto;">
        <router-view></router-view>
      </a-layout-content>
    </a-layout>
  </a-layout>
</template>

<script setup>
import { useGlobalStore } from '@/store/global'
import AdminHeader from './components/AdminHeader.vue'
import AdminSider from './components/AdminSider.vue'

const global = useGlobalStore()

</script>

<style lang="less" scoped>

</style>