<template>
  <a-layout class="login">
    <a-layout-content class="posr">
      <div class="canvas-bg" ref="bgRef"></div>
      <a-flex horizontal class="login-box">
        <div class="cover" :style="`background-image: url(./images/login.png)`"></div>
        <div class="form">
          <h2 class="h2">{{ title }}</h2>
          <p class="slogan">欢迎登录</p>
          <a-form layout="vertical" :model="form" @submit="handleSubmit">
            <a-form-item>
              <a-input v-model:value="form.username" placeholder="请输入用户名">
                <template #prefix>
                  <UserOutlined />
                </template>
              </a-input>
            </a-form-item>
            <a-form-item>
              <a-input v-model:value="form.password" type="password" placeholder="请输入密码">
                <template #prefix>
                  <LockOutlined />
                </template>
              </a-input>
            </a-form-item>
            <a-form-item>
              <a-flex gap="small">
                <a-input v-model:value="form.code" placeholder="验证码" style="flex:1;" />
                <a @click="loadCaptcha" v-if="captcha"><img :src="captcha.image" style="height: 32px;" /></a>
              </a-flex>
            </a-form-item>
            <a-form-item>
              <a-button type="primary" html-type="submit">登录</a-button>
            </a-form-item>
          </a-form>
          <div class="copyright">{{ date }} &copy; {{ copyright }}</div>
        </div>
      </a-flex>
    </a-layout-content>
  </a-layout>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { useRouter ,useRoute } from 'vue-router'
import { useGlobalStore } from '@/store/global'
import dayjs from 'dayjs'
import { getCaptcha, login } from '@/api/user'
import { message } from 'ant-design-vue'
import { md5 } from 'js-md5';
import { COPYRIGHT, COPYRIGHT_DEFAULT, SYS_TITLE, SYS_TITLE_DEFAULT } from '@/common/const'

const global = useGlobalStore()
const form = reactive({})
const bgRef = ref(null)
const date = dayjs().format('YYYY')
const router = useRouter()
const route = useRoute()
const captcha = ref(null)
const title = ref('')
const copyright = ref('')

onMounted(() => {
  // initBg()
  loadCaptcha()

  global.getSetting([SYS_TITLE,COPYRIGHT]).then(arr=>{
    if(arr.length == 2){
      title.value = arr.filter(i=>i.code == SYS_TITLE)[0].content
      copyright.value = arr.filter(i=>i.code == COPYRIGHT)[0].content 
    }else{
      title.value = SYS_TITLE_DEFAULT
      copyright.value = COPYRIGHT_DEFAULT
    }
  })
})

const loadCaptcha = async () => {
  const res = await getCaptcha()
  if (res && res.success) {
    captcha.value = res.data;
  }
}
const handleSubmit = async () => {
  const { username, password, code } = form
  if (!username) {
    message.error('请输入用户名')
    return
  }
  if (!password) {
    message.error('请输入密码')
    return
  }
  if (!code) {
    message.error('请输入验证码')
    return
  }
  const pay = {
    username,
    password: md5(password),
    code,
    random: captcha.value.random
  }
  const res = await login(pay)
  if (res && res.data) {
    global.saveToken(res.data)
    // 根据登录用户权限动态构建路由
    const to = await global.loadUserRouter()
    if(route.query.redirect){
      router.push({path: route.query.redirect})
    }else{
      router.push(to)
    }
  } else{
    loadCaptcha()
  }
  
}

</script>

<style lang="less" scoped>
.login {
  height: 100vh;

  .canvas-bg {
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background-image: url('/images/login-bg.jpg');
    background-size: cover;
    background-position: left bottom;
    background-repeat: no-repeat;
  }

  .login-box {
    position: absolute;
    width: 800px;
    top: 50%;
    left: calc(50% - 400px);
    transform: translateY(-50%);
    box-shadow: 0 0 25px rgba(0, 0, 0, .2);
    background-color: #fff;

    .cover {
      width: 600px;
      aspect-ratio: 1.618;
      background-size: cover;
      background-position: center;
    }

    .form {
      padding: 24px;

      .h2 {
        margin-bottom: 12px;
      }

      .slogan {
        margin-bottom: 36px;
      }

      .copyright {
        opacity: .5;
      }
    }

  }
}
</style>