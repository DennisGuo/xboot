<template>
  <a-drawer :open="true" :title="`${item ? '编辑' : '添加'}字典`" @close="onClose">
    <a-form ref="formRef" :model="formState" layout="vertical" :rules="rules">
      
      <a-form-item label="名称" name="name">
        <a-input v-model:value="formState.name" placeholder="请输入" />
      </a-form-item>
      <a-form-item label="编码" name="code">
        <a-input v-model:value="formState.code" placeholder="请输入" />
      </a-form-item>

      <a-form-item label="字典值" :autoLink="false">

        <a-space direction="vertical" >
          <a-space v-for="(item, index) in values" :key="index">
            <a-input :value="item.code" @change="e=>onValueChange(e,index,'code')" placeholder="请输入值编码"/>
            <a-input :value="item.value" @change="e=>onValueChange(e,index,'value')"  placeholder="请输入值内容"/>
            <a-space >
              <ArrowUpOutlined  @click="toMoveUp(index)" />
              <ArrowDownOutlined  @click="toMoveDown(index)" />
              <CloseOutlined @click="toRemoveValue(index)" />
            </a-space>
          </a-space>
          <a-button @click="toAddValue" >添加</a-button>
        </a-space>
      </a-form-item>      
      <a-form-item>
        <a-space>
          <a-button type="primary" @click="toSave" :loading="saving">保存</a-button>
        </a-space>
      </a-form-item>
    </a-form>
  </a-drawer>
</template>

<script setup>
import { reactive, onMounted, ref } from 'vue'
import { saveDict } from '@/api/dict';
import { message } from 'ant-design-vue'
import { confirm } from '@/mixin'

const props = defineProps(['item'])
const emit = defineEmits(['close', 'saved'])
const values = ref([])

const rules = { 
  name: [
    { required: true, message: '请输入字典名称' }
  ],
  code: [
    { required: true, message: '请输入字典编码' }
  ],
}

const formRef = ref(null)
const formState = reactive({})
const saving = ref(false)

onMounted(() => {
  parseItem()
})

const parseItem = () => {
  const it = props.item
  if (it) {  
    formState.name = it.name
    formState.code = it.code
    values.value = [...it.values]
  } else {
    formState.name = null
    formState.code = null
    values.value = [{code:'',value:''}]
  }
}
const toAddValue =()=>{
  const arr = values.value;
  arr.push({code:'',value:''})
  values.value = arr
}
const toRemoveValue = (index)=>{
  const arr = values.value;
  arr.splice(index,1)
  values.value = arr;
}
const onValueChange = (e,index,field)=>{
  const val = e.target.value
  const arr = values.value
  const item = arr[index]
  item[field] = val;
  arr[index] = item;

  values.value = [...arr]
}

const toMoveUp=index=>{
  if(index > 0){
    const arr = values.value;
    const pre = arr[index-1]
    const it = arr[index]
    arr[index] = pre
    arr[index-1] = it
    values.value = [...arr];
  }
}
const toMoveDown=index=>{
  const arr = values.value
  if(index < arr.length-1){
    const nxt = arr[index+1]
    const it = arr[index]
    arr[index] = nxt
    arr[index+1] = it
    values.value = [...arr];
  }
}

const onClose = () => {
  emit('close')
}

const toSave = () => {
  formRef.value.validate().then(pay => {

    const arr = values.value

    if(arr.length == 0){
      message.warn('请至少填写一条字典值')
      return 
    }else {
      for(let i=0;i<arr.length;i++){
        const it = arr[i]
        if(!it.code){
          message.warn('存在没有编码的字典值，请填写')
          return 
        }
        if(!it.value){
          message.warn('存在没有内容的字典值，请填写')
          return 
        }
      }
    }
    pay.values = arr;

    confirm('您确定要保存字典信息吗?', async () => {
      if (props.item) {
        pay.id = props.item.id
      }
      saving.value = true
      const res = await saveDict(pay)
      saving.value = false
      if (res.data) {
        message.success('保存成功')
        emit('saved')
      } else {
        message.error('保存失败')
      }
    })
  })
}
</script>

<style lang="less" scoped></style>