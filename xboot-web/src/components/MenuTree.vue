<template>
  <a-spin :spinning="loading">
    <a-tree v-model:expandedKeys="expandedKeys" v-model:selectedKeys="selectedKeys" v-model:checkedKeys="checkedKeys"
      checkable :treeData="treeData" :fieldNames="fieldNames" :showLine="true" :checkStrictly="true" @check="onCheck">
      <template #title="{ name, code ,type}">
        <a-space>
          <a-tag>{{ code }} </a-tag>
          <a-tag :color="menuTypeColor[type]">{{ menuTypeDict[type] }}</a-tag>
          <span>{{ name }} </span>
        </a-space>
      </template>
    </a-tree>
  </a-spin>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue'
import { getMenuTree, menuTypeColor, menuTypeDict } from '@/api/menu'
import { loopTree } from '@/mixin';

const props = defineProps(['checked'])
const emit = defineEmits(['checked'])

const fieldNames = {
  title: 'name',
  key: 'id'
}
const loading = ref(false)
const treeData = ref([])
const expandedKeys = ref([])
const selectedKeys = ref([])
const checkedKeys = ref([])

onMounted(() => {
  load()
  parseProps()
})

watch(() => props.checked, () => {
  parseProps()
})

const parseProps = () => {
  checkedKeys.value = props.checked || []
}

const load = async () => {
  loading.value = true
  const res = await getMenuTree();
  loading.value = false
  const arr = res.data || []
  treeData.value = arr

  expandedKeys.value = arr.filter(i => i.children && i.children.length > 0).map(i => i.id)

}

const onCheck = ({ checked }) => {
  emit('checked', checked)
}

const selectAll = (select = true) => {
  const allIds = [];
  loopTree(treeData.value, i => allIds.push(i.id))
  checkedKeys.value = select ? allIds : []

  emit('checked', checkedKeys.value)
}
const expandAll = (select = true) => {
  const allIds = []; 
  loopTree(treeData.value, i => {
    if(i.children && i.children.length > 0){
      allIds.push(i.id)
    }
  })
  expandedKeys.value = select ? allIds : []
}


defineExpose({
  refresh: load,
  selectAll,
  expandAll,
})

</script>

<style lang="less" scoped></style>