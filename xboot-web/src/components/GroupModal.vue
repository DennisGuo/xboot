<template>
  <a-modal :open="true" :title="`${item ? '编辑' : '添加'}机构部门`" :footer="null" @cancel="onClose">
    <a-form ref="formRef" :model="formState" layout="vertical" :rules="rules">      
      <a-form-item label="上级" name="pid">
        <GroupSelect v-model:value="formState.pid" field="id"/>
      </a-form-item>
      <a-form-item label="名称" name="name">
        <a-input v-model:value="formState.name" placeholder="请输入" />
      </a-form-item>
      <a-form-item label="编码" name="code">
        <a-input v-model:value="formState.code" placeholder="请输入" />
      </a-form-item>      
      <a-form-item label="备注" name="remark">
        <a-textarea v-model:value="formState.remark" placeholder="请输入" />
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
  </a-modal>
</template>

<script setup>
import { reactive, onMounted, ref } from 'vue'
import { saveGroup } from '@/api/group';
import { message } from 'ant-design-vue'
import { confirm } from '@/mixin'
import GroupSelect from './GroupSelect.vue'

const props = defineProps(['item'])
const emit = defineEmits(['close', 'saved'])

const rules = {
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
  
    formState.name = it.name
    formState.code = it.code
    formState.pid = it.pid
    formState.remark = it.remark
    formState.sort = it.sort
  } else {
    formState.name = null
    formState.code = null
    formState.pid = null
    formState.remark = null
    formState.sort = 1
  }
}

const onClose = () => {
  emit('close')
}

const toSave = () => {
  formRef.value.validate().then(values => {  
    console.log(values)
    confirm('您确定要保存机构部门信息吗?', async () => {
      if (props.item) {
        values.id = props.item.id
      }
      saving.value = true
      const res = await saveGroup(values)
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