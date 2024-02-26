# XBOOT 框架后端API

## 依赖

- SpringBoot3.x
- SpringSecurity
- MybatisPlus


## 编译

```shell
./gradlew build -x test 
```

## 数据库初始化

- 目前推荐使用 postgresql 数据库

```sql
create user xboot with password 'xboot@1234.';
create database xboot owner xboot;
grant all privileges on database xboot to xboot;
```

## 其他


### REDIS 的链接构造

```shell

redis :// [[username :] password@] host [:port][/database]
          [?[timeout=timeout[d|h|m|s|ms|us|ns]] [&clientName=clientName]
          [&libraryName=libraryName] [&libraryVersion=libraryVersion] ]
```