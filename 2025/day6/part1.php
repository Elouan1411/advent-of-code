<?php

function readFileAndSplit(string $path): array {
    if (!file_exists($path)) {
        return [[], []];
    }

    $lines = file($path, FILE_IGNORE_NEW_LINES | FILE_SKIP_EMPTY_LINES);

    if (empty($lines)) {
        return [[], []];
    }

    $operatorsLine = array_pop($lines);
        
    $operators = preg_split('/\s+/', trim($operatorsLine));

    return [$operators, $lines];
}

function compute(array $operators, array $dataLines): array {
    if (empty($operators) || empty($dataLines)) {
        return [];
    }

    $result = array_fill(0, count($operators), 0);

    foreach ($dataLines as $line) {
        $values = preg_split('/\s+/', trim($line));

        foreach ($values as $col => $value) {
            $value = (int)$value;

            if ($operators[$col] === '*') {
                if ($result[$col] === 0) {
                    $result[$col] = 1;
                }
                $result[$col] *= $value;
            } else {
                $result[$col] += $value;
            }
        }
    }

    return $result;
}

function main(): void {
    $path = 'input.txt';

    [$operators, $dataLines] = readFileAndSplit($path);

    $columns = compute($operators, $dataLines);

    echo "Answer : " . array_sum($columns) . PHP_EOL;
}

main();
