def mul(string: str):
    nb1, nb2 = string[4:-1].split(",")
    return int(nb1) * int(nb2)


def main():
    path = "corrupted.txt"
    res = 0
    valid_str = ""
    n1 = ""
    n2 = ""
    word = "mul(,)"

    with open(path, "r") as file:
        for char in file.read():
            if char in word or char.isnumeric():

                if (
                    (char == "m" and len(valid_str) == 0)
                    or (char == "u" and len(valid_str) == 1)
                    or (char == "l" and len(valid_str) == 2)
                    or (char == "(" and len(valid_str) == 3)
                ):
                    valid_str += char
                elif char.isnumeric() and len(valid_str) > 3 and (len(n1) < 4 or len(n2) < 3):
                    if len(n1) < 4:
                        n1 += "X"
                    else:
                        n2 += "X"
                    valid_str += char
                elif char == "," and len(valid_str) == 4 + len(str(n1)):
                    valid_str += char
                    n1 = "X" * 10 + n1
                elif char == ")" and len(valid_str) == 5 + len(n1) - 10 + len(n2):
                    valid_str += char
                    res += mul(valid_str)
                    valid_str = ""
                    n1 = ""
                    n2 = ""
                else:
                    valid_str = ""
                    n1 = ""
                    n2 = ""
            else:
                valid_str = ""
                n1 = ""
                n2 = ""

    print(res)


if __name__ == "__main__":
    main()
