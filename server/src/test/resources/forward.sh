#!/usr/bin/env bash
set -euo pipefail

if [ $# -ne 2 ]; then
    echo "Usage: $0 <CS username> <any host on cs network>"
    echo
    echo "Example: $0 njodell herring.cs.colostate.edu"
    exit 1;
fi

USER=$1
HOST=$2

ssh -L 3306:faure.cs.colostate.edu:3306 \
    $1@$2 \
    'echo Proxied!; cat > /dev/null'
