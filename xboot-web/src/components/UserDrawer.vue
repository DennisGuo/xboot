<template>
  <a-drawer :open="true" :title="`${item ? '编辑' : '添加'}用户`" @close="onClose">
    <a-form ref="formRef" :model="formState" layout="vertical" :rules="rules">
      <a-form-item label="用户名" name="username">
        <a-input v-model:value="formState.username" placeholder="请输入" :disabled="!!item" />
      </a-form-item>
      <a-form-item label="密码" name="password" v-if="!item"
        :rules="[{ required: true, message: '请输入密码' }, { min: 6, max: 10, message: '密码长度应6-10', trigger: 'blur' }]">
        <a-input type="password" v-model:value="formState.password" placeholder="请输入" />
      </a-form-item>
      <a-form-item label="姓名" name="name">
        <a-input v-model:value="formState.name" placeholder="请输入" />
      </a-form-item>
      <a-form-item label="电话" name="phone">
        <a-input v-model:value="formState.phone" placeholder="请输入" />
      </a-form-item>
      <a-form-item label="性别" name="sex">
        <a-radio-group v-model:value="formState.sex">
          <a-radio :value="0">男</a-radio>
          <a-radio :value="1">女</a-radio>
        </a-radio-group>
      </a-form-item>
      <a-form-item label="角色" name="roleIds">
        <RoleSelect v-model:value="formState.roleIds" mode="multiple"></RoleSelect>
      </a-form-item>
      <a-form-item label="备注" name="remark">
        <a-textarea v-model:value="formState.remark" placeholder="请输入" />
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
import RoleSelect from './RoleSelect.vue'
import { saveUser,getUserRoles } from '@/api/user';
import { message } from 'ant-design-vue'
import { confirm } from '@/mixin'

const props = defineProps(['item'])
const emit = defineEmits(['close','saved'])

const rules = {
  username: [
    { required: true, message: '请输入用户名' }
  ],
  name: [
    { required: true, message: '请输入姓名' }
  ],
}

const formRef = ref(null)
const formState = reactive({})
const saving = ref(false)

onMounted(() => {
  parseItem()
})

const parseItem = async () => {
  const it = props.item
  if (it) {
    formState.username = it.username
    formState.name = it.name
    formState.phone = it.phone
    formState.sex = it.sex
    formState.remark = it.remark
    if(it.id){

      const res = await getUserRoles(it.id)
      if(res.data){
        formState.roleIds = res.data.map(i=>i.id)
      }
    }
  } else {
    formState.username = null
    formState.name = null
    formState.phone = null
    formState.sex = null
    formState.remark = null
    formState.roleIds = []
  }
  formState.password = null
}

const onClose = () => {
  emit('close')
}

const toSave = () => {
  formRef.value.validate().then(values => {
    const pay = {...values}
    if(props.item){
      pay.id = props.item.id
    }
    console.log(pay);

    confirm('您确定要保存用户信息吗?',async ()=>{

      saving.value = true
      const res = await saveUser(pay)
      saving.value = false
      if(res.data){
        message.success('保存成功')
        emit('saved')
      }else{
        message.error('保存失败')
      }
    })
  })
}
</script>

<style lang="less" scoped></style>