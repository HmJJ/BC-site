#!/bin/sh
cd /opt/gopath/src/github.com/hyperledger

if [ -d "/fabric/" ];then
	rm -rf fabric
fi

if [ -d "/opt/gopath/src/github.com/hyperledger/fabric-release-1.1/" ];then
	rm -rf fabric-release-1.1
fi

if [ ! -f "release-1.1.zip" ];then
	wget https://github.com/hyperledger/fabric/archive/release-1.1.zip
fi

unzip release-1.1.zip 
mv fabric-release-1.1 fabric

