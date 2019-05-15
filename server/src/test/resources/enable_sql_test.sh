#!/usr/bin/env bash
set -euo pipefail

# SQL tests are disabled by default, for speed and flakiness reasons
# This script enables them for travis
# You should also enable them if you're monkeying around with any SQL stuff

# Comment out any lines that match '@Ignore("integration-only")'
replacement='s_^@Ignore("integration-only")$_//@Ignore("integration-only")_'

sed -i "$replacement" server/src/test/java/com/tripco/t12/sql/TestSQLQuery.java
sed -i "$replacement" server/src/test/java/com/tripco/t12/TIP/TestTIPFind.java
