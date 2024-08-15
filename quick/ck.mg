#! /usr/bin/env bash
set -uvx
set -e
cd dist
Dependencies -modules -depth 3 main.exe
