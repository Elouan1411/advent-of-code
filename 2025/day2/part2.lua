local function read_file(chemin)
    local file = io.open(chemin, "r")
    if not file then error("file introuvable") end
    local content = file:read("*all")
    file:close()
    local tab = {}
    for element in string.gmatch(content, "([^,\r\n]+)") do
        table.insert(tab, element)
    end
    return tab
end

local function getId(range)
    local first, last = string.match(range, "(%d+)%-(%d+)")
    return {first = tonumber(first), last = tonumber(last)}
end

-- Check if the ID consists of a repeating pattern (e.g., "1212" is "12" repeated twice).
-- We iterate through all possible pattern lengths 'm' (up to half the string length).
-- If the total length is divisible by 'm', we repeat the prefix to see if it matches the original ID.
local function isInvalidId(id)
    local id_str = tostring(id)
    for m = 1, #id_str / 2 do 
        if #id_str % m == 0 then
            if id_str == string.rep(string.sub(id_str, 1, m), #id_str / m) then
                return true
            end
        end
    end
    return false    
end

local function findInvalidInRange(range)
    local cpt = 0
    local id = getId(range)
    local sum = 0
    for i = id.first, id.last do 
        if isInvalidId(i) then
            -- print("detect : ", i, " in : ", range)
            sum = sum + i
        end
    end
    return sum
end

local function main()
    local data = read_file("input.txt")
    local sum = 0
    for i = 1, #data do
        sum = sum + findInvalidInRange(data[i]) 
    end
    print("Answer : ", sum)
end

main()