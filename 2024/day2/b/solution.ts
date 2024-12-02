import * as fs from "fs";
import * as readline from "readline";

const readFileLineByLine = async (filePath: string): Promise<number[][]> => {
    let levelArr: number[][] = [];

    const fileStream = fs.createReadStream(filePath);

    const rl = readline.createInterface({
        input: fileStream,
        crlfDelay: Infinity //carriage return / line feed
    });

    for await (const line of rl) {
        //console.log(line);

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
};

const main = async () => {
    const levelArr: number[][] | void = await readFileLineByLine(
        "./input.txt"
    ).catch((err) => console.error(err));

    let result: number = 0;

    if (levelArr) {
        for (let i = 0; i < levelArr.length; i++) {
            let good: boolean = false;

            for (let k = 0; k < levelArr[i].length - 1; k++) {
                let type: string = "";
                let left: number | null = null;
                let right: number | null = null;

                console.log("k: ", k);

                for (let j = 0; j < levelArr[i].length - 1; j++) {
                    if (j === k) {
                        right = levelArr[i][j + 1];
                        console.log(levelArr[i], "skip left:", levelArr[i][j]);
                    } else if (j == k - 1) {
                        left = levelArr[i][j];
                        right = null;
                    } else {
                        left = levelArr[i][j];
                        right = levelArr[i][j + 1];
                    }

                    if (!left || !right) {
                        continue;
                    }

                    const diff: number = right - left;
                    const pos: boolean = right > left ? true : false;

                    console.log("diff:", diff, "left:", left, "right:", right);

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

                    if (j === levelArr[i].length - 2) {
                        console.log("good");
                        good = true;
                    }
                }
            }

            let type: string = "";

            //check without last entry in line
            for (let j = 0; j < levelArr[i].length - 1; j++) {
                const left: number = levelArr[i][j];
                const right: number = levelArr[i][j + 1];

                const diff: number = right - left;
                const pos: boolean = right > left ? true : false;

                console.log("diff:", diff, "left:", left, "right:", right);

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

                if (j === levelArr[i].length - 3) {
                    console.log("good");
                    good = true;
                }
            }

            if (good) {
                result++;
            }
        }
    }

    console.log("result:", result);
};

main();
