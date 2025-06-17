import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import path from 'path'
import tailwindcss from '@tailwindcss/vite'
import Components from 'unplugin-vue-components/vite'
import {
  AntDesignVueResolver,
} from 'unplugin-vue-components/resolvers'

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
  }
})
