#!/bin/bash

set -x #print bash commands as they are executed

kubectl delete -f $HOME/k8s/storage-service/k8s/deployment.yml

mvn package -f $HOME/k8s/storage-service/pom.xml

docker buildx build \
    --no-cache \
    --output=type=registry,registry.insecure=true  \
    --platform linux/arm64/v8,linux/amd64 \
    -t localhost:5000/homelab/storage-service $HOME/k8s/storage-service

kubectl apply -f $HOME/k8s/storage-service/k8s/deployment.yml
kubectl apply -f $HOME/k8s/storage-service/k8s/service.yml
