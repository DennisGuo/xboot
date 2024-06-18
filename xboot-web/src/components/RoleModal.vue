<template>
  <a-modal :open="true" :title="`${item ? '编辑' : '添加'}角色`" :footer="null" @cancel="onClose">
    <a-form ref="formRef" :model="formState" layout="vertical" :rules="rules">      
      <a-form-item label="名称" name="name">
        <a-input v-model:value="formState.name" placeholder="请输入" />
      </a-form-item>
      <a-form-item label="编码" name="code">
        <a-input v-model:value="formState.code" placeholder="请输入" />
      </a-form-item>      
      <a-form-item label="首页路径" name="homePage">
        <a-input v-model:value="formState.homePage" placeholder="请输入" />
      </a-form-item>      
      <a-form-item>
        <a-space>
          <a-button type="primary" @click="toSave" :loading="saving">保存</a-button>
        </a-space>
      </a-form-item>
    </a-form>
  </a-modal>
</template>

<script setup>
import { reactive, onMounted, ref } from 'vue'
import { saveRole} from '@/api/role';
import { message } from 'ant-design-vue'
import { confirm } from '@/mixin'

const props = defineProps(['item'])
const emit = defineEmits(['close', 'saved'])

const rules = {
  name: [
    { required: true, message: '请输入角色名称' }
  ],
  code: [
    { required: true, message: '请输入角色编码' }
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
    formState.homePage = it.homePage
  } else {
    formState.name = null
    formState.code = null
    formState.homePage = null
  }
}

const onClose = () => {
  emit('close')
}

const toSave = () => {
  formRef.value.validate().then(values => {  

    confirm('您确定要保存角色信息吗?', async () => {
      if (props.item) {
        values.id = props.item.id
      }
      saving.value = true
      const res = await saveRole(values)
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