#!/usr/bin/env bash
set -eo pipefail


#TODO - add rest of steps needed to build the package

docker run --rm -v "$PWD:/conjurinc/api-java" -w /conjurinc/api-java maven:3-jdk-8 /bin/bash -c \
"mvn -X -e clean package -Dmaven.test.skip=true"