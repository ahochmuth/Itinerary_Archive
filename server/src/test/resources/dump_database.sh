#!/usr/bin/env bash
set -euo pipefail

if [ $# -ne 3 ]; then
    echo "Usage: $0 <output filename> <search term 1> <search term 2>"
    echo
    echo "Example: $0 fc_or_ranch_airstrip.sql 'fort collins' 'airstrip'"
    exit 1
fi

SEARCH=$2
SEARCH2=$3
FILENAME=$1

mysqldump -u cs314-db \
    -h 127.0.0.1 \
    cs314 \
    world \
    --where="(id LIKE \"%$SEARCH%\") OR (name LIKE \"%$SEARCH%\") OR (municipality LIKE \"%$SEARCH%\") OR (id LIKE \"%$SEARCH2%\") OR (name LIKE \"%$SEARCH2%\") OR (municipality LIKE \"%$SEARCH2%\")" \
    --password=eiK5liet1uej \
    --lock-tables=false \
    > $FILENAME

echo Wrote to $FILENAME
