def read_file(path)
  File.readlines(path, chomp: true)
end

def get_max_joltage(bank)
    nb_digits = 12
    tab_multiple = create_tab_multiple(nb_digits)
    joltage = 0
    
    current_start_index = 0

    nb_digits.times do |i|
        digits_needed_after = nb_digits - 1 - i
        
        search_limit = bank.length - digits_needed_after

        info = max_with_index(bank, current_start_index, search_limit)

        # Calcul
        joltage = joltage + tab_multiple[nb_digits - i - 1] * info[:max]
        
        current_start_index = info[:index] + 1
    end

    return joltage
end 

def create_tab_multiple(nb_digits)
    result = [1]
    (nb_digits - 1).times do |i|
        result.push(result[i]*10)
    end
    return result
end

def max_with_index(bank, start_index, limit_index)
    max = -1
    i_max = start_index

    (start_index...limit_index).each do |current_index|
        val = bank[current_index].to_i
    
        if val > max
            max = val
            i_max = current_index
        end
        
        break if max == 9 # opti
    end
    
    return {index: i_max, max: max}
end

def main()
    data = read_file("input.txt")
    sum = 0
    data.each do |bank|
        val = get_max_joltage(bank)
        sum += val
    end
    puts "Total joltage : #{sum}"
end

main()