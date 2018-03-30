#!/bin/bash

./gradlew build
if [ $? != 0 ]; then
    echo "Could not compile server"
    exit $?
fi

./gradlew test
if [ $? != 0 ]; then
    echo "Tests failed, please check test results and fix them"
    exit $?
fi

