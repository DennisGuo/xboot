<template>
  <a-spin :spinning="loading">
    <a-tree-select v-model:value="data" show-search style="width: 180px" :fieldNames="fieldNames"
      :dropdown-style="{ maxHeight: '400px', overflow: 'auto' }" placeholder="请选择" allow-clear tree-default-expand-all
      :tree-data="treeData" tree-node-filter-prop="label" @select="onSelect" :labelInValue="true">
    </a-tree-select>
  </a-spin>
</template>

<script setup>
import { ref, onMounted } from 'vue'
const props = defineProps(['value', 'field'])
const emit = defineEmits(['update:value'])
import { getGroupTree } from '@/api/group'

const fieldNames = {
  label: 'name',
  value: 'id'
}
const loading = ref(false)
const data = ref({ value: '' })
const treeData = ref([])

onMounted(() => {
  load()
})


const load = async () => {
  loading.value = true
  const res = await getGroupTree()
  loading.value = false
  treeData.value = res.data || []
  parseProps()
}

const parseProps = () => {
  data.value = { value: props.value || '' }
}

const onSelect = (value, node) => {
  if (props.field) {
    emit('update:value', node[props.field])
  } else {
    emit('update:value', node)
  }
}
</script>

<style lang="less" scoped></style>