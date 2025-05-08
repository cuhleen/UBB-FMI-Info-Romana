#!/bin/bash


while true; do
	for danger in "$@"; do
		ps -ef | grep "$danger" | grep -v grep | grep -v "$0" | while read -r user pid rest; do
			echo "Dangerous program '$danger' (PID: '$pid') detected! Killing it..."
			kill "$pid"
		done
	done
	sleep 1
done

