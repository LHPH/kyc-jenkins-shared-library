#!/bin/bash
component=$1.jar
base_path=$2
pwd
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
  supervisorctl stop $1
  echo "Starting supervisor for $1"
  supervisorctl start $1
  echo "Finish deploy for $1"
else
   echo 'File does not exists'
   exit 1
fi
