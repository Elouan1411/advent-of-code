#include "part2.h"

#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int main(void) {
    CharTable t = read_file("input.txt");

    if (t.data == NULL) {
        fprintf(stderr, "Error: Could not read file.\n");
        return 1;
    }

    unsigned long long res = compute(&t);
    printf("Answer :\n%lld\n", res);

    free_table(&t);
    return 0;
}

unsigned long long compute(CharTable* t) {
    // Arrays for current active columns and next row flags (deduplication)
    unsigned long long* current_cols = calloc(t->cols, sizeof(unsigned long long));
    unsigned long long* next_flags = calloc(t->cols, sizeof(unsigned long long));

    // 1. Find 'S' in the first row
    if (t->rows > 0) {
        for (int j = 0; j < t->cols; j++) {
            if (t->data[0][j] == 'S') {
                current_cols[j] = 1;
            }
        }
    }

    // 2. Process line by line
    for (int i = 1; i < t->rows; i++) {
        // Reset flags for the next row
        memset(next_flags, 0, t->cols * sizeof(unsigned long long));

        // Iterate through currently active lasers
        for (int k = 0; k < t->cols; k++) {
            if (current_cols[k] == 0) continue;
            int pos = k;

            if (t->data[i][pos] == '^') {
                // Split laser: mark left and right positions
                if (pos - 1 >= 0) next_flags[pos - 1] += current_cols[k];
                if (pos + 1 < t->cols) next_flags[pos + 1] += current_cols[k];
            } else {
                // No interaction: mark current position
                next_flags[pos] += current_cols[k];
            }
        }

        // 3. Rebuild current list from flags (deduplication)
        for (int col = 0; col < t->cols; col++) {
            current_cols[col] = next_flags[col];
        }
    }
    unsigned long long res = calculate_result(current_cols, t->cols);
    free(current_cols);
    free(next_flags);
    return res;
}

unsigned long long calculate_result(unsigned long long* final, int nb_col) {
    unsigned long long res = 0;
    for (int i = 0; i < nb_col; i++) {
        res += final[i];
    }
    return res;
}

CharTable read_file(const char* filename) {
    CharTable table = {0, 0, NULL};
    FILE* f = fopen(filename, "r");
    if (!f) return table;

    // First pass: count rows and cols
    int c, current_cols = 0;
    while ((c = fgetc(f)) != EOF) {
        if (c == '\n') {
            table.rows++;
            if (current_cols > table.cols) table.cols = current_cols;
            current_cols = 0;
        } else
            current_cols++;
    }
    // Handle last line without newline
    if (current_cols > 0) {
        table.rows++;
        if (current_cols > table.cols) table.cols = current_cols;
    }
    fclose(f);

    // Allocate memory
    table.data = malloc(table.rows * sizeof(char*));
    for (int i = 0; i < table.rows; i++)
        table.data[i] = malloc(table.cols * sizeof(char));

    // Second pass: fill data
    f = fopen(filename, "r");
    int i = 0, j = 0;
    while ((c = fgetc(f)) != EOF) {
        if (c == '\n') {
            i++;
            j = 0;
        } else {
            if (i < table.rows && j < table.cols)
                table.data[i][j++] = (char)c;
        }
    }
    fclose(f);
    return table;
}

void free_table(CharTable* t) {
    if (!t->data) return;
    for (int i = 0; i < t->rows; i++)
        free(t->data[i]);
    free(t->data);
    t->data = NULL;
}