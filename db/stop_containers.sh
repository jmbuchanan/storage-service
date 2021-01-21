#!/bin/bash

docker rm $(docker stop $(docker ps -a -q --filter ancestor=local-postgres --format="{{.ID}}"))