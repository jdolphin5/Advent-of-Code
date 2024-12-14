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
    let stones: number[] = [];
    const BLINK_COUNT: number = 25;

    if (lineArr) {
        if (lineArr[0]) {
            stones = lineArr[0].split(" ").map(Number);
        }

        for (let x = 0; x < BLINK_COUNT; x++) {
            for (let i = 0; i < stones.length; i++) {
                const stonesStr: string = String(stones[i]);

                if (stones[i] === 0) {
                    stones[i] = 1;
                } else if (stonesStr.length % 2 === 0) {
                    const leftStr: string = stonesStr.substring(
                        0,
                        stonesStr.length / 2 || 0
                    );
                    const rightStr: string = stonesStr.substring(
                        stonesStr.length / 2 || 0,
                        stonesStr.length
                    );

                    const leftNum: number = Number(leftStr);
                    const rightNum: number = Number(rightStr);

                    stones.splice(i, 1, leftNum, rightNum);
                    i++;
                } else {
                    stones[i] *= 2024;
                }
            }
        }
    }

    console.log(stones);

    console.log(result, stones.length);
};

main();
