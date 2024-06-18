<script setup>
import {onMounted} from 'vue'
import zhCN from 'ant-design-vue/es/locale/zh_CN';
import { useGlobalStore } from '@/store/global'
import { SYS_TITLE, SYS_TITLE_DEFAULT } from '@/common/const';
import dayjs from 'dayjs';
import 'dayjs/locale/zh-cn';
dayjs.locale('zh-cn');
const locale = zhCN
const global = useGlobalStore()

onMounted(() => {
  global.getSetting(SYS_TITLE).then(it => {
    document.title = it?.content || SYS_TITLE_DEFAULT
  }) 
})

</script>

<template>
  <a-config-provider :locale="locale">
    <router-view />
  </a-config-provider>
</template>