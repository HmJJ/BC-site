#!/bin/sh
if [ ! -f "/opt/gopath/src/github.com/hyperledger/fabric/release/linux-amd64/bin/cryptogen" ]; then
	source /opt/gopath/src/github.com/hyperledger/fabric/examples/e2e_cli/download-dockerimages.sh -c x86_64-1.1.0 -f x86_64-1.1.0
    cd /opt/gopath/src/github.com/hyperledger/fabric/scripts
	rm -rf bin/
	rm -rf config/
	chmod +x bootstrap.sh
	./bootstrap.sh
	
	if [ ! -d "/opt/gopath/src/github.com/hyperledger/fabric/release/linux-amd64/" ];then
		mkdir -p /opt/gopath/src/github.com/hyperledger/fabric/release/linux-amd64
	fi
	
	cp bin ../release/linux-amd64 -r
else
	echo "bin is exists"
fi

docker stop $(docker ps -aq)
docker rm $(docker ps -aq)
docker rmi --force $(docker images | grep example.com | awk '{print $3}')
cd /opt/gopath/src/github.com/hyperledger/fabric/examples/e2e_cli

if [ -d "/channel-artifacts/" ];then
	rm -rf channel-artifacts
fi

if [ -d "/crypto-config/" ];then
	rm -rf channel-artifacts
fi

if [ -f "/opt/gopath/src/github.com/hyperledger/fabric/examples/e2e_cli/base/peer-base.yaml" ];then
	sed -i "s/e2e_default/e2e_cli_default/g" /opt/gopath/src/github.com/hyperledger/fabric/examples/e2e_cli/base/peer-base.yaml
	cat /opt/gopath/src/github.com/hyperledger/fabric/examples/e2e_cli/base/peer-base.yaml
else
	echo "peer-base.yaml not exists"
fi

chmod +x network_setup.sh

if [ ! -d "/opt/gopath/src/github.com/hyperledger/fabric/examples/e2e_cli/channel-artifacts/" ];then
	mkdir -p /opt/gopath/src/github.com/hyperledger/fabric/examples/e2e_cli/channel-artifacts
fi

./network_setup.sh up
