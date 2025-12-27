class Edge {
    #a;
    #b;
    #isVertical;
    constructor(a, b) {
        this.#a = a;
        this.#b = b;
        this.#isVertical = a.getX() === b.getX();
    }

    getA() {
        return this.#a;
    }

    getB() {
        return this.#b;
    }

    isVertical() {
        return this.#isVertical;
    }

    contain(p) {
        const px = p.getX();
        const py = p.getY();
        const ax = this.#a.getX();
        const ay = this.#a.getY();
        const bx = this.#b.getX();
        const by = this.#b.getY();

        if (this.#isVertical) {
            return px === ax && py >= Math.min(ay, by) && py <= Math.max(ay, by);
        } else {
            return py === ay && px >= Math.min(ax, bx) && px <= Math.max(ax, bx);
        }
    }

    intersects(otherEdge) {
        if (this.#isVertical === otherEdge.isVertical()) {
            return false;
        }

        const r1 = this.#a,
            r2 = this.#b;
        const l1 = otherEdge.getA(),
            l2 = otherEdge.getB();

        const isXMatch = this.#a.getX() >= Math.min(l1.getX(), l2.getX()) && this.#a.getX() <= Math.max(l1.getX(), l2.getX());

        const isYMatch = r1.getY() > l1.getY() !== r2.getY() > l1.getY();

        return isXMatch && isYMatch;
    }
}

module.exports = {
    Edge,
};
