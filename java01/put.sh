#! /usr/bin/env bash
set -uvx
set -e
gradle clean
gradle build
git add .
git-put.cmd
cd ~/
git-put.cmd
