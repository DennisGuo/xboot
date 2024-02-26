<template>
  <a-spin :spinning="loading">
    <a-tree v-model:expandedKeys="expandedKeys" v-model:selectedKeys="selectedKeys" v-model:checkedKeys="checkedKeys"
      :checkable="checkable" :treeData="treeData" blockNode :fieldNames="fieldNames" :checkStrictly="true"
      @check="onCheck" @select="onSelect">
      <template #title="record">
        <div class="flex-row">
          <div class="flex1">
            <a-tag>{{ record.data.code }}</a-tag>
            <a-tooltip :content="record.data.remark || record.name">
              <span> {{ record.name }} </span>
            </a-tooltip>
          </div>
          <a-space v-if="action == true">            
            <EditOutlined class="pointer" @click.stop="toEdit(record.data)" />
            <a-divider type="vertical" />
            <DeleteOutlined class="pointer text-red" @click.stop="toRemove(record.data)" />
          </a-space>
        </div>
      </template>
    </a-tree>
  </a-spin>
  <GroupModal v-if="modal == 'modal'" :item="crtItem" @close="onCancel" @saved="onSaved" />
</template>

<script setup>
import { ref, onMounted, watch } from 'vue'
import { getGroupTree,removeGroup } from '@/api/group'
import { confirm,findItemInTree} from '@/mixin'
import { message } from 'ant-design-vue'; 
import GroupModal from './GroupModal.vue'

const props = defineProps(['checked', 'action', 'checkable'])
const emit = defineEmits(['checked','select','load'])

const fieldNames = {
  title: 'name',
  key: 'id'
}
const loading = ref(false)
const modal = ref(false)
const crtItem = ref(null)
const treeData = ref([])
const expandedKeys = ref([])
const selectedKeys = ref([])
const checkedKeys = ref([])

onMounted(() => {
  load()
  parseProps()
})

watch(() => props.checked, () => {
  parseProps()
})


const parseProps = () => {
  checkedKeys.value = props.checked || []
}

const load = async () => {
  loading.value = true
  const res = await getGroupTree();
  loading.value = false
  const arr = res.data || []
  treeData.value = arr

  expandedKeys.value = arr.filter(i => i.children && i.children.length > 0).map(i => i.id)

  emit('load',arr)

}

const onCheck = ({ checked }) => {
  emit('checked', checked)
}

const onSelect = (keys) => {
  const key = keys[0]
  const item = findItemInTree(treeData.value,i=>i.id == key)
  console.log(item)
  emit('select', item)
}

const toRemove = (record) => {
  confirm(`您确定要删除【${record.name}】吗?`,async ()=>{
    const res = await removeGroup(record.id)
    if(res.data){
      message.success('删除成功！')
      load()
    }
  })
}
const toEdit = (record) => {
  console.log(record)
  crtItem.value = record;
  modal.value = 'modal'
}

const onCancel = ()=>{
  modal.value =null
}

const onSaved =()=>{
  onCancel()
  load()
}

defineExpose({
  refresh: load,
})

</script>

<style lang="less" scoped></style>