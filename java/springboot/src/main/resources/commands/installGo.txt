rm -rf /usr/local/src/go1.10.7.linux-amd64.tar.gz
wget -P /usr/local/src https://dl.google.com/go/go1.10.7.linux-amd64.tar.gz
rm -rf /usr/local/go
tar -xvf /usr/local/src/go1.10.7.linux-amd64.tar.gz -C /usr/local
mkdir -p /opt/gopath/src/github.com/hyperledger	
if [ "$GOROOT" = "" ]; then
    echo 'export GOROOT=/usr/local/go' >> /etc/profile
elif [ "$GOROOT" != "/usr/local/go" ]; then
    echo 'export GOROOT=/usr/local/go' >> /etc/profile
else
    echo "GOROOT has already set"
fi
if [ "$GOPATH" = "" ]; then
    echo 'export GOPATH=/opt/gopath' >> /etc/profile
elif [ "$GOPATH" != "/opt/gopath" ]; then
    echo 'export GOPATH=/opt/gopath' >> /etc/profile
else
    echo "GOPATH has already set"
fi
flag=`go version`
echo $flag
if [ "$flag" = "" ]; then
    echo 'export PATH=$PATH:$GOROOT/bin' >> /etc/profile
elif [ "$flag" != "go version go1.10.7 linux/amd64" ]; then
    echo 'export PATH=$PATH:$GOROOT/bin' >> /etc/profile
else
    echo "PATH has already set"
fi
source /etc/profile
go get -u github.com/golang/lint/golint
go install github.com/golang/lint/golint