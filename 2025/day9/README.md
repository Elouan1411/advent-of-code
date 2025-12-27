# Day 9: JavaScript

To run the solution, execute:

## Part 1 & 2

```bash
node part1.js
# or
node part2.js
```

## Thoughts

Not very hard to code because I know the language well, but hard to find different algorithms to reach the results. A lot of reflection, not much writing time. But I loved it!

## Part 1 Strategy

The optimization relies on sorting by the X-axis combined with mathematical pruning that interrupts calculations as soon as the rectangle width, even multiplied by the maximum room height, cannot beat the current record.

## Part 2 Strategy

The strategy consists of validating the rectangle's integrity by verifying its corners and walls don't go outside the polygon via a Ray Casting algorithm, keeping the pruning from the first part to instantly discard candidates that are too small.

Crucially, performing the verifications in the correct order, with the time-consuming ones (like Ray Casting) last, divided my execution time by 5.
