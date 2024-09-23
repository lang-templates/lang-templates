#! /usr/bin/env bash
set -uvx
set -e
gradle clean
gradle war
cp ./build/libs/gretty01.war C:/xampp/tomcat/webapps/
start http://localhost:8080/gretty01/
