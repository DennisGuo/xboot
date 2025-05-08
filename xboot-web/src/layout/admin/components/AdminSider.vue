<template>
  <div class="">    
    <a-menu mode="inline" theme="dark" :items="items" @click="onClickMenu" v-model:selectedKeys="current"
      v-model:openKeys="openKeys" />
  </div>
</template>

<script setup>
import { ref, onMounted, h, compile, watch, } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useGlobalStore } from '@/store/global';
import { findItemInArr, findItemInTree } from '@/mixin'
import { SYS_TITLE, SYS_TITLE_DEFAULT } from '@/common/const';

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
  init()
})

const init = async () => {
  // console.log('crtModule', global.crtModule);
  const all = await global.getMeMenu()
  const item = findItemInArr(all, i => i.code == global.crtModule)
  const arr = item.children || []// all.filter(i=>i.pid == item.id)
  menusData.value = arr
  const menus = []
  parseMenu(arr, menus)
  // console.log(arr, menus)
  items.value = menus

  // 
  parseRoute()
}

const parseRoute = () => {
  current.value = [route.name]
  // 判断是否需要展开上级
  const it = findItemInTree(menusData.value, i => i.code == route.name)
  if (it && it.pid) {
    const parent = findItemInTree(menusData.value, i => i.id == it.pid)
    if (parent) {
      openKeys.value = [parent.code]
    }
  }
}

const parseMenu = (arr, menus) => {
  arr
  .filter(i => i.type == 1 || i.type == 2).forEach(i => {
    if (!i.hidden) {
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
    }
  })
}

const onClickMenu = ({ key }) => {
  router.push({ name: key })
}

</script>

<style lang="less" scoped>
@import url('@/assets/common.less');
</style>