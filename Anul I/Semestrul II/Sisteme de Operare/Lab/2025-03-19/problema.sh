#!/bin/bash

# Numele fișierului
echo -n "Introdu numele fișierului: "
read file

# Verificăm dacă fișierul există
if [[ ! -f "$file" ]]; then
	echo "Fișierul nu există!"
	exit 1
fi

# Calculăm media pentru coloana CPU_Usage
cpuSum=0
cpuCount=0

while read -r line; do
	cpu=$(echo "$line" | awk '{print $9}' | tr -d '%')
	if [[ "$cpu" =~ ^[0-9]+$ ]]; then
		cpuSum=$(($cpuSum + cpu))
		cpuCount=$((cpuCount + 1))
	fi
done < <(tail -n +2 "$file")

if [[ $cpuCount -gt 0 ]]; then
	cpuAvg=$(echo "scale=2; $cpuSum / $cpuCount" | bc)
	echo "Media CPU Usage: $cpuAvg%"
else
	echo "Nu s-au găsit valori pentru CPU Usage"
fi

# Numărăm câte IP-uri încep cu 120 și se termină cu 10
ipCount=$(grep -E '^120\..*\.10$' "$file" |wc -l)
echo "Numărul de IP-uri care încep cu 120 și se termină cu 10: $ipCount"

# Numărăm apariția fiecărui nume de imagine
awk 'NR>1 {sub(/:.*/, "", $2); print $2}' "$file" | sort | uniq -c | sort -nr
