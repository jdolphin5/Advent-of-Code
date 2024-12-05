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
                if (lineArr[i].charAt(j) == "X") {
                    result += checkLetter(lineArr, i, j, 0, 0, "XMAS");
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
    dir: number,
    letterIndex: number,
    word: string
): number => {
    if (
        i < 0 ||
        i >= lineArr.length ||
        j < 0 ||
        j >= lineArr[0].length ||
        lineArr[i].charAt(j) !== word.charAt(letterIndex)
    ) {
        return 0;
    } else if (letterIndex === 3) {
        return 1;
    }

    let ret: number = 0;

    console.log(lineArr[i].charAt(j), "letterIndex:", letterIndex);

    //dir -> 0 (start) /    1: down, 2: down-right, 3: down-left, 4: up, 5: up-right,
    //                      6: up-left, 7: right, 8: left
    if (dir === 0) {
        ret += checkLetter(lineArr, i + 1, j, 1, letterIndex + 1, "XMAS");
        ret += checkLetter(lineArr, i + 1, j + 1, 2, letterIndex + 1, "XMAS");
        ret += checkLetter(lineArr, i + 1, j - 1, 3, letterIndex + 1, "XMAS");
        ret += checkLetter(lineArr, i - 1, j, 4, letterIndex + 1, "XMAS");
        ret += checkLetter(lineArr, i - 1, j + 1, 5, letterIndex + 1, "XMAS");
        ret += checkLetter(lineArr, i - 1, j - 1, 6, letterIndex + 1, "XMAS");
        ret += checkLetter(lineArr, i, j + 1, 7, letterIndex + 1, "XMAS");
        ret += checkLetter(lineArr, i, j - 1, 8, letterIndex + 1, "XMAS");
    } else if (dir === 1) {
        //down
        ret += checkLetter(lineArr, i + 1, j, 1, letterIndex + 1, "XMAS");
    } else if (dir === 2) {
        ret += checkLetter(lineArr, i + 1, j + 1, 2, letterIndex + 1, "XMAS");
    } else if (dir === 3) {
        ret += checkLetter(lineArr, i + 1, j - 1, 3, letterIndex + 1, "XMAS");
    } else if (dir === 4) {
        ret += checkLetter(lineArr, i - 1, j, 4, letterIndex + 1, "XMAS");
    } else if (dir === 5) {
        ret += checkLetter(lineArr, i - 1, j + 1, 5, letterIndex + 1, "XMAS");
    } else if (dir === 6) {
        ret += checkLetter(lineArr, i - 1, j - 1, 6, letterIndex + 1, "XMAS");
    } else if (dir === 7) {
        ret += checkLetter(lineArr, i, j + 1, 7, letterIndex + 1, "XMAS");
    } else if (dir === 8) {
        ret += checkLetter(lineArr, i, j - 1, 8, letterIndex + 1, "XMAS");
    }

    return ret;
};

main();
