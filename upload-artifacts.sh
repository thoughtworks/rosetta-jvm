#!/bin/sh

set -e

cd service/build/distributions
travis-artifacts upload --path service.zip --target-path $TRAVIS_COMMIT