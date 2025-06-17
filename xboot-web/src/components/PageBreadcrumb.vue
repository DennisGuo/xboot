<template>
  <div class="h2">
    <a-breadcrumb>
      <a-breadcrumb-item v-for="(item, index) in items" :key="index">
        {{ item?.meta?.title || "" }}
      </a-breadcrumb-item>
    </a-breadcrumb>
  </div>
</template>

<script setup>
import { ref ,onMounted } from "vue";
import { useRoute } from "vue-router";
const route = useRoute();

const items = ref([]);

onMounted(() => {
  items.value = (route.matched || []).filter(i=>i.meta && i.meta.title);
});
</script>
