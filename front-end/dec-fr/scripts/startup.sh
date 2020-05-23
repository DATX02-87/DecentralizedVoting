#!/bin/bash

npm run-script build

if [ -z "$INIT" ]
then
    echo "Not running init scripts"
else
    echo "Waiting 20 seconds until API has come up"
    sleep 20
    node src/init-votes.js
fi
