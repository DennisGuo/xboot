<template>
  <div class="sider">
    <div class="logo">
      <img :src="`/logo.png`" />
      <div class="title flex1" v-if="!global.siderCollapsed">{{ global.site.title }}</div>
    </div>
    <a-menu mode="inline" theme="dark" :items="items" @click="onClickMenu" 
    v-model:selectedKeys="current" 
    v-model:openKeys="openKeys" 
    />
  </div>
</template>

<script setup>
import { ref, onMounted, h, compile, watch, } from 'vue'
import { useRoute,useRouter } from 'vue-router'
import { useGlobalStore } from '@/store/global';
import { findItemInTree } from '@/mixin'

const global = useGlobalStore()
const items = ref([])
const menusData = ref([])
const current = ref([])
const openKeys = ref([])

const route = useRoute()
const router = useRouter()

onMounted(() => {
  init()
})

watch(() => route.name, () => {
  parseRoute()
})

const init = async () => {
  const arr = await global.getMeMenu()
  menusData.value = arr
  const menus = []
  parseMenu(arr, menus)
  console.log(menus)
  items.value = menus

  // 
  parseRoute()
}

const parseRoute = () => {
  current.value = [route.name]
  // 判断是否需要展开上级
  const it = findItemInTree(menusData.value, i=>i.code == route.name)
  if(it && it.pid ){
    const parent = findItemInTree(menusData.value, i=>i.id == it.pid)
    if(parent){
      openKeys.value = [parent.code]
    }
  }
}

const parseMenu = (arr, menus) => {
  arr.filter(i => i.type == 0 || i.type == 1).forEach(i => {
    const item = {
      label: i.name,
      key: i.code,
    }
    if (i.icon) {
      item.icon = () => h(compile(`<${i.icon}/>`))
    }
    if (i.children && i.children.length > 0) {
      const child = []
      parseMenu(i.children, child)
      if (child.length > 0) {
        item.children = child
      }
    }
    menus.push(item)
  })
}

const onClickMenu = ({key}) => {
  router.push({name: key})
}

</script>

<style lang="less" scoped>
@import url('@/assets/common.less');

.sider {
  .logo {
    .flex-row();
    height: 64px;
    line-height: 64px;
    padding: 1em;
    color: #fff;
    align-items: center;
    justify-content: center;
    gap: 12px;

    img {
      height: 36px;
    }
  }
}
</style>