<template>
  <div class="icon-select">
    <div class="actions">
      <div class="flex1">
        <div class="selected">
          <component :is="selected" v-if="selected" />
        </div>
      </div>
      <a-space>
        <a-input placeholder="关键字" v-model:value="keyword" size="small"/>
        <a-radio-group v-model:value="type" button-style="solid" size="small">
          <a-radio-button value="Outlined">线框</a-radio-button>
          <a-radio-button value="Filled">填充</a-radio-button>
        </a-radio-group>
      </a-space>
    </div>
    <div class="list">
      <div class="item" v-for="(item, index) in icons" :key="index" @click="select(item)">
        <component :is="item" :title="item"/>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'
import * as Icons from '@ant-design/icons-vue'

const props = defineProps(['value'])
const emit = defineEmits(['update:value'])

const arr = Object.keys(Icons).filter(i => i.indexOf('Outlined') >= 0 || i.indexOf('Filled') >= 0)

const selected = ref(props.value)
const type = ref('Outlined')
const keyword = ref(null)
const icons = computed(() => {
  return arr.filter(i => {
    let rs = i.indexOf(type.value) >= 0
    if(keyword.value ){
      rs = rs && i.toLocaleLowerCase().indexOf(keyword.value.toLocaleLowerCase()) >= 0
    }
    return rs
  })
})


const select = name => {
  selected.value = name
  emit('update:value', name)
}

</script>

<style lang="less" scoped>
@import url('@/assets/common.less');

.icon-select {
  .flex-col();

  .actions {
    .flex-row();

    .selected {
      width: 32px;
      height: 32px;
      border: 1px dashed #eee;
      margin-bottom: 8px;
      line-height: 32px;
      text-align: center;
      background-color: rgba(0, 0, 0, .1);
    }
  }

  .list {
    max-height: 200px;
    overflow-y: auto;

    .item {
      display: inline-block;
      width: 32px;
      height: 32px;
      line-height: 32px;
      text-align: center;
      cursor: pointer;

      &:hover {
        background-color: rgba(0, 0, 0, .1);
      }
    }
  }
}
</style>