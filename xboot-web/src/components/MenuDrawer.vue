<template>
  <a-drawer :open="true" :title="`${item ? '编辑' : '添加'}菜单`" @close="onClose">
    <a-form ref="formRef" :model="formState" layout="vertical" :rules="rules">
      <a-form-item label="类型" name="type">
        <a-radio-group v-model:value="formState.type">
          <a-radio :value="0">菜单</a-radio>
          <a-radio :value="1">页面</a-radio>
          <a-radio :value="2">按钮</a-radio>
        </a-radio-group>
      </a-form-item>
      <a-form-item label="上级" name="pid">
        <MenuSelect v-model:value="formState.pid" placeholder="请输入" />
      </a-form-item>
      <a-form-item label="名称" name="name">
        <a-input v-model:value="formState.name" placeholder="请输入" />
      </a-form-item>
      <a-form-item label="编码" name="code">
        <a-input v-model:value="formState.code" placeholder="请输入" />
      </a-form-item>

      <a-form-item label="路径" name="path" v-if="[0, 1].includes(formState.type)">
        <a-input v-model:value="formState.path" placeholder="请输入" />
      </a-form-item>
      <a-form-item label="组件" name="component" v-if="1 == formState.type">
        <a-input v-model:value="formState.component" placeholder="请输入" />
      </a-form-item>
      <a-form-item label="图标" name="icon" v-if="[0, 1].includes(formState.type)" :autoLink="false">
        <IconSelect v-model:value="formState.icon" />
      </a-form-item>
      <a-form-item label="排序" name="sort">
        <a-input-number v-model:value="formState.sort" placeholder="请输入" />
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
import { saveMenu } from '@/api/menu';
import { message } from 'ant-design-vue'
import { confirm } from '@/mixin'
import MenuSelect from './MenuSelect.vue';
import IconSelect from './IconSelect.vue';

const props = defineProps(['item'])
const emit = defineEmits(['close', 'saved'])

const rules = {
  type: [
    { required: true, message: '请选择类型' }
  ],
  name: [
    { required: true, message: '请输入名称' }
  ],
  code: [
    { required: true, message: '请输入编码' }
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
    formState.type = it.type || 0
    formState.name = it.name
    formState.code = it.code
    formState.pid = it.pid
    formState.path = it.path
    formState.component = it.component
    formState.icon = it.icon
    formState.sort = it.sort
  } else {
    formState.type = 0
    formState.name = null
    formState.code = null
    formState.pid = null
    formState.path = null
    formState.component = null
    formState.icon = null
    formState.sort = 1
  }
}

const onClose = () => {
  emit('close')
}

const toSave = () => {
  formRef.value.validate().then(values => {
    if (values.type == 1 && !values.component) {
      message.warn('组件类型菜单需要填写组件路径！')
      return;
    }

    confirm('您确定要保存菜单信息吗?', async () => {
      if (props.item) {
        values.id = props.item.id
      }
      saving.value = true
      const res = await saveMenu(values)
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