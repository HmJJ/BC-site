#!/bin/sh
docker stop $(docker ps -aq)
docker rm $(docker ps -aq)
docker rmi --force $(docker images | grep example.com | awk '{print $3}')
