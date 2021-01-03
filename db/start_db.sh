#!/bin/bash

if [[ "$(docker images -q local-postgres 2> /dev/null)" == "" ]]; then
  docker build -t local-postgres .
fi

docker run --rm -p 5432:5432 local-postgres
