#ifndef PART1_H
#define PART1_H

typedef struct {
    int rows;
    int cols;
    char** data;
} CharTable;

long int compute(CharTable* t);
CharTable read_file(const char* filename);
void free_table(CharTable* t);

#endif
