#!/bin/bash

if [[ "$(docker images -q local-postgres 2> /dev/null)" == "" ]]; then
  docker build -t local-postgres .
fi

if [[ "$(docker ps -a -q --filter ancestor=local-postgres --format="{{.ID}}" 2> /dev/null)" != "" ]]; then
  docker rm $(docker stop $(docker ps -a -q --filter ancestor=local-postgres --format="{{.ID}}"))
fi

docker run --rm -p 5432:5432 local-postgres
