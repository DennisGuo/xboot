import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import path from 'path'
import tailwindcss from '@tailwindcss/vite'
import Components from 'unplugin-vue-components/vite'
import {
  AntDesignVueResolver,
} from 'unplugin-vue-components/resolvers'
import dayjs from 'dayjs'

// https://vitejs.dev/config/
export default defineConfig({
  base: "/",
  resolve: {
    alias: {
      vue: "vue/dist/vue.esm-bundler.js",
      '@': path.resolve(__dirname,'src'),
    }
  },
  plugins: [
    Components({ 
      resolvers:[
        AntDesignVueResolver({
          importStyle: false,
        })
      ]
     }),
    tailwindcss(),
    vue()
  ],
  server:{
    proxy:{      
      '/api':{
        target:'http://localhost:18808',
        changeOrigin:true,
        rewrite: (path) => path.replace(/^\/api/, ''),
      }
    }
  },
  define: {
    // 定义环境变量
    'import.meta.env.APP_VERSION': JSON.stringify((() => {
      // 获取构建时间
      const dateStr = dayjs().format('YYYYMMDDHHmm')     
      const baseVersion = '1.0.0' // 你可以从 package.json 动态读取
      return `${baseVersion}-${dateStr}`
    })())
  }
})
