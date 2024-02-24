#!/bin/bash
component=$1.jar
base_path=$2

if test -f component; then
  echo "Moving $component to tmp"
  mv *.jar /tmp/$component
  echo "Go to tmp"
  cd /tmp
  echo "Change owner for $component"
  chown kyc_user:execution $component
  echo "Change rights for $component"
  chmod 750 $component
  echo "Moving $1 to path"
  mv $component $base_path/$1/$component
else
   echo 'File does not exists'
   exit 1
fi