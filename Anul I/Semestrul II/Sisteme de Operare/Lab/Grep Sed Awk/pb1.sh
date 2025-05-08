#!/bin/bash

awk '{print $1}' who.fake | sort | uniq | while read user; do
	count=$(grep "^$user[[:space:]]" ps.fake | wc -l)
	echo "$user $count"
done
