#! /usr/bin/env bash
set -uvx
set -e
cwd=`pwd`

mkdir -p ~/cmd/java64
rm -rf ~/cmd/java64/jre*
"C:\Program Files\Zulu\zulu-17\bin\jlink.exe" \
--compress=2 \
--add-modules java.base,\
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
--output ~/cmd/java64/jre-17-64bit

mkdir -p ~/cmd/java32
rm -rf ~/cmd/java32/jre*
"C:\Program Files (x86)\Zulu\zulu-17\bin\jlink.exe" \
--compress=2 \
--add-modules java.base,\
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
--output ~/cmd/java32/jre-17-32bit
