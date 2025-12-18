use std::fs::File;
use std::io::{self, BufRead};
use std::path::Path;

fn count_word(grid: &[Vec<char>], word: &str) -> usize {
    let directions = [
        (0, 1),   // droite
        (1, 0),   // bas
        (1, 1),   // diagonale bas droite
        (1, -1),  // diagonale bas gauche
        (0, -1),  // gauche
        (-1, 0),  // haut
        (-1, -1), // diagonale haut gauche
        (-1, 1),  // diagonale haut droite
    ];

    let word_len = word.len();
    let word_chars: Vec<char> = word.chars().collect();
    let rows = grid.len();
    let cols = grid[0].len();
    let mut count = 0;

    for row in 0..rows {
        for col in 0..cols {
            for &(dr, dc) in &directions {
                let mut found = true;

                for i in 0..word_len {
                    let r = row as isize + dr * i as isize;
                    let c = col as isize + dc * i as isize;

                    if r < 0 || r >= rows as isize || c < 0 || c >= cols as isize {
                        found = false;
                        break;
                    }

                    if grid[r as usize][c as usize] != word_chars[i] {
                        found = false;
                        break;
                    }
                }

                if found {
                    count += 1;
                }
            }
        }
    }

    count
}

fn main() {
    let input_file = "input.txt";

    Path::new(input_file);

    let file = File::open(input_file).expect("Failed to open file.");
    let reader = io::BufReader::new(file);

    let grid: Vec<Vec<char>> = reader
        .lines()
        .map(|line| line.expect("Failed to read line.").chars().collect())
        .collect();

    let word = "XMAS";
    let result = count_word(&grid, word);
    println!("The word '{}' appears {} times in the grid.", word, result);
}
