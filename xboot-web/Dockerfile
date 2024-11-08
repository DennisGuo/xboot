FROM nginx

COPY dist/ /usr/share/nginx/html
COPY nginx.conf /etc/nginx/conf.d/default.conf

# 指定容器在运行过程时监听80端口
EXPOSE 80

# 指定在容器开启运行时，通过运行以下命令行指令开启nignx
CMD ["nginx", "-g", "daemon off;"]