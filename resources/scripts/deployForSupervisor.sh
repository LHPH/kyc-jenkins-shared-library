#!/bin/bash

deployJavaProject(){
  
  project=$1
  component=$1.jar
  base_path=$2
  file=$(find . -type f -name "$project*.jar")
  if [ -n "$file" ]; then
    echo "Moving $component to tmp"
    mv *.jar /tmp/$component
    echo "Go to tmp"
    cd /tmp
    echo "Change owner for $component"
    sudo chown kyc_user:execution $component
    echo "Change rights for $component"
    sudo chmod 750 $component
    stopSupervisor $project
    echo "Moving $component to path $base_path/$project/$component"
    sudo mv $component $base_path/$project/$component
    deploySupervisor $project
    
  else
    echo 'File does not exists'
    exit 1
  fi
}

deployNodeProject(){

  component=$1
  base_path=$2
  echo "Checking if dist and node_modules exists"
  if [ -d dist ] && [ -d node_modules ]; then

    echo "Exists both directories"
    mv dist /tmp/
    echo "Moved dist to tmp"
    mv node_modules /tmp/
    echo "Moved node_modules to tmp"
    echo "Go to tmp"
    cd /tmp
    stopSupervisor $component
    
    echo "Change owner for dist of $component"
    sudo chown -R kyc_user:execution dist
    echo "Change rights for dist of $component"
    sudo chmod -R 750 dist
    
    echo "Change owner for node_modules of $component"
    sudo chown -R kyc_user:execution node_modules
    echo "Change rights for node_modules of $component"
    sudo chmod -R 750 node_modules

    if [ -d $base_path/$component/dist ]; then

      echo "Removing dist of $base_path/$component/dist"
      sudo rm -r $base_path/$component/dist

    fi
   
    if [ -d  $base_path/$component/node_modules ]; then

      echo "Removing node_modules of $base_path/$component/node_modules"
      sudo rm -r $base_path/$component/node_modules
    fi
   
    echo "Moving dist to $base_path/$component/dist"
    sudo mv dist $base_path/$component/
    echo "Moving node_modules to $base_path/$component/node_modules"
    sudo mv node_modules $base_path/$component/

    deploySupervisor $component
  else
    echo "Do not exists dist and/or node_modules"
    exit 1
  fi

}

stopSupervisor(){

  project=$1
  echo "Stoping supervisor for $project"
  supervisorctl stop $project:*
  sleep 8
}

deploySupervisor(){

  project=$1
  echo "Starting supervisor for $project"
  supervisorctl start $project:*
  sleep 8
  status=$(supervisorctl status $project:* 2>&1)
  echo "The status is $status"
  if [[ $status == *RUNNING* ]]; then
    echo "Finish deploy for $project"
  else
    echo "It could not deploy $project"
    exit 1
  fi 
}

set -e
project=$1
base_path=$2
pwd
if [ -e pom.xml ]; then
  echo "The $project is a maven project"
  cd target
  deployJavaProject $project $base_path
elif [ -e build.gradle ]; then
  echo "The $project is a gradle project"
  cd build/libs
  deployJavaProject $project $base_path
elif [ -e package.json ]; then
  echo "The $project is a node project"
  deployNodeProject $project $base_path
else
  echo "$project is unsupported due build type"
  exit 1;
fi
