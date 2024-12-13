import * as fs from "fs";
import * as readline from "readline";

const readFileLineByLine = async (filePath: string): Promise<string[]> => {
    let lineArr: string[] = [];

    const fileStream = fs.createReadStream(filePath);

    const rl = readline.createInterface({
        input: fileStream,
        crlfDelay: Infinity //carriage return / line feed
    });

    for await (const line of rl) {
        //console.log(line);

        lineArr.push(line);
    }

    return lineArr;
};

const main = async () => {
    const lineArr: string[] | void = await readFileLineByLine(
        "./input.txt"
    ).catch((err) => console.error(err));

    let result: number = 0;

    if (lineArr) {
        for (let i = 0; i < lineArr.length; i++) {
            for (let j = 0; j < lineArr[i].length; j++) {
                if (lineArr[i].charAt(j) === "0") {
                    const add: number = recur(
                        lineArr,
                        i,
                        j,
                        "0",
                        new Set(),
                        new Set()
                    );
                    console.log(add);
                    result += add;
                }
            }
        }
    }

    console.log(result);
};

const recur = (
    lineArr: string[],
    i: number,
    j: number,
    last: string,
    visited: Set<string>,
    nineVisited: Set<string>
): number => {
    const position: string = "i - " + i + " j - " + j;

    if (last === "9") {
        if (!nineVisited.has(position)) {
            nineVisited.add(position);

            return 1;
        } else return 0;
    }

    if (
        i < 0 ||
        i >= lineArr.length ||
        j < 0 ||
        j >= lineArr[0].length ||
        visited.has(position)
    ) {
        return 0;
    }

    visited.add(position);

    const lastNum = Number(last);

    const nextNum = ["1", "2", "3", "4", "5", "6", "7", "8", "9"];

    let ret: number = 0;

    if (i - 1 >= 0 && lineArr[i - 1].charAt(j) === nextNum[lastNum]) {
        ret += recur(lineArr, i - 1, j, nextNum[lastNum], visited, nineVisited);
    }

    if (
        i + 1 < lineArr.length &&
        lineArr[i + 1].charAt(j) === nextNum[lastNum]
    ) {
        ret += recur(lineArr, i + 1, j, nextNum[lastNum], visited, nineVisited);
    }

    if (j - 1 >= 0 && lineArr[i].charAt(j - 1) === nextNum[lastNum]) {
        ret += recur(lineArr, i, j - 1, nextNum[lastNum], visited, nineVisited);
    }

    if (
        j + 1 < lineArr[i].length &&
        lineArr[i].charAt(j + 1) === nextNum[lastNum]
    ) {
        ret += recur(lineArr, i, j + 1, nextNum[lastNum], visited, nineVisited);
    }

    return ret;
};

main();
