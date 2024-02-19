<script setup>
import {onMounted} from 'vue'
import zhCN from 'ant-design-vue/es/locale/zh_CN';
import { useGlobalStore } from '@/store/global'
import dayjs from 'dayjs';
import 'dayjs/locale/zh-cn';
dayjs.locale('zh-cn');
const locale = zhCN
const global = useGlobalStore()

onMounted(() => {
  document.title = global.site.title  
})

</script>

<template>
  <a-config-provider :locale="locale">
    <router-view />
  </a-config-provider>
</template>