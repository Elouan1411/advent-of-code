# Day 8: Java

To run the solution, compile and execute:

## Part 1 & 2

```bash
javac *.java
java Part1
# or
java Part2
```

## Thoughts

The problems are becoming more complicated, which I like! It's good that I saved the strong languages (suitable and known to me) for the end. It took me more time, but I loved coding it.

## Part 1 Strategy

Even though brute-forcing wasn't strictly necessary since the input file was only 1000 lines, I implemented a major optimization because the goal of Advent of Code is to learn and improve.

To avoid calculating the distance between all points, I first sort the list along each axis (X, Y, Z) in order to generate candidate connections only between geographically close neighbors.

Then, I select the 1000 shortest connections among these candidates to merge the points via a Union-Find structure, and finally multiply the size of the three largest groups formed.
