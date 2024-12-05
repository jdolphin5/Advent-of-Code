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
                if (lineArr[i].charAt(j) == "A") {
                    if (checkLetter(lineArr, i, j, 0) === 6) {
                        result++;
                    }
                }
            }
        }
    }

    console.log("result:", result);
};

const checkLetter = (
    lineArr: string[],
    i: number,
    j: number,
    index: number
): number => {
    if (i < 0 || i >= lineArr.length || j < 0 || j >= lineArr[0].length) {
        return 0;
    } else if (lineArr[i].charAt(j) === "M") {
        return 1;
    } else if (lineArr[i].charAt(j) === "S") {
        return 2;
    } else if (index === 1) {
        return 0;
    }

    let ret1: number = 0;
    let ret2: number = 0;

    // down-right, up-left
    ret1 += checkLetter(lineArr, i + 1, j + 1, 1);
    ret1 += checkLetter(lineArr, i - 1, j - 1, 1);

    // down-left, up-right
    ret2 += checkLetter(lineArr, i + 1, j - 1, 1);
    ret2 += checkLetter(lineArr, i - 1, j + 1, 1);

    if (ret1 === 3 && ret2 === 3) {
        return 6;
    } else {
        return -1;
    }
};

main();
