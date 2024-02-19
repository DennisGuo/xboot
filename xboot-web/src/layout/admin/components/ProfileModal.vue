<template>
  <a-modal :open="true" title="个人信息" width="500px" @ok="handleOk" @cancel="handleCancel">
    <a-form ref="formRef" :model="formState" :label-col="{ style: { width: '80px' } }" autocomplete="off" >
      <a-form-item label="用户ID" name="id">
        <a-input v-model:value="formState.id" disabled/>
      </a-form-item>
      <a-form-item label="用户名" name="username">
        <a-input v-model:value="formState.username" disabled/>
      </a-form-item>
      <a-form-item label="姓名" name="name">
        <a-input v-model:value="formState.name"/>
      </a-form-item>
      <a-form-item label="手机号" name="phone">
        <a-input v-model:value="formState.phone"/>
      </a-form-item>
      <a-form-item label="性别" name="sex">
        <a-radio-group v-model:value="formState.sex">
          <a-radio :value="0">男</a-radio>
          <a-radio :value="1">女</a-radio>
        </a-radio-group>
      </a-form-item>
      <a-form-item label="角色" name="roleName">
        <a-input v-model:value="formState.roleName" disabled/>
      </a-form-item>
      <a-form-item label="备注" name="remark">
        <a-textarea v-model:value="formState.remark"/>
      </a-form-item>
    </a-form>
  </a-modal>
</template>

<script setup>
import { ref, reactive ,onMounted} from 'vue'
import { useGlobalStore } from '@/store/global'
import { confirm } from '@/mixin'
import { message } from 'ant-design-vue'
import { saveUser } from '@/api/user'

const emit = defineEmits(['close'])

const global = useGlobalStore()
const formRef = ref(null)
const formState = reactive({})

onMounted(()=>{
  init()
})

const init = async ()=>{
  const me = await global.getMe()
  formState.id = me.id 
  formState.username = me.username 
  formState.name = me.name
  formState.phone = me.phone
  formState.sex = me.sex
  formState.roleName = me.role ? me.role.name : null 
  formState.remark = me.remark

}

const handleCancel = () => {
  emit('close')
}

const handleOk  =()=>{
  formRef.value.validate().then(values=>{
    confirm('您确定要保存个人信息吗?',async ()=>{
      const res = await saveUser(values)
      if(res.data){
        global.me = {...global.me,...values}
        message.success('保存成功')
        handleCancel()
      }
    })
  })
}

</script>

<style lang="less" scoped></style>