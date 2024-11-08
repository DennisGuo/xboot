FROM dennisguo/xboot-api:base

COPY build/libs/*SNAPSHOT.jar app.jar

#RUN sed -i 's/dl-cdn.alpinelinux.org/mirrors.tuna.tsinghua.edu.cn/g' /etc/apk/repositories
#RUN apk add --no-cache freetype msttcorefonts-installer fontconfig
#RUN update-ms-fonts

ENV PG_HOST="postgis"
ENV PG_PORT=5432
ENV PG_DB="xboot"
ENV PG_USERNAME="xboot"
ENV PG_PASSWORD="xboot@1234."

ENV REDIS_HOST="redis"
ENV REDIS_PORT=6379
ENV REDIS_DB=5
ENV REDIS_PASSWORD=""

ENV SERVER_PORT=18808
ENV STORAGE_PATH="/data/storage"


EXPOSE 18808

ENTRYPOINT ["java","-jar","/app.jar"]
