#include <stdio.h>
#include <stdlib.h>
#include <string.h>

typedef struct {
    int x;
    int y;
} Vect2D;

void displayMap(char** map, Vect2D v) {
    for (int i = 0; i < v.x; i++) {
        for (int j = 0; j < v.y; j++) {
            printf("%c ", map[i][j]);
        }
        printf("\n");
    }
}

void readFileToMap(const char* filename, char*** map, Vect2D* size) {
    FILE* file = fopen(filename, "r");
    if (file == NULL) {
        printf("Erreur d'ouverture du fichier.\n");
        return;
    }

    char line[256];
    size->x = 0;
    size->y = 0;

    while (fgets(line, sizeof(line), file)) {
        (size->x)++;
        int len = strlen(line);
        if (line[len - 1] == '\n') {
            line[len - 1] = '\0';
            len--;
        }
        if (len > size->y) {
            size->y = len;
        }
    }

    rewind(file);

    // Allouer la mémoire pour la carte (tableau 2D)
    *map = (char**)malloc(size->x * sizeof(char*));
    for (int i = 0; i < size->x; i++) {
        (*map)[i] = (char*)malloc(
            (size->y + 1) *
            sizeof(char));  //+1 pour le caractère null de fin de chaîne
    }

    // Remplir le tableau 2D avec le contenu du fichier
    int i = 0;
    while (fgets(line, sizeof(line), file)) {
        int len = strlen(line);
        if (line[len - 1] == '\n') {
            line[len - 1] = '\0';
            len--;
        }
        strcpy((*map)[i], line);
        i++;
    }

    fclose(file);
}

void turnRight(Vect2D* dir, char** map, Vect2D* pGuard) {
    if (dir->x == -1 && dir->y == 0) {  // Haut → Droite
        dir->x = 0;
        dir->y = 1;
        // map[pGuard->x][pGuard->y] = '>';
    } else if (dir->x == 0 && dir->y == 1) {  // Droite → Bas
        dir->x = 1;
        dir->y = 0;
        // map[pGuard->x][pGuard->y] = 'v';
    } else if (dir->x == 1 && dir->y == 0) {  // Bas → Gauche
        dir->x = 0;
        dir->y = -1;
        // map[pGuard->x][pGuard->y] = '<';
    } else if (dir->x == 0 && dir->y == -1) {  // Gauche → Haut
        dir->x = -1;
        dir->y = 0;
        // map[pGuard->x][pGuard->y] = '^';
    }
}

int move(char** map, Vect2D* pGuard, Vect2D size, Vect2D* dir, int* cpt) {
    if (pGuard->x + dir->x >= size.x || pGuard->x + dir->x < 0 ||
        pGuard->y + dir->y >= size.y || pGuard->y + dir->y < 0) {
        return 0;  // exit the map
    }
    if (map[pGuard->x + dir->x][pGuard->y + dir->y] == '#') {
        turnRight(dir, map, pGuard);
    } else {
        if (map[pGuard->x + dir->x][pGuard->y + dir->y] != 'X') {
            (*cpt)++;
        }
        map[pGuard->x + dir->x][pGuard->y + dir->y] = map[pGuard->x][pGuard->y];
        map[pGuard->x][pGuard->y] = 'X';

        pGuard->x += dir->x;
        pGuard->y += dir->y;
    }
    return 1;
}

int calculPath(char** map, Vect2D size) {
    Vect2D dir;
    dir.x = -1;
    dir.y = 0;
    int cpt = 1;
    Vect2D guard;
    guard.x = 0;
    guard.y = 0;
    int find = 0;
    // find Vect2D guard
    for (guard.x = 0; guard.x < size.x && !find; guard.x++) {
        for (guard.y = 0; guard.y < size.y && !find; guard.y++) {
            if (map[guard.x][guard.y] == '^') {
                find = 1;
                guard.x--;
                guard.y--;
            }
        }
    }
    while (move(map, &guard, size, &dir, &cpt)) {
        // if not exit the map
        // displayMap(map, size);
        // printf("%d,%d", dir.x, dir.y);
        // printf("%d", cpt);
    }

    // printf("The guard left the map, he explored %d squares", cpt);
    printf("%d", cpt);

    return cpt;
}

void freeMap(char** map, Vect2D size) {
    for (int i = 0; i < size.x; i++) {
        free(map[i]);
    }
    free(map);
}

int main() {
    Vect2D v;
    char** map;
    readFileToMap("map.txt", &map, &v);
    // displayMap(map, v);
    // printf("\n");
    calculPath(map, v);
    freeMap(map, v);
}