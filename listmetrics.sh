#!/bin/bash
if [[ -z $* ]] ; then
    echo 'Supply the name of one of the example classes as an argument.'
    echo 'If there are arguments to the class, put them in quotes after the class name.'
    exit 1
fi
export CLASSPATH=target/AWSSDKCloudWatch-1.0-SNAPSHOT.jar
export className=$1
echo "## Running $className..."
shift
echo "## arguments $@..."
mvn exec:java -Dexec.mainClass="listmetrics.$className" -Dexec.args="$@" -Dexec.cleanupDaemonThreads=false
