<template>
  <a-spin :spinning="loading">
    <a-cascader v-model:value="data" :fieldNames="fieldNames" :options="options" placeholder="请选择" change-on-select @change="onChange"/>
  </a-spin>
</template>

<script setup>
import { ref, onMounted,watch } from 'vue'
import { getMenuTree } from '@/api/menu'
import { findTreePath } from '@/mixin'

const props = defineProps(['value'])
const emit = defineEmits(['update:value'])

const fieldNames = {
  label: 'name',
  value: 'id',
}
const data = ref([])
const options = ref([])
const loading = ref(false)

onMounted(() => {
  init()
  
})

watch(()=>props.value,()=>{
  parseValue()
})

const parseValue = ()=>{
  if(props.value){
    const arr = findTreePath(options.value,(i)=>i.id == props.value)
    data.value = arr.map((i)=>i.id)
  }else{
    data.value = []
  }
}


const init = async () => {
  loading.value = true
  const res = await getMenuTree()  
  loading.value = false
  options.value = res.data || [];  

  parseValue()
}

const onChange = e=>{
  let rs = null
  if(e.length > 0){
    rs = e[e.length -1]
  }
  emit('update:value',rs)
}


</script>

<style lang="less" scoped></style>