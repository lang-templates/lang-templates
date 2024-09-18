#! /usr/bin/env bash
set -uvx
set -e
var=$(java -version 2>&1 | awk -F '"' 'NR==1 {print $2}')
echo $var
