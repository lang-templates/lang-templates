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
#$JAVA_HOME/bin/jlink --compress=2 --add-modules java.base,java.desktop,java.prefs --output tmp.dist/jre-min
exewrap64 -o tmp.dist/$name.exe -i duke256.ico -t 17 build/libs/noname-all.jar
$JAVA_HOME/bin/jlink --compress=2 --add-modules java.base,\
java.compiler,\
java.datatransfer,\
java.desktop,\
java.instrument,\
java.logging,\
java.management,\
java.management.rmi,\
java.naming,\
java.net.http,\
java.prefs,\
java.rmi,\
java.scripting,\
java.se,\
java.security.jgss,\
java.security.sasl,\
java.smartcardio,\
java.sql,\
java.sql.rowset,\
java.transaction.xa,\
java.xml,\
java.xml.crypto \
--output tmp.dist/jre-min
