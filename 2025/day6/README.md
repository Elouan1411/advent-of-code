# Day 6: PHP

To run the solution, simply execute:

## Part 1 & 2

```bash
php part1.php
# or
php part2.php
```

## Thoughts

Part 1 was rather easy, but Part 2 required some reflection to avoid brute-forcing and repeatedly traversing the file; it was essentially a parsing problem. I enjoyed it, and being well-versed in PHP, the language didn't get in my way.

## Part 2 Strategy

My strategy is to cut the grid into horizontal zones based on the placement of operators located on the last line. Then, I reconstruct the numbers vertically (from bottom to top) in each zone to apply the operator.
