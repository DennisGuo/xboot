<template>
  <a-result :status="status" :title="status" :sub-title="title">
    <template #extra>
      <a-button type="primary" @click="router.push({ name: 'login' })">返回登录</a-button>
    </template>
  </a-result>
</template>

<script setup>
import { computed } from 'vue'
import { useRoute, useRouter } from 'vue-router'
const route = useRoute()
const router = useRouter()
const status = computed(() => route.query.status || 403)
const title = computed(() => {
  switch (status.value) {
    case '403':
      return '授权过期或无权访问！'
    case '404':
      return '页面不存在'
    default:
      return '访问失败咯~'
  }
})
</script>

<style lang="less" scoped></style>