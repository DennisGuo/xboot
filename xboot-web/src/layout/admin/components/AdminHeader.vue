<template>
  <div class="header flex flex-row">
    <div class="logo flex flex-row items-center gap-4 min-w-[200px] pl-[24px]">
      <img :src="logo" v-if="logo"/>
      <div class="text-white" v-if="title">{{ title }}</div>
    </div>
    <!-- <div class="sider-collapse">
      <span class="text-white pointer" @click="toggleLeft">
        <MenuUnfoldOutlined v-if="global.siderCollapsed" />
        <MenuFoldOutlined v-else />
      </span>
    </div> -->
    <div class="content flex1 ml-[24px]">
      <a-menu
        theme="dark"
        :selectedKeys="[global.crtModule]"
        mode="horizontal"
        :items="modules"
        @select="onTopMenuChange"
      ></a-menu>
    </div>
    <div class="actions">
      <a-dropdown>
        <span class="text-white pointer" v-if="global.me">
          <UserOutlined /> {{ global.me.name || global.me.username }}  <CaretDownOutlined />
        </span>
        <template #overlay>
          <a-menu @click="onClick" :items="items"></a-menu>
        </template>
      </a-dropdown>
    </div>
    <ProfileModal v-if="modal == 'profile'" @close="onCancel" />
    <PasswordModal v-if="modal == 'password'" @close="onCancel" />
  </div>
</template>

<script setup>
import { onMounted, ref, h, resolveComponent, watch } from "vue";
import { useGlobalStore } from "@/store/global";
import { confirm } from "@/mixin";
import ProfileModal from "./ProfileModal.vue";
import PasswordModal from "./PasswordModal.vue";
import { useRoute, useRouter } from "vue-router";
import * as Icons from "@ant-design/icons-vue";
import { SYS_TITLE, SYS_TITLE_DEFAULT } from "@/common/const";

const route = useRoute();
const router = useRouter();
const global = useGlobalStore();
const modal = ref(null);
const modules = ref([]);
const title = ref('')
const logo = ref()
const items = [
  { label: "个人信息", key: "profile" },
  { label: "修改密码", key: "password" },
  { type: "divider" },
  { label: "退出登录", key: "logout" }
];

onMounted(() => {
  global.getSetting(SYS_TITLE).then(it => {
    title.value = it?.content || SYS_TITLE_DEFAULT
  })
  global.getSetting('LOGO').then(it => {
    logo.value = it?.content || '/logo.png'
  })
  load();
  parseModule();
});

watch(
  () => route.matched,
  () => {
    parseModule();
  }
);

const parseModule = () => {
  const matched = route.matched;
  if (matched.length > 1) {
    global.crtModule = matched[1].name;
  }
};

const load = async () => {
  const menus = await global.getMeMenu();
  modules.value = menus
    .filter((i) => i.type == 0)
    .map((i) => {
      const it = {
        label: i.name,
        key: i.code
      };
      if (i.icon) {
        it.icon = h(Icons[i.icon]);
      }
      return it;
    });
};

const onTopMenuChange = ({ key }) => {
  router.push({ name: key });
  global.crtModule = key;
};

const toggleLeft = () => {
  global.siderCollapsed = !global.siderCollapsed;
};
const onCancel = () => {
  modal.value = null;
};
const onClick = ({ key }) => {
  if (key == "logout") {
    confirm("您确定要退出登录吗？", () => {
      global.logout();
    });
  } else {
    modal.value = key;
  }
};
</script>

<style lang="less" scoped>
.header {
  gap: 16px;
  .logo {
    height: 64px;
    line-height: 64px;
    img {
      height: 36px;
    }
  }
  .sider-collapse {
    font-size: 1.6em;
  }
  .actions {
    color: #fff;
  }
}
</style>
