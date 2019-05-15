#!/usr/bin/env bash

if [ $# -ne 1 ] ; then
    echo Usage: 
    echo "    $0 enforce"
    echo or
    echo "    $0 allow"
    status=$(git config branch.master.pushremote)
    echo -n "Current status: "
    if [ "$status" == "no_push_to_master" ]; then
        echo enforce
    elif [ "$status" == "origin" ]; then
        echo allow
    else
        echo "Unknown status $status"
    fi
    exit
fi


if [ "$1" == enforce ] ; then
    git config branch.master.pushremote no_push_to_master
    exit
elif [ "$1" == allow ] ; then
    git config branch.master.pushremote origin
    exit
else
    echo "Unknown arg $1"
    exit
fi
