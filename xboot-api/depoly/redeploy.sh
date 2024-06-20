#!/bin/bash

# harbor
base="docker.wuzg.cn/em/"
# tag
tag="latest"
#
rep=""

for service in "$@"
do
  url="$base$service:$tag"
  printf "\n镜像：$url\n"

  # echo "关闭WEB服务: docker compose stop $service"
  echo "删除W服务: docker compose rm -s -f $service"
  echo "删除镜像: docker image rm $url"
  echo "部署服务: docker compose up -d $service"

  # docker compose stop $service
  docker compose rm -s -f $service
  docker image rm $url
  docker compose up -d $service

done

printf "\n所有服务已经重新部署完成."