#!/usr/bin/env bash
set -euo pipefail

if [ $# -ne 1 ]; then
    echo "Usage: $0 <cs username>"
    echo
    echo "Example: $0 njodell"
    exit 1
fi

# CS username
user=$1

# host on CS network
host=herring.cs.colostate.edu
login=$user@$host
version=`grep version pom.xml | sed 's/[^0-9.]//g'`
status_url='http://www.cs.colostate.edu/~cs314/status/pages/current'
checkin_targets=$(curl -s $status_url | \
                  sed -n '/assignments available./,/^$/ p' | \
                  head -n -2 | \
                  tail -n -2 | \
                  head -c -1 | \
                  tr '\n' '/')

ask() {
    read -p "$1" yn
    case $yn in
        [Yy]* ) ;;
        [Nn]* ) echo "Do that."; exit;;
        * ) echo "Do that."; exit;;
    esac
}

ask "Detected version ${version} Is that correct? (y/n) "
ask "Did you run 'git pull'? (y/n) "
ask "Did you checkout the master branch? (y/n) "
read -p "Check in to what? ($checkin_targets) " target

# Run tests explicitly, because build_client.sh doesn't
./test_client.sh
./build_client.sh
./build_server.sh

temp_remote=$(ssh $login 'mktemp -d')
echo Copying to $login:$temp_remote
scp target/server-$version.jar $login:$temp_remote
ssh $login "cd $temp_remote; ~cs314/bin/checkin $target server-$version.jar"
