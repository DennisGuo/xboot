import { createApp } from 'vue'
import App from './App.vue'
import './style.css'
import '@/assets/common.less'
import router from  './router'
import { createPinia } from 'pinia';
import Antd from 'ant-design-vue';
import 'ant-design-vue/dist/reset.css';
import * as Icons from '@ant-design/icons-vue'
import {useGlobalStore} from '@/store/global'
import Auth from './common/auth-directive';

const pinia = createPinia()
const app = createApp(App)

Object.keys(Icons).forEach(key=>{
  app.component(key,Icons[key])
})

app.use(Antd)
app.use(pinia)

app.directive('auth',Auth)

// 加载菜单
const global = useGlobalStore()
global.loadUserRouter().then(()=>{
  render()
}).catch((e)=>{
  console.log('加载菜单失败',e)
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

