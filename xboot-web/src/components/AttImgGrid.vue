<template>
  <div class="imgs" ref="imgsRef">
    <img :src="getFile(att.id)" v-for="(att, index) in attachments" :key="index" @click="toPreview(index)"/>
  </div>
</template>

<script setup>
import { ref ,shallowRef} from 'vue'
import 'viewerjs/dist/viewer.css';
import Viewer from 'viewerjs';
import { getFile } from '@/api/att'

const props = defineProps(['attachments'])

const imgsRef = ref()
const viewer = shallowRef()

const toPreview = (index)=>{
  if(!viewer.value){
    viewer.value = new Viewer(imgsRef.value)
  }
  viewer.value.view(index)
}

</script>

<style lang="less" scoped>
.imgs {
  img {
    cursor: pointer;
    width: 100px;
    margin-top: 8px;
    margin-right: 8px;
  }
}
</style>