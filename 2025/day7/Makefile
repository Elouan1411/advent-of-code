CC = gcc
CFLAGS = -Wall -Wextra -Werror
EXEC1 = part1
EXEC2 = part2

all: $(EXEC1) $(EXEC2)

$(EXEC1): part1.c part1.h
	$(CC) $(CFLAGS) part1.c -o $(EXEC1)

$(EXEC2): part2.c part2.h
	$(CC) $(CFLAGS) part2.c -o $(EXEC2)

clean:
	rm -f $(EXEC1) $(EXEC2)
