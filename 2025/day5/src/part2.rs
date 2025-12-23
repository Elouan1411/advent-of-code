use std::fs;
use std::cmp::Ordering;

#[derive(Debug, Clone, Copy, PartialEq, Eq)]
struct Interval {
    pub start: i64,
    pub end: i64,
}

impl Ord for Interval {
    fn cmp(&self, other: &Self) -> Ordering {
        self.start.cmp(&other.start)
            .then_with(|| self.end.cmp(&other.end))
    }
}

impl PartialOrd for Interval {
    fn partial_cmp(&self, other: &Self) -> Option<Ordering> {
        Some(self.cmp(other))
    }
}

impl Interval {
    fn size(&self) -> i64 {
        self.end - self.start + 1
    }
}

fn parse_advent_input(input: &str) -> Vec<Interval> {
    let normalized_input = input.replace("\r\n", "\n");
    let parts: Vec<&str> = normalized_input.split("\n\n").collect();

    if parts.len() < 2 {
        panic!("blank line not found");
    }

    let intervals: Vec<Interval> = parts[0]
        .lines()
        .filter(|line| !line.is_empty())
        .map(|line| {
            let (start_str, end_str) = line.split_once('-').expect("Invalid interval");
            Interval {
                start: start_str.parse().expect("Start not a number"),
                end: end_str.parse().expect("End not a number"),
            }
        })
        .collect();
    return intervals;
}

fn merge_intervals(intervals: Vec<Interval>) -> Vec<Interval> {
    let mut merged = Vec::new();
    let mut current = intervals[0];
    for next in intervals.into_iter().skip(1){
        if next.start <= current.end {
            current.end = current.end.max(next.end);
        }else{
            merged.push(current);
            current = next;
        }
    }
    merged.push(current);
    return merged;
}


fn parse_range(intervals: Vec<Interval>) -> i64 {
    let mut count: i64 = 0;
    for inter in &intervals{
        count += inter.size();
    }
    return count;
}


pub fn run(file_path: &str) {
    let content = fs::read_to_string(file_path)
        .expect("file not found");

    let mut intervals = parse_advent_input(&content);

    intervals.sort();
    intervals = merge_intervals(intervals);

    let sum = parse_range(intervals);
    println!("Answer part2 :\n\t{}", sum);
}