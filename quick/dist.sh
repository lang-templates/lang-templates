#! /usr/bin/env bash
set -uvx
set -e
cd "$(dirname "$0")"
cwd=`pwd`
rm -rf main.exe
rm -rf dist
mkdir -p dist
pro64.cmd main.pro -f
cp -rp build-main.64.dynamic/release/main.exe dist/
cd dist
cp -rp `which libgcc_s_seh-1.dll` .
cp -rp `which libstdc++-6.dll` .
cp -rp `which libwinpthread-1.dll` .
windeployqt6.exe --qmldir $cwd main.exe
rm -rf vc_redist.x64.exe
cd $cwd
vbpack -o main.exe -i dist/main.exe
