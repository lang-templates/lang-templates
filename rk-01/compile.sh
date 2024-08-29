#! /usr/bin/env bash
set -uvx
set -e

#raco exe -o test.exe test.rkt
raco exe --gui -o test.exe test.rkt

rcedit-x64.exe ./test.exe --set-icon                           "./mingw32.ico"

rcedit-x64.exe ./test.exe --set-version-string FileDescription "test"
rcedit-x64.exe ./test.exe --set-version-string ProductName     "test"
rcedit-x64.exe ./test.exe --set-version-string CompanyName     "JavaCommons Technologies"
rcedit-x64.exe ./test.exe --set-version-string ProductVersion  "1.0.0"
rcedit-x64.exe ./test.exe --set-version-string FileVersion     "1.0.0"
rcedit-x64.exe ./test.exe --set-version-string LegalCopyright  "Copyright JavaCommons Technologies"

rm -rf inst.tmp
raco distribute inst.tmp test.exe || true
vbpack.exe -i inst.tmp/test.exe -o ./test.exe
