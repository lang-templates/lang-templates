#! /usr/bin/env bash
cwd=`pwd`
rm -rf bin obj
dotnet build -c Release
cd bin/x64/Release/net462
ilmerge //wildcards //out:`cygpath -w $cwd/lt.exe` lt.exe *.dll
