#!/bin/bash

dir=$1
numarFileGasite=0

for FILE in $(find "$dir" -type f -name "*.[cC]"); do
	commandSubstitutionNrLinii=$(wc -l < $FILE)
	if [[ $commandSubstitutionNrLinii -gt 500 ]]; then
		((numarFileGasite++))
		echo "$FILE"
	fi
	if [[ $numarFileGasite -eq 2 ]]; then
		break
	fi
done
