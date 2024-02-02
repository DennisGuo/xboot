<template>
  <a-layout class="login">
    <a-layout-content class="posr">
      <div class="canvas-bg" ref="bgRef"></div>
      <a-flex horizontal class="login-box">
        <div class="cover" :style="`background-image: url(./images/login.png)`"></div>
        <div class="form">
          <h2 class="h2">{{ global.site.title }}</h2>
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
              <a-button type="primary" html-type="submit">登录</a-button>
            </a-form-item>
          </a-form>
          <div class="copyright">{{ date }} &copy; {{ global.site.copyright }}</div>
        </div>
      </a-flex>
    </a-layout-content>
  </a-layout>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { useGlobalStore } from '@/store/global'
import dayjs from 'dayjs'
import p5 from 'p5'
import Clouds from 'vanta/dist/vanta.clouds.min'
import * as THREE from 'three'
const global = useGlobalStore()
const form = reactive({})
const bgRef = ref(null)
const date = dayjs().format('YYYY')

onMounted(() => {
  initBg()
})
const initBg = () => {
  Clouds({
    el: bgRef.value,
    THREE,
    p5,
    touchControls: true,
    mouseControls: true,
    color: '#b7e6ee',
    backgroundColor: '#071c3b',
  })
}
const handleSubmit = () => { }
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
      .copyright{
        opacity: .5;
      }
    }

  }
}</style>