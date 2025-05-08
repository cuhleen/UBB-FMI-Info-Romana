#!/bin/bash

# NU MERGE CA LUMEA

inputFile=$1
emails=""

while read user; do
	emails+="${user}@scs.ubbcluj.ro,"
done < "$inputFile"

emails=${emails%,}

echo "$emails"
