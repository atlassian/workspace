#!/bin/bash
set -ev
export ARTIFACTS_TARGET_PATHS="artifacts/$TRAVIS_REPO_SLUG/$TRAVIS_BUILD_NUMBER/$TRAVIS_JOB_NUMBER:artifacts/$TRAVIS_REPO_SLUG/$TRAVIS_COMMIT"
export ARTIFACTS_BUCKET="jpt-travis-ci-pipeline"
export ARTIFACTS_PATHS="build/reports/:build/test-results/"
export ARTIFACTS_DEST=artifacts
curl -sL https://raw.githubusercontent.com/travis-ci/artifacts/v0.7.9/install | bash
./artifacts --debug upload
