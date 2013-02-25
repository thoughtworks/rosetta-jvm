#!/bin/sh

set -e

cd service/build/distributions
travis-artifacts upload --path service.tar --target-path $TRAVIS_COMMIT

cd -

cd deployer/build/distributions
travis-artifacts upload --path deployer.tar --target-path $TRAVIS_COMMIT