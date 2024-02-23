#!/bin/bash

rm -rf dist.zip
rm -rf dist
yarn build
7z a  -r dist.zip dist/*