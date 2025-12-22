def read_file(path)
  File.readlines(path, chomp: true)
end

def get_max_joltage(bank)
    # choice the first number 
    first = max_with_index(bank, false)
    # choice the second
    second = max_with_index(bank, true, first[:index] + 1)
    return first[:max] * 10 + second[:max]
end 


# get the maximum value (index of first occurence return)
def max_with_index(bank, with_last_jolt, first_index = 0)
    max = bank[first_index].to_i
    i_max = first_index
    bank = bank.slice(first_index..-1)
    bank.each_char.with_index do |jolt, index|
        if jolt.to_i > max && (with_last_jolt || index < (bank.length - 1))
            i_max = index + first_index
            max = jolt.to_i
        end
    end
    return {index:i_max, max:max}
end




def main()
    data = read_file("input.txt")
    sum = 0
    data.each do |bank|
        sum = sum + get_max_joltage(bank)
    end
    puts "Total joltage : ", sum
end


main()