# XBOOT 框架后端API

## 依赖

- SpringBoot3.x
- SpringSecurity
- MybatisPlus



## 数据库初始化

- 目前推荐使用 postgresql 数据库

```sql
create user xboot with password 'xboot@1234.';
create database xboot owner xboot;
grant all privileges on database xboot to xboot;
```

## 编译

```shell
# 运行
./gradlew bootRun -x test

# 打包jar 
./gradlew bootJar -x test

# 打包docker 
docker buildx build --platform=linux/amd64 -t dennisguo/xboot-api:latest . --push
```


## 其他

### 修改包名之后需要调整的内容

- MybatisConfig
> @MapperScan("cn.ghx.xboot.mapper")

- ExceptionConfig
> @ControllerAdvice(basePackages = {"cn.ghx.xboot"})

### REDIS 的链接构造

```shell

redis :// [[username :] password@] host [:port][/database]
          [?[timeout=timeout[d|h|m|s|ms|us|ns]] [&clientName=clientName]
          [&libraryName=libraryName] [&libraryVersion=libraryVersion] ]
```

## 部署方式

推荐使用 Docker 运行部署



