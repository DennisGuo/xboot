<template>
  <div class="header flex-row">
    <div class="collapse">
      <span class="text-white pointer" @click="toggleLeft">
        <MenuUnfoldOutlined v-if="global.siderCollapsed" />
        <MenuFoldOutlined v-else />
      </span>
    </div>
    <div class="content flex1"></div>
    <div class="actions">
      <a-dropdown>
        <span class="text-white pointer">
          <UserOutlined /> {{ global.me.name || global.me.username }}
        </span>
        <template #overlay>
          <a-menu @click="onClick" :items="items"></a-menu>
        </template>
      </a-dropdown>
    </div>
    <ProfileModal v-if="modal == 'profile'" @close="onCancel"/>
    <PasswordModal v-if="modal == 'password'" @close="onCancel"/>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useGlobalStore } from '@/store/global';
import { confirm } from '@/mixin'
import ProfileModal from './ProfileModal.vue'
import PasswordModal from './PasswordModal.vue'

const global = useGlobalStore()
const modal = ref(null)
const items = [
  { label: '个人信息', key: 'profile' },
  { label: '修改密码', key: 'password' },
  { type: 'divider' },
  { label: '退出登录', key: 'logout' },
]
const toggleLeft = () => {
  global.siderCollapsed = !global.siderCollapsed
}
const onCancel =()=>{
  modal.value = null 
}
const onClick = ({key}) => {
  if(key == 'logout'){
    confirm('您确定要退出登录吗？',()=>{
      global.logout()
    })
  }else{
    modal.value = key
  }
}
</script>

<style lang="less" scoped>
.header {
  gap: 16px;
  .collapse{
    font-size: 1.6em;
  }
  .actions {
    color: #fff;
  }
}
</style>