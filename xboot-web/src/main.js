import { createApp } from 'vue'
import App from './App.vue'
import '@/assets/common.less'
import router from  './router'
import { createPinia } from 'pinia';
import Antd from 'ant-design-vue';
import 'ant-design-vue/dist/reset.css';
import * as Icons from '@ant-design/icons-vue'
import {useGlobalStore} from '@/store/global'

const pinia = createPinia()
const app = createApp(App)

Object.keys(Icons).forEach(key=>{
  app.component(key,Icons[key])
})

app.use(Antd)
app.use(pinia)

// 加载菜单
const global = useGlobalStore()
global.loadUserRouter().then(()=>{
  render()
}).catch(()=>{
  render(true)
})

const render = (login=false)=>{
  app.use(router)
  document.body.removeChild(document.getElementById('loading'))
  app.mount('#app')
  if(login){
    router.push({ name: "login", query: { redirect: router.currentRoute.value.path } });
  }
}

