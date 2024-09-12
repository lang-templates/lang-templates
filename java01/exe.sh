#! /usr/bin/env bash
set -uvx
set -e
cwd=`pwd`
name=PROGRAM
gradle shadowJar
ls -ltr build/libs
#cp -rp build/libs/noname-all.jar ./myapp.jar
rm -rf win64.tmp
rm -rf tmp.dist
mkdir tmp.dist
exewrap64 \
  -g \
  -o tmp.dist/$name.exe \
  -i duke256.ico \
  -t 17 \
  -v 1.0.0.0 -V 1.0.0.0 \
  -j build/libs/noname-all.jar
cp -rp ~/cmd/java/jre-min tmp.dist/jre-min
