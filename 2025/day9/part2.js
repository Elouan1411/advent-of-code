const fs = require("fs");
const { Point2D } = require("./point2D.js");
const { Edge } = require("./edge.js");

function main() {
    const data = readFile("input.txt");
    const edges = createEdges(data);
    const res = compute(data, edges);
    console.log("Answser : ", res);
}

function createEdges(data) {
    let edges = [new Edge(data[data.length - 1], data[0])];
    for (let i = 0; i < data.length - 1; i++) {
        edges.push(new Edge(data[i], data[i + 1]));
    }
    return edges;
}

function compute(data, edges) {
    let maxH = getMaxH(data);
    data.sort((a, b) => a.getX() - b.getX());
    let n = data.length;
    let maxArea = 0;
    let maxX = Math.max(...data.map((p) => p.getX()));
    for (let i = 0; i < n; i++) {
        let p1 = data[i];
        let x1 = data[i].getX();
        let y1 = data[i].getY();
        for (let j = n - 1; j > i; j--) {
            let p2 = data[j];
            let x2 = data[j].getX();
            let y2 = data[j].getY();

            // opti :
            let width = x2 - x1 + 1;
            if (width * maxH <= maxArea) {
                break;
            }

            let height = Math.abs(y2 - y1) + 1;
            let currentArea = height * width;

            if (currentArea > maxArea && rectangleIsInside(p1, p2, edges, maxX)) {
                maxArea = currentArea;
            }
        }
    }
    return maxArea;
}

function rectangleIsInside(p1, p2, edges, maxX) {
    // Check edges
    if (!noWallInside(p1, p2, edges)) return false;

    // Check points
    const p3 = new Point2D(p1.getX(), p2.getY());
    const p4 = new Point2D(p2.getX(), p1.getY());
    return pointIsInside(p3, edges, maxX) && pointIsInside(p4, edges, maxX);
}

function noWallInside(p1, p2, edges) {
    const xMin = Math.min(p1.getX(), p2.getX());
    const xMax = Math.max(p1.getX(), p2.getX());
    const yMin = Math.min(p1.getY(), p2.getY());
    const yMax = Math.max(p1.getY(), p2.getY());

    for (const edge of edges) {
        const ax = edge.getA().getX();
        const ay = edge.getA().getY();
        const bx = edge.getB().getX();
        const by = edge.getB().getY();

        if (edge.isVertical()) {
            if (ax > xMin && ax < xMax) {
                const wallYMin = Math.min(ay, by);
                const wallYMax = Math.max(ay, by);
                if (wallYMax > yMin && wallYMin < yMax) return false;
            }
        } else {
            if (ay > yMin && ay < yMax) {
                const wallXMin = Math.min(ax, bx);
                const wallXMax = Math.max(ax, bx);
                if (wallXMax > xMin && wallXMin < xMax) return false;
            }
        }
    }
    return true;
}

function pointIsInside(p, edges, maxX) {
    for (const edge of edges) {
        if (edge.contain(p)) return true;
    }

    let nbEdges = 0;
    for (const edge of edges) {
        let pEdge = new Point2D(maxX + 1, p.getY());
        if (edge.isVertical() && edge.intersects(new Edge(p, pEdge))) {
            nbEdges++;
        }
    }
    return nbEdges % 2 !== 0;
}

function getMaxH(data) {
    let all_y = data.map((point) => point.getY());
    return Math.max(...all_y) - Math.min(...all_y);
}

function readFile(filePath) {
    try {
        const fileContent = fs.readFileSync(filePath, "utf-8");
        const lines = fileContent.split(/\r?\n/);
        const data = [];

        lines.forEach((line) => {
            if (line.trim()) {
                data.push(new Point2D(line));
            }
        });

        return data;
    } catch (error) {
        console.error("Error reading file:", error.message);
        return [];
    }
}

main();
