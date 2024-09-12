#! /usr/bin/env bash
set -uvx
set -e
gradle shadowJar
ls -ltr build/libs
rm -rf linux.tmp
/home/user/jdk/bin/java -jar ./packr-all-4.0.0.jar \
     --platform linux64 \
     --jdk ../zulu17.44.53-ca-jdk17.0.8.1-linux_x64.tar.gz \
     --useZgcIfSupportedOs \
     --executable myapp \
     --classpath build/libs/noname-all.jar \
     --mainclass app.Main \
     --output linux.tmp
chmod 755 linux.tmp/myapp

