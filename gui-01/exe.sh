#! /usr/bin/env bash
set -uvx
set -e
cwd=`pwd`
name=PROGRAM
gradle shadowJar
ls -ltr build/libs
cp -rp build/libs/noname-all.jar ./myapp.jar
rm -rf win64.tmp
rm -rf tmp.dist
mkdir tmp.dist
exewrap64 -o tmp.dist/$name.exe -i duke256.ico -t 17 build/libs/noname-all.jar
#cp -rp $JAVA_HOME tmp.dist/jdk
$JAVA_HOME/bin/jlink --compress=2 --add-modules java.base,java.desktop --output tmp.dist/jre-min
#vbpack -i tmp.dist/$name.exe -o $cwd/$name.exe
