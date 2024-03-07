<template>
  <div class="page-content">
    <div class="h2">用户管理</div>
    <div class="padding bg-white mb flex-row">
      <div class="flex1">
        <a-form layout="inline" :model="searchState" @finish="toSearch">
          <a-form-item name="keyword">
            <a-input v-model:value="searchState.keyword" placeholder="姓名|用户名|电话" />
          </a-form-item>
          <a-form-item>
            <a-space>
              <a-button type="primary" html-type="submit">搜索</a-button>
              <a-button @click="reset" v-if="search">重置</a-button>
            </a-space>
          </a-form-item>
        </a-form>
      </div>
      <a-space>
        <a-button type="primary" @click="toAdd">新增</a-button>
        <a-button @click="refresh">刷新</a-button>
      </a-space>
    </div>
    <a-table :loading="loading" :columns="columns" :data-source="rows" :pagination="pagination" bordered>
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
        <template v-if="column.key === 'action'">
          <a-space>
            <a @click="handleEdit(record)">编辑</a>
            <a @click="handleRemove(record)" class="text-red">删除</a>
          </a-space>
        </template>
      </template>
    </a-table>
  </div>
  <UserDrawer v-if="modal == 'user'" :item="crtItem" @saved="onSaved" @close="onCancel" />
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue';
import { listUser, removeUser } from '@/api/user'
import { confirm } from '@/mixin'
import { message } from 'ant-design-vue';
import UserDrawer from '@/components/UserDrawer.vue'

const columns = [
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
    title: '备注',
    dataIndex: 'remark',
  },
  {
    title: '角色',
    key: 'role',
    width: 120,
  },
  {
    title: '操作',
    key: 'action',
    width: 100
  },
]
const pagination = reactive({
  pageSize: 10,
  current: 1,
  total: 0,
  showSizeChanger: true,
  showQuickJumper: true,
  showTotal: (total) => `共${total}条`,
  onChange: (page,size) => {
    pagination.pageSize = size
    load(page)
  }
})
const rows = ref([])
const loading = ref(false)
const modal = ref(false)
const crtItem = ref(null)
const search = ref(false)
const searchState = reactive({})


onMounted(() => {
  load(1)
})
const reset = () => {
  searchState.keyword = null
  search.value = false
  load(1)
}
const toSearch = () => {
  search.value = true;
  load(1)
}
const load = async (page = 1) => {
  const params = {
    page,
    size: pagination.pageSize,
  }
  if (search.value) {
    for (const key in searchState) {
      const val = searchState[key]
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
const toAdd = () => {
  crtItem.value = null
  modal.value = 'user'
}
const handleEdit = item => {
  crtItem.value = item
  modal.value = 'user'
}
// 删除
const handleRemove = item => {
  confirm(`您确定要删除【${item.name || item.username}】吗？`, async () => {
    const res = await removeUser(item.id)
    if (res.data) {
      message.success('删除成功')
      load(pagination.current)
    }
  })
}
const refresh = () => {
  load(pagination.current)
}
const onCancel = () => {
  modal.value = null
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