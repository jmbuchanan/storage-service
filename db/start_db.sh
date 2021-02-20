#!/bin/bash

IMAGE_NAME=local-postgres
containerId=$(docker ps -a -q --filter ancestor=$IMAGE_NAME --format="{{.ID}}" 2> /dev/null)
imageId=$(docker images -q $IMAGE_NAME 2> /dev/null)

#stop running local-postgres containers
if [ $containerId != "" ]; then
  docker stop $containerId > /dev/null
fi

#rebuild if -r flag included
if [ "$1" = "-r" ]; then
  docker image rm $imageId
  docker build -t $IMAGE_NAME .
fi

#build image if doesn't exist
if [[ $imageId == "" ]]; then
  docker build -t $IMAGE_NAME .
fi

#run in detached mode with auto-clean on exit
docker run \
	-d \
	--rm \
	-e POSTGRES_DB=storage_site \
	-e POSTGRES_USER=${POSTGRES_USER} \
	-e POSTGRES_PASSWORD=${POSTGRES_PASSWORD} \
	-p 5433:5432 \
	$IMAGE_NAME
	
