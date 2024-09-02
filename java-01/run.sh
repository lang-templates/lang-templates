#! /usr/bin/env bash
gradle shadowJar
$JAVA_HOME/bin/java -cp build/libs/noname-all.jar app.Main
#$JAVA_HOME/bin/java -cp build/libs/noname-all.jar app.MainSWT
