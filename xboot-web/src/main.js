import { createApp } from 'vue'
import App from './App.vue'
import '@/assets/common.less'
import router from  './router'
import { createPinia } from 'pinia';
import Antd from 'ant-design-vue';
import 'ant-design-vue/dist/reset.css';
import * as Icons from '@ant-design/icons-vue'

const pinia = createPinia()
const app = createApp(App)

Object.keys(Icons).forEach(key=>{
  app.component(key,Icons[key])
})

app.use(Antd)
app.use(pinia)
app.use(router)
app.mount('#app')
