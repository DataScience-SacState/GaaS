#!/bin/bash
cd $INSTANCEDIR
sed -i "s/^\(server\-port\s*=\s*\).*\$/\1$PORT/" server.properies
java -jar $EXECDIR/minecraft_server.1.11.jar
