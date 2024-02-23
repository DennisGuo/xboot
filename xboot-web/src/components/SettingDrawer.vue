<template>
  <a-drawer :open="true" :title="`${item ? '编辑' : '添加'}配置`" @close="onClose">
    <a-form ref="formRef" :model="formState" layout="vertical" :rules="rules">
      
      <a-form-item label="名称" name="name">
        <a-input v-model:value="formState.name" placeholder="请输入" />
      </a-form-item>
      <a-form-item label="编码" name="code">
        <a-input v-model:value="formState.code" placeholder="请输入" />
      </a-form-item>

      <a-form-item label="内容" name="content" >
        <a-textarea v-model:value="formState.content" placeholder="请输入" />
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
import { saveSetting } from '@/api/setting';
import { message } from 'ant-design-vue'
import { confirm } from '@/mixin'

const props = defineProps(['item'])
const emit = defineEmits(['close', 'saved'])

const rules = {
  content: [
    { required: true, message: '请输入配置内容' }
  ],
  name: [
    { required: true, message: '请输入配置名称' }
  ],
  code: [
    { required: true, message: '请输入配置编码' }
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
    formState.content = it.content
  } else {
    formState.name = null
    formState.code = null
    formState.content = null
  }
}

const onClose = () => {
  emit('close')
}

const toSave = () => {
  formRef.value.validate().then(values => {
    

    confirm('您确定要保存配置信息吗?', async () => {
      if (props.item) {
        values.id = props.item.id
      }
      saving.value = true
      const res = await saveSetting(values)
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