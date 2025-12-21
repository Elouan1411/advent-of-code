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

local function isInvalidId(id)
    local id_str = tostring(id)
    if #id_str % 2 == 1 then
        return false
    end
    local mid = #id_str / 2
    for i = 1, mid do
        if string.sub(id_str, i, i) ~= string.sub(id_str, mid+i, mid+i) then
            return false
        end
    end
    return true
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