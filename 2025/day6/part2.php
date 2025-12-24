<?php

function readFileAndSplit(string $path): array {
    if (!file_exists($path)) {
        return [[], []];
    }

    // keep spaces but not \n
    $lines = file($path, FILE_IGNORE_NEW_LINES);

    if (empty($lines)) {
        return [[], []];
    }

    $operatorsLine = array_pop($lines);
    
    // remove empty line if needed
    while (!empty($lines) && trim(end($lines)) === '') {
        array_pop($lines);
    }

    return [$operatorsLine, $lines];
}

/**
 * define influence zone of the operator
 * return [start, end, char]
 */
function parseOperatorRanges(string $opLine): array {
    $ranges = [];
    $len = strlen($opLine);
    $currentEnd = $len;

    // we go through the process in reverse to find the operators
    for ($i = $len - 1; $i >= 0; $i--) {
        $char = $opLine[$i];
        
        // if it's an operator
        if ($char !== ' ') {
            $ranges[] = [
                'start' => $i, 
                'end' => $currentEnd, 
                'op' => $char
            ];
            
            $currentEnd = $i;
        }
    }

    return array_reverse($ranges);
}


function parseVerticalNumbers(array $lines, array $ranges): array {
    $columnNumbers = []; 
    
    // parse down to up
    $reversedLines = array_reverse($lines);

    foreach ($ranges as $range) {
        for ($col = $range['start']; $col < $range['end']; $col++) {
            
            $currentVal = 0;
            $multiplier = 1;
            $hasNumber = false;
            $finished = false; 

            foreach ($reversedLines as $line) {
                // if the line is to short, it's a space
                if (!isset($line[$col])) {
                    $finished = true;
                    break;
                }

                $char = $line[$col];

                if ($char === ' ') { // space = end of number
                    if ($hasNumber) {
                        $finished = true;
                    }
                } elseif (is_numeric($char)) {
                    if (!$finished) {
                        $digit = (int)$char;
                        $currentVal += $digit * $multiplier;
                        $multiplier *= 10; // with each climb, we multiply
                        $hasNumber = true;
                    }
                }
            }

            if ($hasNumber) {
                $columnNumbers[$col] = $currentVal;
            }
        }
    }

    return $columnNumbers;
}

function compute(array $ranges, array $columnNumbers): int {
    $totalSum = 0;

    foreach ($ranges as $range) {
        $start = $range['start'];
        $end = $range['end'];
        $op = $range['op'];
        
        $currentRangeResult = 0;
        $firstValue = true;

        for ($col = $start; $col < $end; $col++) {
            if (isset($columnNumbers[$col])) {
                $val = $columnNumbers[$col];

                if ($firstValue) {
                    $currentRangeResult = $val;
                    $firstValue = false;
                } else {
                    if ($op === '*') {
                        $currentRangeResult *= $val;
                    } else {
                        $currentRangeResult += $val;
                    }
                }
            }
        }
        
        $totalSum += $currentRangeResult;
    }

    return $totalSum;
}

function main(): void {
    $path = 'input.txt';

    [$operatorsLine, $dataLines] = readFileAndSplit($path);

    $ranges = parseOperatorRanges($operatorsLine);

    $columnNumbers = parseVerticalNumbers($dataLines, $ranges);

    $answer = compute($ranges, $columnNumbers);

    echo "Answer Part 2 : " . $answer . PHP_EOL;
}

main();
?>