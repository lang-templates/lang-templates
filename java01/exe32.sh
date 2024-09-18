#! /usr/bin/env bash
set -uvx
set -e
cwd=`pwd`
name=PROGRAM
gradle shadowJar
ls -ltr build/libs
rm -rf tmp.dist32
mkdir tmp.dist32
exewrap \
  -A x86 \
  -g \
  -o tmp.dist32/$name.exe \
  -i duke256.ico \
  -t 11 \
  -v 1.0.0.0 -V 1.0.0.0 \
  -j build/libs/noname-all.jar
cp -rp ~/cmd/java32/jre-11-32bit tmp.dist32/jre-11-32bit
