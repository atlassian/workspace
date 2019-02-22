#!/bin/bash
set -ev
export ARTIFACTS_BUCKET="jpt-travis-ci-pipeline"
export ARTIFACTS_PATHS="build/reports/:build/test-results/"
export ARTIFACTS_DEST=artifacts
curl -sL https://raw.githubusercontent.com/travis-ci/artifacts/v0.7.9/install | bash
./artifacts upload
