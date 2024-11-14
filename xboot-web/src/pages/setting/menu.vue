<template>
  <div class="page-content">
    <div class="h2">菜单管理</div>
    <div class="padding bg-white mb flex-row">
      <div class="flex1">
      </div>
      <a-space>
        <a-button type="primary" @click="toAdd" v-auth="`menu-add`">新增</a-button>
        <a-button @click="refresh">刷新</a-button>
      </a-space>
    </div>
    <a-table :loading="loading" :columns="columns" :data-source="rows" :row-key="r => r.id" :defaultExpandAllRows="true"
    v-model:expandedRowKeys="expandedRowKeys"
      :rowClassName="record => record.type == 0? 'bg-gray-100': record.type == 1? 'bg-gray-200': 'bg-gray-300'"
      :pagination="false" bordered>
      <template #bodyCell="{ column, record, index }">
        <template v-if="column.key === 'type'">
          <a-tag :color="record.type == 0? 'blue': record.type == 1 ? 'green': undefined">{{ record.type == 0 ? '菜单' : record.type == 1 ? '页面' : '按钮' }}</a-tag>
        </template>
        <template v-if="column.key === 'icon'">
          <span v-if="record.icon">
            <component :is="record.icon" />
          </span>
        </template>
        <template v-if="column.key === 'action'">
          <a-space>
            <a @click="handleEdit(record)" v-auth="`menu-edit`">编辑</a>
            <a @click="handleRemove(record)" class="text-red" v-auth="`menu-delete`">删除</a>
          </a-space>
        </template>
      </template>
    </a-table>
  </div>
  <MenuDrawer v-if="modal == 'menu'" :item="crtItem" @close="onCancel" @saved="onSaved" />
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue';
import { message } from 'ant-design-vue';
import { getMenuTree, removeMenu } from '@/api/menu'
import MenuDrawer from '@/components/MenuDrawer.vue';
import { confirm } from '@/mixin'

const modal = ref(null)
const crtItem = ref(null)
const loading = ref(false)
const rows = ref([])
const expandedRowKeys = ref([])
const columns = [
  {
    title: '名称',
    dataIndex: 'name',
    width: 220,
  },
  {
    title: '类型',
    key: 'type',
    width: 80,
  },
  {
    title: '编码',
    dataIndex: 'code',
    width: 180
  },
  {
    title: '路径',
    dataIndex: 'path',
  },
  {
    title: '组件',
    dataIndex: 'component',
  },
  {
    title: '图标',
    key: 'icon',
    width: 80,
    align: 'center'
  },
  {
    title: '排序',
    dataIndex: 'sort',
    width: 80,
    align: 'center'
  },
  {
    title: '操作',
    key: 'action',
    width: 100
  },
]



onMounted(() => {
  load()
})


const load = async () => {
  loading.value = true;
  const res = await getMenuTree();
  loading.value = false;
  if (res.data) {
    rows.value = res.data || []
    expandedRowKeys.value = rows.value.filter(i=>i.type == 0 && i.children && i.children.length > 0).map(i=>i.id)
  }
}
const toAdd = () => {
  crtItem.value = null
  modal.value = 'menu'
}
const refresh = () => {
  load()
}
const onCancel = () => {
  modal.value = null
}

const handleEdit = item => {
  crtItem.value = item
  modal.value = 'menu'
}
// 删除
const handleRemove = item => {
  confirm(`您确定要删除【${item.name}】吗？`, async () => {
    const res = await removeMenu(item.id)
    if (res.data) {
      message.success('删除成功')
      load()
    }
  })
}
const onSaved = () => {
  onCancel()
  if (crtItem.value) {
    refresh()
  } else {
    load(1)
  }
}

</script>

<style lang="less" scoped></style>