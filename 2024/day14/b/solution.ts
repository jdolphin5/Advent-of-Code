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

    //sample line
    //p=4,72 v=24,-91

    if (lineArr) {
        let robotArr: Robot[] = [];

        let maxX: number = -1;
        let maxY: number = -1;

        let maxConsecIndex: number = -1;
        let maxConsecCount: number = -Infinity;

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

        //need to make clone of robotArr for each step instead of modifying directly

        for (let STEPS = 0; STEPS < 20000; STEPS++) {
            let grid = Array(maxX + 1)
                .fill(null)
                .map(() => Array(maxY + 1).fill("."));

            let robotArrClone = JSON.parse(JSON.stringify(robotArr));

            for (const robot of robotArrClone) {
                //console.log(robot);
                robot.x =
                    (robot.x + robot.velX * STEPS + (maxX + 1) * 100000) %
                    (maxX + 1);
                robot.y =
                    (robot.y + robot.velY * STEPS + (maxY + 1) * 100000) %
                    (maxY + 1);
                //console.log(robot);

                if (grid[robot.x][robot.y] === ".") {
                    grid[robot.x][robot.y] = "#";
                } else {
                    //const num: number = Number(grid[robot.x][robot.y]);
                    //grid[robot.x][robot.y] = String(num + 1);
                }
                //console.log(robot);

                grid[0][0] = STEPS;
            }

            appendStringArrayToFile("./output.txt", grid);

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

            const ret: number = topLeft * botLeft * topRight * botRight;
            //console.log(ret);
            result += ret;
        }

        console.log("m", maxConsecCount, maxConsecIndex);
    }
    console.log(result);
};

const appendStringArrayToFile = (filePath: string, data: string[][]): void => {
    // Convert 2D array into a formatted string (each row as a line)
    const content = data.map((row) => row.join(",")).join("\n") + "\n";

    // Append the content to the file
    fs.appendFileSync(filePath, content, "utf-8");
};

main();
