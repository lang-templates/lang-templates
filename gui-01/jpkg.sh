#! /usr/bin/env bash
set -uvx
set -e
cwd=`pwd`
gradle shadowJar
ls -ltr build/libs
cp -rp build/libs/noname-all.jar ./myapp.jar
rm -rf win64.tmp
rm -rf tmp.dist
mkdir tmp.dist
#jpackage --name MyApplication --input build/libs --main-jar noname-all.jar --add-modules java.base,java.desktop --win-shortcut --dest build/product
jpackage --type exe --name MyApplication --input build/libs --main-jar noname-all.jar --add-modules java.base,java.desktop --dest build/product
