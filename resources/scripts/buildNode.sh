#!/bin/bash

cache=$1
service=$2
skip_test=$3

set -e
pwd

MD5_SUM_PACKAGE_JSON=($(md5sum package.json))
echo "MD5 package.json is ${MD5_SUM_PACKAGE_JSON}"
CACHE_FOLDER=/var/cache/jenkins/npm/${MD5_SUM_PACKAGE_JSON}
echo "Cache folder is ${CACHE_FOLDER}"


if [ $cache = true]; then
 
  echo 'Using previous cache installation'
  if [ -d ${CACHE_FOLDER} ]; then
     cp -r ${CACHE_FOLDER}/node_modules .
  fi

  if ! [ -d ${CACHE_FOLDER} ]; then
   
   echo 'The cache folder does not exist, then install again'
   npm install --production --no-audit

   mkdir -p ${CACHE_FOLDER}
   echo "Copy dependencies to cache folder in ${CACHE_FOLDER}"
   cp -r node_modules ${CACHE_FOLDER}/node_modules
  
  fi
  
else

  echo 'Refresh or new installation'

  if [ -d ${CACHE_FOLDER} ]; then
    echo 'Remove cache folder of node_modules'
    rm -r ${CACHE_FOLDER}/node_modules
  fi

  echo 'Installing dependencies'
  npm install --no-audit

  mkdir -p ${CACHE_FOLDER}
  echo "Copy dependencies to cache folder in ${CACHE_FOLDER}"
  cp -r node_modules ${CACHE_FOLDER}/node_modules

fi

echo "Building project"
npm run build