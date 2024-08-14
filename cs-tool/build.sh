#! /usr/bin/env bash
set -uvx
set -e
cd "$(dirname "$0")"
cwd=`pwd`
name=tool
rm -rf bin obj
dotnet build -c Release
cd bin/x64/Release/net462
#ilmerge //wildcards //out:`cygpath -w $cwd/$name.exe` $name.exe *.dll
ilmerge //wildcards //out:`cygpath -w $HOME/cmd/$name.exe` $name.exe *.dll
