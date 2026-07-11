#!/bin/bash

set -e
FILE_PATH=$1
KEYSTORE_PATH=$2

jarsigner -verbose \
 -sigalg SHA256withRSA \
 -digestalg SHA-256 \
 -keystore $KEYSTORE_PATH \
 -storepass $KEYSTORE_PASSWORD \
 -key-pass $KEY_PASSWORD \
 $FILE_PATH \
 $KEY_ALIAS

jarsigner -verify \
 -verbose \
 $FILE_PATH