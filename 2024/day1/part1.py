def quick_sort(arr):
    if len(arr) <= 1:
        return arr
    else:
        pivot = arr[len(arr) // 2]
        left = [x for x in arr if x < pivot]
        middle = [x for x in arr if x == pivot]
        right = [x for x in arr if x > pivot]
        return quick_sort(left) + middle + quick_sort(right)


def openFile(path: str) -> tuple:
    l1 = list()
    l2 = list()
    with open(path, "r", encoding="utf-8") as file:
        for line in file:
            nb = line.strip().split("   ")
            l1.append(nb[0])
            l2.append(nb[1])
    return (l1, l2)


def sumDiff(l1: list, l2: list) -> int:
    return sum(abs(int(x) - int(y)) for x, y in zip(l1, l2))


def main():
    l1, l2 = openFile("list_nb.txt")
    l1 = quick_sort(l1)
    l2 = quick_sort(l2)
    print(sumDiff(l1, l2))


if __name__ == "__main__":
    main()
