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

    //left.sort((a, b) => a - b);
    //right.sort((a, b) => a - b);
}

const main = async () => {
    await readFileLineByLine("./input.txt").catch((err) => console.error(err));

    let result: number = 0;

    let rightMap = new Map<number, number>();

    for (let i = 0; i < right.length; i++) {
        rightMap.set(right[i], (rightMap.get(right[i]) ?? 0) + 1);
    }

    for (let i = 0; i < left.length; i++) {
        let lineValue: number = 0;
        if (rightMap.get(left[i]) !== undefined) {
            lineValue = rightMap.get(left[i])! * left[i];
            console.log(
                "left:",
                left[i],
                "rightMap.get:" + rightMap.get(left[i])!,
                "val:",
                lineValue
            );
            result += lineValue;
        }
    }

    console.log("result:", result);
};

main();
