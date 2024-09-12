#! /usr/bin/env bash
set -uvx
set -e
#rm -rf tmp.native
#mkdir -p tmp.native
native-image.cmd \
--no-fallback \
-H:IncludeResources='^org/joda/time/tz/data/.+$' \
--static \
-cp ./build/libs/noname-all.jar \
-o java01 \
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
demo.WaitUntil
