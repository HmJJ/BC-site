cd /usr/local
git clone https://github.com/ethereum/go-ethereum.git
cd go-ethereum
make all
echo "export PATH=$PATH:/usr/local/go-ethereum/build/bin" >> /etc/profile
source /etc/profile
cd /usr/local/src
wget https://cmake.org/files/v3.9/cmake-3.9.2.tar.gz
tar -xzvf cmake-3.9.2.tar.gz
cd cmaker-3.9.2
./bootstrap && make && make install
systemctl enable ntpd
systemctl start ntpd
systemctl start firewalld
firewall-cmd --zone=public --add-port=8087/tcp --permanent
firewall-cmd --zone=public --add-port=30303/tcp --permanent