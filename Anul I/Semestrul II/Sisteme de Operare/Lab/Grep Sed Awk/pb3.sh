#!/bin/bash

dir=$1

for FILE in $(find "$dir" -type f -name "*.log"); do
	touch "$FILE".sorted
	sort "$FILE" > "$FILE".sorted
	mv "$FILE".sorted "$FILE"
	#rm "$FILE".sorted
done


