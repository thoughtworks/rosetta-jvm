#!/bin/sh

set -e

cd service/build/distributions
travis-artifacts upload --path service.tar --target-path $TRAVIS_COMMIT

cd -

cd deploy/build/distributions
travis-artifacts upload --path deploy.tar --target-path $TRAVIS_COMMIT