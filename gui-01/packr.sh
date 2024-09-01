#! /usr/bin/env bash
set -uvx
set -e
cwd=`pwd`
name=PROGRAM
gradle shadowJar
ls -ltr build/libs
rm -rf win64.tmp
java -jar ./packr-all-4.0.0.jar \
     --platform windows64 \
     --jdk $JAVA_HOME.zip \
     --useZgcIfSupportedOs \
     --executable $name \
     --classpath build/libs/noname-all.jar \
     --mainclass app.Main \
     --output win64.tmp

#     --vmargs "Dfile.encoding=MS932" \
