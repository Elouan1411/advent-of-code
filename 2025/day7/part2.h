#ifndef PART2_H
#define PART2_H

typedef struct {
    int rows;
    int cols;
    char** data;
} CharTable;

unsigned long long compute(CharTable* t);
CharTable read_file(const char* filename);
void free_table(CharTable* t);
unsigned long long calculate_result(unsigned long long* final, int nb_col);

#endif
