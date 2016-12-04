# make new user named steam
useradd -m steam

cd /home/steam
sudo apt-get install steamcmd
sudo apt-get install lib32gccl
sudo -iu steam

mkdir ~/Steam && cd ~/Steam
curl -sqL 'https://steamcdn-a.akamaihd.net/client/installer/steamcmd_osx.tar.gz' | tar zxvf -

cd ~
./steamcmd
login gampishi
# password
force install_dir ./csgo-ds
app_update 740 validate
quit

# this is a set of scripts that does a lot for us
cd /etc/init.d/
wget https://raw.githubusercontent.com/crazy-max/csgo-server-launcher/master/csgo-server-launcher.sh -O csgo-server-launcher --no-check-certificate
chmod +x csgo-server-launcher
update-rc.d csgo-server-launcher defaults
mkdir /etc/csgo-server-launcher/
wget https://raw.githubusercontent.com/crazy-max/csgo-server-launcher/master/csgo-server-launcher.conf -O /etc/csgo-server-launcher/csgo-server-launcher.conf --no-check-certificate

# change vars in config file
# SCREEN_NAME
# USER
# IP
# PORT
# GSLT = 82EEB71BCDA52F84B796624A2A8A22B5
# DIR_STEAMCMD = ~/steamcmd
# STEAM_LOGIN = gampishi
# STEAM_PASSWORD = ********
# DIR_ROOT ~/steamcmd/csgo-ds
# DIR_GAME 
# DIR_LOGS = directory of game logs
# UPDATE_LOG
# UPDATE_RETRY
# MAXPLAYERS = 10
# TICKRATE = 60

service csgo-server-launcher start
