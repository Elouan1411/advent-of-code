class Point2D {
    #x;
    #y;
    #id;
    static count = 0;
    constructor(a, b) {
        if (typeof a === "string") {
            const parts = a.split(",");
            this.#x = parseInt(parts[0], 10);
            this.#y = parseInt(parts[1], 10);
        } else {
            this.#x = a;
            this.#y = b;
        }

        this.#id = Point2D.count++;
    }

    getX() {
        return this.#x;
    }

    getY() {
        return this.#y;
    }

    getId() {
        return this.#id;
    }
}

module.exports = {
    Point2D,
};
