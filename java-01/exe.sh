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
exewrap64 -o tmp.dist/$name.exe -i duke256.ico -t 17 build/libs/noname-all.jar
cp -rp ~/cmd/java/jre-min tmp.dist/jre-min
