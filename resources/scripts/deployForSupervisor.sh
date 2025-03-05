#!/bin/bash
set -e
component=$1.jar
base_path=$2
pwd
if [ -e pom.xml ]; then
  echo "The $1 is a maven project"
  cd target
elif [ -e build.gradle ]; then
  echo "The $1 is a gradle project"
  cd build/libs
else
 echo "$1 is unsupported build type"
  exit 1;
fi
file=$(find . -type f -name "$1*.jar")
if [ -n "$file" ]; then
  echo "Moving $component to tmp"
  mv *.jar /tmp/$component
  echo "Go to tmp"
  cd /tmp
  echo "Change owner for $component"
  sudo chown kyc_user:execution $component
  echo "Change rights for $component"
  sudo chmod 750 $component
  echo "Moving $1 to path"
  sudo mv $component $base_path/$1/$component
  echo "Stoping supervisor for $1"
  supervisorctl stop $1:*
  sleep 8
  echo "Starting supervisor for $1"
  supervisorctl start $1:*
  sleep 8
  status=$(supervisorctl status $1:* 2>&1)
  echo "The status is $status"
  if [[ $status == *RUNNING* ]]; then
    echo "Finish deploy for $1"
  else
    echo "It could not deploy $1"
    exit 1
  fi 
else
   echo 'File does not exists'
   exit 1
fi
