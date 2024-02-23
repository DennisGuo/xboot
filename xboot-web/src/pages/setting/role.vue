<template>
  <div class="page-content">
    <div class="h2">角色管理</div>
    <div class="flex-row gap">
      <a-card title="角色列表" style="width:300px">
        <template #extra>
          <a-button type="primary" @click="toAdd">添加</a-button>
        </template>
        <a-spin :spinning="loading">
          <div class="role-list">
            <div class="item" :class="crtItem == item ? 'active' : ''" v-for="(item, index) in rows" :key="index"
              @click="clickItem(item)">
              <div class="info">
                <div class="name">{{ item.name }}</div>
                <div class="desc">{{ item.code }}</div>
              </div>
              <div class="actions">
                <a-space>
                  <EditOutlined @click.stop="toEdit(item)" class="pointer" />
                  <a-divider type="vertical" />
                  <DeleteOutlined @click.stop="handleRemove(item)" class="pointer" />
                </a-space>
              </div>
            </div>
            <a-pagination :current="pagination.current" :total="pagination.total"
              :showSizeChanger="pagination.showSizeChanger" :showQuickJumper="pagination.showQuickJumper"
              :showTotal="pagination.showTotal" @change="pagination.onChange" show-less-items class="mt" />
          </div>
        </a-spin>
      </a-card>
      <a-card class="flex1" :tab-list="tabs" :active-tab-key="tab" @tabChange="onTabChange">

        <template v-if="crtItem">
        <a-spin :spinning="fetching">
          <template v-if="tab == 'user'">
            <a-space>
              <a-button type="primary" @click="toAddUser">添加</a-button>
              <a-badge :count="checkedUser.length">
                <a-button @click="toRemoveUser" :disabled="checkedUser.length == 0">移出</a-button>
              </a-badge>
            </a-space>
            <UserTable ref="userTableRef" class="mt" :filter="{ roleId: crtItem.id }" @checked="onUserChecked">
              <template #action="{ record }">
                <a @click="removeUser(record)" class="text-red">移出</a>
              </template>
            </UserTable>
          </template>
          <template v-if="tab == 'menu'">
            <a-space>
              <a-button type="primary" @click="saveMenu">保存</a-button>
            </a-space>
            <MenuTree class="mt" @checked="onMenuChecked" :checked="crtItemMenu"/>
          </template>
        </a-spin>
        </template>
        <a-empty description="请先选择角色" v-else />
      </a-card>
    </div>
  </div>
  <RoleModal v-if="modal == 'modal'" :item="modalItem" @close="onCancel" @saved="onSaved" />
  <UserSelectModal v-if="modal == 'user'" @close="onCancel" @ok="toSaveUser"/>
</template>

<script setup>
import { ref, onMounted, reactive } from 'vue'
import { listRole, removeRole, removeRoleUser,saveRoleUser,saveRoleMenu, getRoleMenuList} from '@/api/role'
import RoleModal from '@/components/RoleModal.vue';
import UserTable from '@/components/UserTable.vue';
import UserSelectModal from '@/components/UserSelectModal.vue';
import MenuTree from '@/components/MenuTree.vue';
import { confirm } from '@/mixin'
import { message } from 'ant-design-vue'

const loading = ref(false)
const fetching = ref(false)
const modal = ref(null)
const userTableRef = ref(null)
const checkedUser = ref([])
const crtItem = ref(null)
const modalItem = ref(null)
const checkedMenu = ref([])
const crtItemMenu = ref([])
const rows = ref([])
const tab = ref('user')
const tabs = ref([
  { key: 'user', tab: '用户' },
  { key: 'menu', tab: '权限' },
])
const pagination = reactive({
  pageSize: 10,
  current: 1,
  total: 0,
  showSizeChanger: false,
  showQuickJumper: false,
  showTotal: (total) => `共${total}条`,
  onChange: (page) => {
    load(page)
  }
})
onMounted(() => {
  load()
})

const load = async (page = 1) => {
  const params = {
    page,
    size: pagination.pageSize,
  }
  loading.value = true;
  const res = await listRole(params);
  loading.value = false;
  if (res.data) {
    const { total, records } = res.data;
    pagination.total = total
    pagination.current = page
    rows.value = records
    if(records.length > 0 ){
      clickItem(records[0])
    }
  }
}

const onMenuChecked = (keys)=>{
  console.log(keys)
  checkedMenu.value = keys;
}

const saveMenu = ()=>{
  confirm('您确定要保存所选的菜单权限吗？',async ()=>{
    const res = await saveRoleMenu(crtItem.value.id,checkedMenu.value)
    if(res.data){
      message.success('保存成功！')
    }
  })
}

const removeUser = (user) => {
  confirm(`您确定要将用户【${user.name || user.username}】移除【${crtItem.value.name}】角色吗？`, async () => {
    const res = await removeRoleUser(crtItem.value.id, [user.id])
    if (res.data) {
      message.success('移除成功！')
      userTableRef.value.refresh()
    }
  })
}

const toAdd = () => {
  modalItem.value = null
  modal.value = 'modal'
}
const toEdit = (item) => {
  modalItem.value = item
  modal.value = 'modal'
}
const onCancel = () => {
  modal.value = null
}
const refresh = () => {
  load(pagination.current)
}
// 保存了角色
const onSaved = () => {
  onCancel()
  if (modalItem.value) {
    refresh()
  } else {
    load(1)
  }
}

// 保存所选的用户
const toSaveUser = (users)=>{
  confirm(`您确定要将所选的【${users.length}】位用户添加【${crtItem.value.name}】角色吗？`,async ()=>{
    const res = await saveRoleUser(crtItem.value.id,users.map(i=>i.id))
    if(res.data){
      modal.value = null
      message.success('添加成功！')
      userTableRef.value.refresh()
    }
  })
}

// 切换角色tab
const onTabChange = (key) => {
  tab.value = key;
}
// 删除
const handleRemove = item => {
  confirm(`您确定要删除【${item.name}】吗？`, async () => {
    const res = await removeRole(item.id)
    if (res.data) {
      message.success('删除成功')
      load(pagination.current)
    }
  })
}
// 点击角色
const clickItem = async (item) => {
  crtItem.value = item
  // 加载该角色权限
  fetching.value = true;
  const res = await getRoleMenuList(item.id)
  fetching.value = false;
  crtItemMenu.value = res.data || []
}

// 勾选用户
const onUserChecked = (users)=>{
  checkedUser.value = users;
}
// 移除角色用户
const toRemoveUser =()=>{
  confirm(`您确定要移除所选的【${checkedUser.value.length}】位用户角色吗？`,async ()=>{
    const res = await removeRoleUser(crtItem.value.id,checkedUser.value.map(i=>i.id))
    if(res.data){
      message.success('移除成功！')
      userTableRef.value.refresh()
    }
  })
}
// 打开选择用户的弹框
const toAddUser =()=>{
  modal.value = 'user'
}

</script>

<style lang="less" scoped>
@import url('@/assets/common.less');

.role-list {
  .flex-col();
  gap: 8px;

  .item {
    .flex-row();
    padding: 8px 0;
    border-bottom: 1px dashed rgba(0, 0, 0, .1);
    cursor: pointer;
    transition: .3s;

    &:hover {
      border-left: 8px solid #001529;
      padding-left: 12px;
    }

    &.active {
      border-left: 8px solid #de2151;
      padding-left: 12px;
    }

    .info {
      .flex1();

      .desc {
        margin-top: 4px;
        opacity: .7;
      }
    }
  }
}
</style>
