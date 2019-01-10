#!/bin/sh
sudo yum install -y curl
sudo curl -L https://github.com/docker/compose/releases/download/1.23.1/docker-compose-`uname -s`-`uname -m` > /usr/local/bin/docker-compose
sudo chmod +x /usr/local/bin/docker-compose
sudo cp /usr/local/bin/docker-compose /usr/bin
docker-compose --version
