#!/usr/bin/env bash
set -euo pipefail

FILENAME=country_continent_region.sql

mysqldump -u cs314-db \
    -h 127.0.0.1 \
    cs314 \
    country continent region \
    --password=eiK5liet1uej \
    --lock-tables=false \
    > $FILENAME

echo Wrote to $FILENAME
