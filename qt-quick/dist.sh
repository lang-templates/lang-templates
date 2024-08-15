#! /usr/bin/env bash
set -uvx
set -e
cd "$(dirname "$0")"
cwd=`pwd`
name=PROGRAM
rm -rf $name.exe
rm -rf tmp.dist
mkdir -p tmp.dist
pro64.cmd main.pro -f
cp -rp build-main.64.dynamic/release/$name.exe tmp.dist/
cd tmp.dist
cp -rp `which libgcc_s_seh-1.dll` .
cp -rp `which libstdc++-6.dll` .
cp -rp `which libwinpthread-1.dll` .
windeployqt6.exe --qmldir $cwd $name.exe
rm -rf vc_redist.x64.exe
cd $cwd
vbpack -o $name.exe -i tmp.dist/$name.exe
