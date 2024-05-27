# XBOOT-WEB


## 依赖

- vite
- vue3
- ant-design-vue
- less
- pinia
- vue-router

## 说明

- 默认登录用户密码：admin/qwer1234

## 打包编译

```shell
# 安装依赖
yarn 

# 启动测试服务
yarn dev

# 打包
yarn build

# docker 
docker buildx build --platform=linux/arm64,linux/amd64 -t dennisguo/xboot-web:latest . --push

```