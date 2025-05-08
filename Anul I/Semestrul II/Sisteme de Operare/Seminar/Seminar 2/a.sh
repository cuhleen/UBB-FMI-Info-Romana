#!/bin/bash

D=$1
if [ ! -d "$D" ]; then
	echo "Nu gasesc directorul \"$D\"" >&2
	exit 1
fi

N=0

for F in `find $D -type f -name "*.c"` `find $D -type f -name "*.h`; do
	if [ ! -f $F ]; then
		continue
	fi
	K=`cat $F | wc -l`
	N=`expr $N + $K`
done

echo $N

N=0
find $D -type f -name "*.c" > lista
find $D -type f -name "*.h" >> lista 
while read F; do
	if [ ! -f "$F" ]; then
		continue
	fi
	K=`cat "$F" | wc -l`
	N=`expr $N + $K`
done < lista

echo $N
