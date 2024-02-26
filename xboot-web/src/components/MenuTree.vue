<template>
  <a-spin :spinning="loading">
  <a-tree v-model:expandedKeys="expandedKeys" v-model:selectedKeys="selectedKeys" v-model:checkedKeys="checkedKeys"
    checkable :treeData="treeData" :fieldNames="fieldNames" :showLine="true" :checkStrictly="true" @check="onCheck">
    <template #title="{ name, code }">
      <a-tag>{{ code }} </a-tag>
      <span>{{ name }} </span>
    </template>
  </a-tree>
</a-spin>
</template>

<script setup>
import { ref, onMounted,watch } from 'vue'
import { getMenuTree } from '@/api/menu'

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

watch(()=>props.checked,()=>{
  parseProps()
})

const parseProps = ()=>{   
  checkedKeys.value = props.checked || []
}

const load = async () => {
  loading.value = true
  const res = await getMenuTree();
  loading.value = false
  const arr = res.data || []
  treeData.value = arr

  expandedKeys.value = arr.filter(i=>i.children && i.children.length > 0).map(i=>i.id)

}

const onCheck = ({checked})=>{
  emit('checked', checked)
}


defineExpose({
  refresh: load,
})

</script>

<style lang="less" scoped></style>