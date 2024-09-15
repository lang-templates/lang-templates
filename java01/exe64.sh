#! /usr/bin/env bash
set -uvx
set -e
cwd=`pwd`
name=PROGRAM
gradle shadowJar
ls -ltr build/libs
rm -rf tmp.dist64
mkdir tmp.dist64
exewrap \
  -A x64 \
  -g \
  -o tmp.dist64/$name.exe \
  -i duke256.ico \
  -t 17 \
  -v 1.0.0.0 -V 1.0.0.0 \
  -j build/libs/noname-all.jar
cp -rp ~/cmd/java64/jre-17-64bit tmp.dist64/jre-17-64bit
