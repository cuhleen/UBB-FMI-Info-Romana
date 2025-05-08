#!/bin/bash

fisierInput="who.fake"
fisierCautare="ps.fake"
mapfile -t persoane < <(awk '{print $1}' who.fake)
frecventa=()
for((i=0;i<${#persoane[@]};i++)); do
        frecventa[i]=0
done

mapfile -t procese< <(awk '{print $1}' ps.fake)
for((i=0;i<${#persoane[@]};i++)); do
        for proces in "${procese[@]}"; do
                if [[ "${persoane[i]}" == "$proces" ]]; then
                        ((frecventa[i]++))
                fi
        done
done
for ((i=0; i<${#persoane[@]}; i++)); do
    echo "${persoane[i]} appears ${frecventa[i]} times"
done
~
