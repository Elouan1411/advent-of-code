def openFile(path: str) -> tuple:
    l1 = list()
    l2 = list()
    with open(path, "r", encoding="utf-8") as file:
        for line in file:
            nb = line.strip().split("   ")
            l1.append(int(nb[0]))
            l2.append(int(nb[1]))
    return (l1, l2)


def result(l1: list[int], l2: list[int]) -> int:
    dico = {}
    for nb in l2:
        dico[nb] = dico.get(nb, 0) + 1

    return sum(nb * dico.get(nb, 0) for nb in l1)


def main():
    l1, l2 = openFile("list_nb.txt")
    print(result(l1, l2))


if __name__ == "__main__":
    main()
