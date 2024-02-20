<template>
  <a-select :options="options" v-model:value="model" @change="onChange"></a-select>
</template>

<script setup>
import { ref ,onMounted,watch } from 'vue'
import { listRole } from '@/api/role'

const props = defineProps(['value'])
const emit = defineEmits(['update:value'])
const options = ref([])
const rows = ref([])
const model = ref(props.value)

onMounted(() => {
  load()
})
watch(()=>props.value,(v)=>{
  model.value = v
})

const load = async ()=>{
  const res = await listRole({size: 100})
  if(res.data){
    const arr = res.data.records
    rows.value = arr;
    options.value = arr.map((item)=>{
      return {
        value: item.id,
        label: item.name
      }
    })
  }
}

const onChange = e=>{
  console.log('select role',e)
  emit('update:value',e)
}
</script>

<style lang="less" scoped>

</style>