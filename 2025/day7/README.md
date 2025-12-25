# Day 7: C

To run the solution, verify you have `make` installed and execute:

## Part 1 & 2

```bash
make
./part1
# or
./part2
```

## Thoughts

I really enjoyed this one because I have much more experience with C than the other languages, so I know the syntax well. Part 2 looked impressive at first glance, but thinking about how Part 1 was done, the same logic applied to Part 2.

I had a big issue for almost 30 minutes where I couldn't understand why I was getting the correct answer with the easy input but not the normal input. After some research, I realized the capacity of an `int` was too small, so I switched to `unsigned long long`, and everything worked perfectly!

## Part 2 Strategy

Instead of tracking each laser trace individually, simply sum their quantity for each column (storing the number "50" instead of managing 50 different objects).

This transforms an exponential explosion of data into simple mathematical additions on a fixed-size array, making the calculation instant even with billions of lasers.
