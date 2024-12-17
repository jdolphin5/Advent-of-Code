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

type Robot = {
    x: number;
    y: number;
    velX: number;
    velY: number;
};

const main = async () => {
    const lineArr: string[] | void = await readFileLineByLine(
        "./input.txt"
    ).catch((err) => console.error(err));

    let result: number = 0;

    const STEPS: number = 100;

    //sample line
    //p=4,72 v=24,-91

    if (lineArr) {
        let robotArr: Robot[] = [];

        let maxX: number = -1;
        let maxY: number = -1;

        for (let i = 0; i < lineArr.length; i++) {
            let pX: number = -1;
            let pY: number = -1;
            let vX: number = -1;
            let vY: number = -1;

            const split1: string[] = lineArr[i].split(" ");
            if (split1[0] && split1[1]) {
                const split2: string[] = split1[0].split("=");
                if (split2[1]) {
                    const split3: string[] = split2[1].split(",");

                    if (split3[0] && split3[1]) {
                        pX = Number(split3[0]);
                        pY = Number(split3[1]);
                    }
                }

                const split4: string[] = split1[1].split("=");

                if (split4[1]) {
                    const split5: string[] = split4[1].split(",");

                    if (split5[0] && split5[1]) {
                        vX = Number(split5[0]);
                        vY = Number(split5[1]);
                    }
                }
            }

            console.log("pX:", pX, "pY:", pY, "vX:", vX, "vY:", vY);

            maxX = Math.max(pX, maxX);
            maxY = Math.max(pY, maxY);

            robotArr.push({ x: pX, y: pY, velX: vX, velY: vY });
        }

        console.log("maxX:", maxX, "maxY:", maxY);

        for (const robot of robotArr) {
            console.log(robot);
            robot.x =
                (robot.x + robot.velX * STEPS + (maxX + 1) * 100) % (maxX + 1);
            robot.y =
                (robot.y + robot.velY * STEPS + (maxY + 1) * 100) % (maxY + 1);
            console.log(robot);
        }

        let topLeft: number = 0;
        let topRight: number = 0;
        let botLeft: number = 0;
        let botRight: number = 0;

        let midX: number = -1;
        let midY: number = -1;

        if (maxX % 2 === 0) {
            midX = maxX / 2;
        }
        if (maxY % 2 === 0) {
            midY = maxY / 2;
        }

        for (const robot of robotArr) {
            if (robot.x === midX || robot.y === midY) {
                continue;
            }

            if (robot.x < midX && robot.y < midY) {
                topLeft++;
            } else if (robot.x < midX && robot.y > midY) {
                botLeft++;
            } else if (robot.x > midX && robot.y < midY) {
                topRight++;
            } else if (robot.x > midX && robot.y > midY) {
                botRight++;
            }
        }

        result += topLeft * botLeft * topRight * botRight;
    }

    console.log(result);
};

main();
