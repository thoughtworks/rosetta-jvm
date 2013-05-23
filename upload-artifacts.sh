#!/bin/sh

set -e

cd service-package/build/distributions
ls rosetta-jvm*.deb | xargs travis-artifacts upload --target-path $TRAVIS_COMMIT --path

cd -

cd deployer-package/build/distributions
ls deployer*.deb | xargs travis-artifacts upload --target-path $TRAVIS_COMMIT --path