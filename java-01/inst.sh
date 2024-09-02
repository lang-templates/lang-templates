#! /usr/bin/env bash
set -uvx
set -e
cwd=`pwd`
name=PROGRAM
gradle shadowJar
ls -ltr build/libs
cp -rp build/libs/noname-all.jar ./myapp.jar
exewrap64 -o ~/cmd/java/$name.exe -i duke256.ico -t 17 build/libs/noname-all.jar
