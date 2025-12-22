#!/bin/bash

path_input="input.txt"
declare -A map
offsets_r=(-1 -1 -1  0 0  1 1 1)
offsets_c=(-1  0  1 -1 1 -1 0 1)

rows=0
cols=0
total_removed=0

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

run_round() {
    local to_remove=""
    local round_count=0
    
    for ((r=0; r<rows; r++)); do
        for ((c=0; c<cols; c++)); do
            
            local val="${map["$r,$c"]}"
            if [[ "$val" != "@" ]]; then 
                continue
            fi

            local cpt=0
            local nr nc neighbor_val
            
            for ((k=0; k<8; k++)); do
                nr=$((r + offsets_r[k]))
                nc=$((c + offsets_c[k]))
                neighbor_val="${map["$nr,$nc"]}"
                
                if [[ "$neighbor_val" == "@" ]]; then 
                    ((cpt++))
                fi
                
                if (( cpt >= 4 )); then
                    break
                fi
            done

            
            if (( cpt < 4 )); then
                to_remove+="$r,$c "
                ((round_count++))
            fi
        done
    done

    # manage delete
    if (( round_count > 0 )); then
        for coord in $to_remove; do
            map[$coord]="." 
        done
        ((total_removed += round_count))
        return 0 
    else
        return 1 
    fi
}

solve_part2() {
    while run_round; do
        :
    done
}

read_file
solve_part2

echo "Answer :"
echo $total_removed