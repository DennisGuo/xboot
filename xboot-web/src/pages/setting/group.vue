<template>
  <div class="page-content">
    <div class="h2">机构部门管理</div>
    <div class="flex-row gap">
      <a-card title="机构部门" style="width:380px">
        <template #extra>
          <a-button type="primary" @click="toAdd">添加</a-button>
        </template>
        <GroupTree ref="groupTreeRef" @select="onSelect" @load="onLoadTree"/>
      </a-card>
      <div class="flex-col gap flex1">
        <a-card title="基本信息" class="base-info">
          <template #extra>
            <a-space>
              <a-button type="primary" @click="toEdit" :disabled="!crtItem" >修改</a-button>              
              <a-button danger @click="toRemove" :disabled="!crtItem">删除</a-button>              
            </a-space>
          </template>
          <a-empty description="请先选择机构部门" v-if="!crtItem"/>
          <a-descriptions v-else   bordered size="small">
            <a-descriptions-item label="名称">{{ crtItem.name }}</a-descriptions-item>
            <a-descriptions-item label="编码">{{ crtItem.code }}</a-descriptions-item>
            <a-descriptions-item label="排序">{{ crtItem.sort }}</a-descriptions-item>
            <a-descriptions-item label="备注" :span="3">{{ crtItem.remark }}</a-descriptions-item>
          </a-descriptions>
        </a-card>
        <a-card title="用户列表">
          <template #extra>
            <a-space>
              <a-button type="primary" @click="toAddUser" :disabled="!crtItem">添加</a-button>
              <a-badge :count="checkedUser.length">
                <a-button @click="toRemoveUser" :disabled="checkedUser.length == 0">移出</a-button>
              </a-badge>
            </a-space>
          </template>
          <template v-if="crtItem">
            <UserTable ref="userTableRef" class="mt" :filter="{ groupId: crtItem.id }" @checked="onUserChecked">
              <template #action="{ record }">
                <a @click="removeUser(record)" class="text-red">移出</a>
              </template>
            </UserTable>
          </template>
          <a-empty description="请先选择机构部门" v-else />

        </a-card>
      </div>
    </div>
  </div>
  <GroupModal v-if="modal == 'modal'" :item="modalItem" @close="onCancel" @saved="onSaved" />
  <UserSelectModal v-if="modal == 'user'" @close="onCancel" @ok="toSaveUser" />
</template>

<script setup>
import { ref, onMounted, reactive } from 'vue'
import { removeGroupUser, saveGroupUser ,getGroup} from '@/api/group'
import GroupModal from '@/components/GroupModal.vue';
import GroupTree from '@/components/GroupTree.vue';
import UserTable from '@/components/UserTable.vue';
import UserSelectModal from '@/components/UserSelectModal.vue';
import { confirm } from '@/mixin'
import { message } from 'ant-design-vue'

const modal = ref(null)
const userTableRef = ref(null)
const groupTreeRef = ref(null)
const checkedUser = ref([])
const crtItem = ref(null)
const modalItem = ref(null)


onMounted(() => { })


const removeUser = (user) => {
  confirm(`您确定要将用户【${user.name || user.username}】移出吗？`, async () => {
    const res = await removeGroupUser(crtItem.value.id, [user.id])
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
const onSaved = async () => {
  onCancel()
  groupTreeRef.value.refresh()
  if(modalItem.value){
    const res = await getGroup(modalItem.value.id)
    crtItem.value = res.data
  }
}
const onCancel = () => {
  modal.value = null
}

const onLoadTree = (data)=>{
  if(data.length > 0){
    crtItem.value = data[0]
  }
}

// 保存所选的用户
const toSaveUser = (users) => {
  confirm(`您确定要将所选的【${users.length}】位用户添加【${crtItem.value.name}】角色吗？`, async () => {
    const res = await saveGroupUser(crtItem.value.id, users.map(i => i.id))
    if (res.data) {
      modal.value = null
      message.success('添加成功！')
      userTableRef.value.refresh()
    }
  })
}

// 点击
const onSelect = async (item) => {
  crtItem.value = item
}

// 勾选用户
const onUserChecked = (users) => {
  checkedUser.value = users;
}
// 移除机构用户
const toRemoveUser = () => {
  confirm(`您确定要移出所选的【${checkedUser.value.length}】位用户吗？`, async () => {
    const res = await removeGroupUser(crtItem.value.id, checkedUser.value.map(i => i.id))
    if (res.data) {
      message.success('移除成功！')
      checkedUser.value = []
      userTableRef.value.refresh()
    }
  })
}
// 打开选择用户的弹框
const toAddUser = () => {
  modal.value = 'user'
}
const toEdit = () => {
  modalItem.value = {...crtItem.value}
  modal.value = 'modal'
}
const toRemove = () => {
  const record=  crtItem.value 
  confirm(`您确定要删除【${record.name}】吗?`,async ()=>{
    const res = await removeGroup(record.id)
    if(res.data){
      message.success('删除成功！')
      groupTreeRef.value.refresh()
    }
  })
}

</script>

<style lang="less" scoped>
.base-info{
  :deep(.ant-descriptions-item-label){
    width: 100px;
  }
}
</style>
