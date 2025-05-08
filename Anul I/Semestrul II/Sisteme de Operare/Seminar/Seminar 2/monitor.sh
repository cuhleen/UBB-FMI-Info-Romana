#!/bin/bash

D=~/c_project
P=""
while true; do
	C=""
	for F in `find $D`; do
		if [ -f $F ]; then
			MCS=`ls -l $F | md5sum | sed "s/ .*//"`
			CCS=`cat $F | md5sum | sed "s/ .*//"`
		elif [ -d $F ]; then
			MCS=`ls -ld $F | md5sum | sed "s/ .*//"`
			CCS=`cat $F | md5sum | sed "s/ .*//"`		
		fi
		C="$C$MCC$CCS"
	done	
	
	if [ -n "$P" ] && [ "$P" != "$C" ]; then
		echo "`date`: s-a modificat ceva"
	fi
	P=$C
done
