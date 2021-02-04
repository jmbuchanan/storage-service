#!/bin/bash

if [[ "$(docker images -q local-postgres 2> /dev/null)" == "" ]]; then
  docker build -t local-postgres .
fi

if [[ "$(docker ps -a -q --filter ancestor=local-postgres --format="{{.ID}}" 2> /dev/null)" != "" ]]; then
  docker rm $(docker stop $(docker ps -a -q --filter ancestor=local-postgres --format="{{.ID}}"))
fi

docker run \
	-d \
	--rm \
	-e POSTGRES_DB=storage_site \
	-e POSTGRES_USER=${POSTGRES_USER} \
	-e POSTGRES_PASSWORD=${POSTGRES_PASSWORD} \
	-p 5433:5432 \
	local-postgres
	
