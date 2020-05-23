#!/bin/bash
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null 2>&1 && pwd )"
pushd $DIR
docker-compose -f docker-compose.dev.yaml up -d --build
docker-compose -f docker-compose.dev.yaml logs -f &
read -p "Press enter to stop the environment"
kill %1
docker-compose -f docker-compose.dev.yaml down
popd