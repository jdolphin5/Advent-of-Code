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
        let line1: string = "";
        let line2: string = "";
        let line3: string = "";

        for (let i = 0; i < lineArr.length; i++) {
            //first line with Button A
            if (i % 4 === 0) {
                line1 = lineArr[i];
            }
            //second line with Button B
            else if (i % 4 === 1) {
                line2 = lineArr[i];
            }
            //third line with prize
            else if (i % 4 === 2) {
                line3 = lineArr[i];
                let aX: number = -1;
                let aY: number = -1;
                let bX: number = -1;
                let bY: number = -1;
                let prizeX: number = -1;
                let prizeY: number = -1;

                //console.log(line1);
                //console.log(line2);
                //console.log(line3);

                const line1split1 = line1.split("Button A: ");
                if (line1split1[1]) {
                    const line1split2 = line1split1[1].split(", ");

                    if (line1split2[0] && line1split2[1]) {
                        aX = Number(
                            line1split2[0].substring(2, line1split2[0].length)
                        );
                        aY = Number(
                            line1split2[1].substring(2, line1split2[1].length)
                        );
                    }
                }

                const line2split1 = line2.split("Button B: ");
                if (line2split1[1]) {
                    const line2split2 = line2split1[1].split(", ");

                    if (line2split2[0] && line2split2[1]) {
                        bX = Number(
                            line2split2[0].substring(2, line2split2[0].length)
                        );
                        bY = Number(
                            line2split2[1].substring(2, line2split2[1].length)
                        );
                    }
                }

                const line3split1 = line3.split("Prize: ");
                if (line3split1[1]) {
                    const line3split2 = line3split1[1].split(", ");

                    if (line3split2[0] && line3split2[1]) {
                        prizeX = Number(
                            line3split2[0].substring(2, line3split2[0].length)
                        );
                        prizeY = Number(
                            line3split2[1].substring(2, line3split2[1].length)
                        );
                    }
                }

                console.log(
                    "aX:",
                    aX,
                    "aY:",
                    aY,
                    "bX:",
                    bX,
                    "bY:",
                    bY,
                    "prizeX:",
                    prizeX,
                    "prizeY:",
                    prizeY
                );

                for (let x = 0; bX * x <= prizeX && bY * x <= prizeY; x++) {
                    const xVal: number = bX * x;
                    const yVal: number = bY * x;

                    //console.log("x:", x, "bX * x:", xVal, "bY * x:", yVal);

                    if (
                        (prizeX - xVal) % aX === 0 &&
                        (prizeY - yVal) % aY === 0 &&
                        (prizeX - xVal) / aX === (prizeY - yVal) / aY
                    ) {
                        const ret: number = x + 3 * ((prizeX - xVal) / aX);
                        console.log(ret);
                        result += ret;
                        break;
                    }
                }
            }
        }
    }

    console.log(result);
};

main();
