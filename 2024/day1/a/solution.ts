import * as fs from "fs";
import * as readline from "readline";

let left: number[] = [];
let right: number[] = [];

async function readFileLineByLine(filePath: string) {
    const fileStream = fs.createReadStream(filePath);

    const rl = readline.createInterface({
        input: fileStream,
        crlfDelay: Infinity // Handles CRLF and LF line endings
    });

    for await (const line of rl) {
        console.log(line); // Process each line

        const splitLine: string[] = line.split(" ", 4);
        console.log(splitLine);
        left.push(Number(splitLine[0]));
        right.push(Number(splitLine[3]));
    }

    left.sort((a, b) => a - b);
    right.sort((a, b) => a - b);
}

const main = async () => {
    await readFileLineByLine("./input.txt").catch((err) => console.error(err));

    let result: number = 0;

    for (let i = 0; i < left.length; i++) {
        const sum: number = Math.abs(left[i] - right[i]);
        result += sum;
        console.log("left:", left[i], "right:", right[i], "result:", sum);
    }

    console.log("result:", result);
};

main();
