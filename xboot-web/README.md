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

## 权限说明

1. 在菜单管理中，添加模块，菜单，按钮，设置每一项的 编码
2. 在角色权限管理中，为角色赋予菜单按钮权限
3. 在界面开发中使用 `v-auth` 指令控制按钮权限

示例：

```html
<a @click="handleEdit(record)" v-auth="`user-edit`">编辑</a>
```

其中 `v-auth="'user-edit'"` 控制只有赋予了 `user-edit` 这个编码的按钮权限的用户才会显示

## 打包编译

```shell
# 安装依赖
yarn 

# 启动测试服务
yarn dev

# 打包
yarn build

# docker 
docker buildx build --platform=linux/linux/amd64 -t dennisguo/xboot-web:latest . --push

```