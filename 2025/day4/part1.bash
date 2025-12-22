#!/bin/bash

path_input="input.txt"
declare -A map
offsets_r=(-1 -1 -1  0 0  1 1 1)
offsets_c=(-1  0  1 -1 1 -1 0 1)

rows=0
cols=0
sum=0

read_file() {
    while IFS= read -r line || [ -n "$line" ]; do
        length=${#line}
        (( length > cols )) && cols=$length
        for ((i=0; i<length; i++)); do
            map["$rows,$i"]="${line:i:1}"
        done
        ((rows++))
    done < "$path_input"
}

check_and_sum() {
    local i=$1
    local j=$2
    local val="${map["$i,$j"]}"

    if [[ "$val" != "@" ]]; then 
        return
    fi

    local cpt=0
    local nr nc neighbor_val
    
    # parse each neighbors
    for ((k=0; k<8; k++)); do
        nr=$((i + offsets_r[k]))
        nc=$((j + offsets_c[k]))
        
        neighbor_val="${map["$nr,$nc"]}"
        
        if [[ "$neighbor_val" == "@" ]]; then 
            ((cpt++))
        fi

        if (( cpt >= 4 )); then
            return 
        fi
    done
    ((sum++))
}

parse_map(){
    for ((r=0; r<rows; r++)); do
        for ((c=0; c<cols; c++)); do
            check_and_sum "$r" "$c"
        done
    done
}

read_file
parse_map


echo "Answer :"
echo $sum