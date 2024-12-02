import * as fs from "fs";
import * as readline from "readline";

const readFileLineByLine = async (filePath: string): Promise<number[][]> => {
    let levelArr: number[][] = [];

    const fileStream = fs.createReadStream(filePath);

    const rl = readline.createInterface({
        input: fileStream,
        crlfDelay: Infinity // Handles CRLF and LF line endings
    });

    for await (const line of rl) {
        console.log(line); // Process each line

        const splitLine: string[] = line.split(" ");
        console.log(splitLine);

        let lineArr: number[] = [];

        for (let i = 0; i < splitLine.length; i++) {
            if (splitLine[i] !== " ") {
                lineArr.push(Number(splitLine[i]));
            }
        }

        levelArr.push(lineArr);
    }

    return levelArr;

    //left.sort((a, b) => a - b);
    //right.sort((a, b) => a - b);
};

const main = async () => {
    const levelArr: number[][] | void = await readFileLineByLine(
        "./input.txt"
    ).catch((err) => console.error(err));

    let result: number = 0;

    if (levelArr) {
        for (let i = 0; i < levelArr.length; i++) {
            let type: string = "";

            for (let j = 0; j < levelArr[i].length - 1; j++) {
                const diff: number = levelArr[i][j + 1] - levelArr[i][j];
                const pos: boolean =
                    levelArr[i][j + 1] > levelArr[i][j] ? true : false;
                if (Math.abs(diff) >= 1 && Math.abs(diff) <= 3) {
                    if (type === "") {
                        type = pos ? "pos" : "neg";
                    } else if (type === "pos") {
                        if (!pos) {
                            break;
                        }
                    } else {
                        if (pos) {
                            break;
                        }
                    }
                } else {
                    break;
                }

                if (j == levelArr[i].length - 2) {
                    result++;
                }
            }
        }
    }

    console.log("result:", result);
};

main();
