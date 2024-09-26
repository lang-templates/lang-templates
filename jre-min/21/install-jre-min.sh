#! /usr/bin/env bash
set -uvx
set -e
cwd=`pwd`

rm -rf ~/cmd/jre*
"C:\Program Files\Zulu\zulu-21\bin\jlink.exe" \
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
java.xml.crypto,\
jdk.unsupported \
--output ~/cmd/jre-21-64bit

