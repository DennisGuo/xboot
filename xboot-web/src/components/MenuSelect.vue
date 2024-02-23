<template>
  <a-spin :spinning="loading">
    <a-cascader v-model:value="data" :fieldNames="fieldNames" :options="options" placeholder="请选择" change-on-select @change="onChange"/>
  </a-spin>
</template>

<script setup>
import { ref, onMounted,watch } from 'vue'
import { getMenuTree } from '@/api/menu'

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
  parseValue()
})

watch(()=>props.value,()=>{
  parseValue()
})

const parseValue = ()=>{
  if(props.value){
    data.value = [props.value]
  }else{
    data.value = []
  }
}


const init = async () => {
  loading.value = true
  const res = await getMenuTree()  
  loading.value = false
  options.value = res.data || [];  
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