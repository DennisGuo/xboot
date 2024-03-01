<template>
  <a-modal width="900px" title="选择用户" :open="true" @ok="handleOk" @cancel="handleCancel" >
    <div class="padding">
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
    <UserTable ref="userTableRef" class="mt" :filter="filter" @checked="onUserChecked" :action="false"></UserTable>
  </a-modal>
</template>

<script setup>
import { ref,reactive } from 'vue'
import UserTable from './UserTable.vue';
import { message } from 'ant-design-vue';

const emit = defineEmits(['ok','close'])

const checkedUser = ref([])
const userTableRef = ref(null)
const search = ref(false)
const searchState = reactive({})
const filter = reactive({})

const toSearch = () => {
  search.value = true;
  filter.keyword = searchState.keyword
  load(1)
}

const handleCancel =()=>{
  emit('close')
}

const reset = () => {
  searchState.keyword = null
  search.value = false
  filter.keyword = null 
  load(1)
}

const load = (page=1) => {
  userTableRef.value.load(page)
}

const handleOk =()=>{
  if(checkedUser.value.length > 0 ){
    emit('ok',checkedUser.value)
  }else{
    message.warn('请至少选择一个用户！');
  }
}
const onUserChecked =(users)=>{
  checkedUser.value = users;
}

</script>

<style lang="less" scoped>

</style>