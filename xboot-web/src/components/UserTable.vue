<template>
  <a-table :loading="loading" :columns="columns" :rowKey="r => r.id" :data-source="rows" :pagination="pagination"
    :rowSelection="rowSelection" bordered>
    <template #bodyCell="{ column, record, index }">
      <template v-if="column.key === 'index'">
        <span>{{ ((pagination.current - 1) * pagination.pageSize) + index + 1 }}</span>
      </template>
      <template v-if="column.key === 'sex'">
        <a-tag :color="record.sex == 0 ? 'blue' : 'pink'">{{ record.sex == 0 ? '男' : '女' }}</a-tag>
      </template>
      <template v-if="column.key === 'role'">
        <a-tag v-if="record.role">{{ record.role.name }}</a-tag>
        <span v-else>-</span>
      </template>
      <template v-if="column.dataIndex === 'remark'">
        <a-popover v-if="action == false" :content="record.remark">
          <a>查看</a>
        </a-popover>
        <span v-else>{{ record.remark }}</span>
      </template>
      <template v-if="column.key === 'action'">
        <slot name="action" :record="record" />
      </template>
    </template>
  </a-table>
</template>

<script setup>
import { ref, reactive, onMounted, watch, toRaw } from 'vue'
import { listUser } from '@/api/user'

const props = defineProps(['filter', 'action'])
const emit = defineEmits(['checked'])

const columns = ref([
  {
    title: '序号',
    key: 'index',
    width: 80,
    align: 'center'
  },
  {
    title: '姓名',
    dataIndex: 'name',
    width: 100,
  },
  {
    title: '性别',
    key: 'sex',
    width: 80
  },
  {
    title: '用户名',
    dataIndex: 'username',
    width: 120,
  },
  {
    title: '电话',
    dataIndex: 'phone',
    width: 120,
  },
  {
    title: '角色',
    key: 'role',
    width: 120,
  },
  {
    title: '备注',
    dataIndex: 'remark',
  },
])
const pagination = reactive({
  pageSize: 10,
  current: 1,
  total: 0,
  showSizeChanger: true,
  showQuickJumper: true,
  showTotal: (total) => `共${total}条`,
  onChange: (page) => {
    load(page)
  }
})
const rows = ref([])
const loading = ref(false)

// 勾选
const rowSelection = {
  onChange(keys, rows) {
    emit('checked', toRaw(rows))
  }
}

onMounted(() => {
  load()
  if (props.action !== false) {
    columns.value = [...columns.value,
    {
      title: '操作',
      key: 'action',
      width: 100
    },
    ]
  }
})
watch(() => props.filter, () => {
  load()
})

const load = async (page = 1) => {
  const params = {
    page,
    size: pagination.pageSize,
  }
  if (props.filter) {
    for (const key in props.filter) {
      const val = props.filter[key]
      if (val) {
        params[key] = val;
      }
    }
  }
  loading.value = true;
  const res = await listUser(params);
  loading.value = false;
  if (res.data) {
    const { total, records } = res.data;
    pagination.total = total
    pagination.current = page
    rows.value = records
  }
}

const refresh = () => {
  load(pagination.current)
}

defineExpose({
  refresh,
  load,
})

</script>

<style lang="less" scoped></style>