#!/bin/bash

set -e

INPUT_PATH=$1
OUTPUT_PATH=$2
KEYSTORE_PATH=$3

$ANDROID_HOME/build-tools/35.0.0/apksigner sign \
--ks $KEYSTORE_PATH \
--ks-pass pass:$KEYSTORE_PASSWORD \
--ks-key-alias $KEY_ALIAS \
--key-pass pass:$KEY_PASSWORD \
--out $OUTPUT_PATH \
$INPUT_PATH

$ANDROID_HOME/build-tools/35.0.0/apksigner verify \
--verbose \
$OUTPUT_PATH