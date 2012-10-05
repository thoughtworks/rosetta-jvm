#!/bin/sh
wget http://download01.thoughtworks.com/go/12.3.1/ga/go-server-12.3.1-15878.deb &&
wget http://download01.thoughtworks.com/go/12.3.1/ga/go-agent-12.3.1-15878.deb &&
wget https://raw.github.com/flexiondotorg/oab-java6/master/oab-java.sh -O oab-java6.sh &&
chmod +x oab-java6.sh &&
sudo ./oab-java6.sh &&
sudo apt-get install sun-java6-jre sun-java6-plugin sun-java6-jdk &&
sudo dpkg -i go-server-12.3.1-15878.deb &&
sudo dpkg -i go-agent-12.3.1-15878.deb &&
sudo -u go /etc/init.d/go-agent start
