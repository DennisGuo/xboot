<template>
  <div class="page-content">
    <div class="h2">参数配置</div>
    <div class="padding bg-white mb flex-row">
      <div class="flex1">
        <a-form layout="inline" :model="searchState" @finish="toSearch">
          <a-form-item name="keyword">
            <a-input v-model:value="searchState.keyword" placeholder="名称|编码|内容" />
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
        <a-button type="primary" @click="toAdd" v-auth="`config-add`">新增</a-button>
        <a-button @click="refresh">刷新</a-button>
      </a-space>
    </div>
    <a-table :loading="loading" :columns="columns" :data-source="rows" :pagination="pagination" bordered>
      <template #bodyCell="{ column, record, index }">
        <template v-if="column.key === 'index'">
          <span>{{ ((pagination.current - 1) * pagination.pageSize) + index + 1 }}</span>
        </template>        
        <template v-if="column.key === 'sys'">
          <a-tag>{{ record.isSys ? '是':'否' }}</a-tag>
        </template>        
        <template v-if="column.key === 'action'">
          <a-space>
            <a @click="handleEdit(record)" v-auth="`config-edit`">编辑</a>
            <a @click="handleRemove(record)" class="text-red" v-auth="`config-delete`">删除</a>
          </a-space>
        </template>
      </template>
    </a-table>
  </div>
  <SettingDrawer v-if="modal == 'drawer'" :item="crtItem" @close="onCancel" @saved="onSaved"/>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue';
import { message } from 'ant-design-vue';
import { listSetting, removeSetting } from '@/api/setting'
import SettingDrawer from '@/components/SettingDrawer.vue';
import { confirm } from '@/mixin'

const modal = ref(null)
const crtItem = ref(null)
const search = ref(false)
const loading = ref(false)
const rows = ref([])
const searchState = reactive({})
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

const columns = [
  {
    title: '序号',
    key: 'index',
    width: 80,
    align: 'center'
  }, 
  {
    title: '名称',
    dataIndex: 'name',
    width: 100,
  }, 
  {
    title: '编码',
    dataIndex: 'code',
    width: 100
  },
  {
    title: '内容',
    dataIndex: 'content',
  },
  {
    title: '系统',
    key: 'sys',
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
  const res = await listSetting(params);
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
  modal.value = 'drawer'
}
const refresh = () => {
  load(pagination.current)
}
const onCancel = () => {
  modal.value = null
}

const handleEdit = item => {
  crtItem.value = item
  modal.value = 'drawer'
}
// 删除
const handleRemove = item => {
  confirm(`您确定要删除【${item.name}】吗？`, async () => {
    const res = await removeSetting(item.id)
    if (res.data) {
      message.success('删除成功')
      load(pagination.current)
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

<style lang="less" scoped>

</style>