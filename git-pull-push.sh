#!/bin/bash

git pull
git pull git@github.com:denis-volnenko/openapi-asciidoc-maven-plugin.git

git add .
git commit -m "Project updated."

git push

git push git@github.com:denis-volnenko/openapi-asciidoc-maven-plugin.git