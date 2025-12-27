const fs = require("fs");
const { Point2D } = require("./point2D.js");

function main() {
    const data = readFile("input.txt");
    const res = compute(data);
    console.log("Answser : ", res);
}

function compute(data) {
    let maxH = getMaxH(data);
    data.sort((a, b) => a.getX() - b.getX());
    let n = data.length;
    let maxArea = 0;
    for (let i = 0; i < n; i++) {
        let x1 = data[i].getX();
        let y1 = data[i].getY();
        for (let j = n - 1; j > i; j--) {
            let x2 = data[j].getX();
            let y2 = data[j].getY();

            // opti :
            let width = x2 - x1 + 1;
            if (width * maxH <= maxArea) {
                break;
            }

            let height = Math.abs(y2 - y1) + 1;
            let currentArea = height * width;
            if (currentArea > maxArea) {
                maxArea = currentArea;
            }
        }
    }
    return maxArea;
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
