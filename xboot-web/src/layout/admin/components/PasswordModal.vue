<template>
  <a-modal :open="true" title="修改密码" width="500px" @ok="handleOk" @cancel="handleCancel">
    <a-form :rules="rules" ref="formRef" :model="formState" :label-col="{ style: { width: '80px' } }" autocomplete="off">
      <a-form-item label="旧密码" name="password">
        <a-input type="password" v-model:value="formState.password" />
      </a-form-item>
      <a-form-item label="新密码" name="newPassword">
        <a-input type="password" v-model:value="formState.newPassword" />
      </a-form-item>
      <a-form-item label="确认密码" name="confirmPassword">
        <a-input type="password" v-model:value="formState.confirmPassword" />
      </a-form-item>
    </a-form>
  </a-modal>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { confirm } from '@/mixin'
import { changeMePassword } from '@/api/user'
import { useGlobalStore } from '@/store/global'
import { message } from 'ant-design-vue'
import md5 from 'js-md5'

const emit = defineEmits(['close'])
const global = useGlobalStore()
const formRef = ref(null)
const formState = reactive({})

const rules = {
  password: [
    { required: true, message: '请输入密码' }
  ],
  newPassword: [
    { required: true, message: '请输入新密码' },
    { min: 6, max: 10, message: '密码长度应6-10' ,trigger: 'blur'},
  ],
  confirmPassword: [
    { required: true, message: '请确认密码' },
    {
      trigger: 'blur',
      validator: async (_rule, value) => {
        if (value && formState.newPassword != value) {
          return Promise.reject("确认密码不一致!");
        }
        return Promise.resolve();
      }
    },
  ],
}


const handleCancel = () => {
  emit('close')
}
const handleOk = () => {
  formRef.value.validate().then(values => {
    confirm('您确定要修改密码吗?', async () => {
      const pay = {
        oldPwd: md5(values.password),
        newPwd: md5(values.newPassword)
      }
      const res = await changeMePassword(pay)
      if (res.data) {
        message.success('修改密码成功！')
        handleCancel()
        global.logout()
      }
    })
  })
}
</script>

<style lang="less" scoped></style>