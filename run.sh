#!/bin/bash

check_error() {
  if [ $1 -ne 0 ]
  then
    echo "run.sh: Build failed!"
    exit $1
  fi
}

# When deploying to production, we only send the jar
# and not run.sh. When running on travis, run.sh is not
# used. Therefore, we can assume that we're running in
# development mode. (This affects what SQL server we use.)
export CS314_ENV=development

./build_client.sh
check_error $?
./build_server.sh
check_error $?
./run_server.sh
