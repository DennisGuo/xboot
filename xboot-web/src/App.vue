<script setup>
import { onMounted } from "vue";
import zhCN from "ant-design-vue/es/locale/zh_CN";
import { useGlobalStore } from "@/store/global";
import { SYS_TITLE, COPYRIGHT } from "@/common/const";
import dayjs from "dayjs";
import "dayjs/locale/zh-cn";
dayjs.locale("zh-cn");
const locale = zhCN;
const global = useGlobalStore();

onMounted(() => {
  load();
});
const load = async () => {
  const keys = [SYS_TITLE, COPYRIGHT];
  const arr = await global.getSetting(keys);
  if (arr.length == keys.length) {
    global.title = arr.filter((i) => i.code == SYS_TITLE)[0].content;
    global.copyright = arr.filter((i) => i.code == COPYRIGHT)[0].content;
  }

  document.title = global.title;
};
</script>

<template>
  <a-config-provider :locale="locale">
    <router-view />
  </a-config-provider>
</template>
