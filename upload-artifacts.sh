#!/bin/sh

set -e

cd service/build/distributions
ls rosetta-jvm*.tar | xargs travis-artifacts upload --target-path $TRAVIS_COMMIT --path

cd -

cd deployer/build/distributions
ls deployer*.tar | xargs travis-artifacts upload --target-path $TRAVIS_COMMIT --path