#!/bin/bash
# Setup the directory to match the workspace hierarchy 
# to support importing dependent github projects
# that are referenced by the `bazel` WORKSPACE file.

echo "Restructuring directory to match hierarchical workspace"
mkdir -p /persistent_deploy/

ls -la
shopt -s extglob dotglob
mv !(scripts) /persistent_deploy
shopt -u dotglob
ls -la /persistent_deploy
