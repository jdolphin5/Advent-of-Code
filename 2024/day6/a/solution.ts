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
        let i: number = -1;
        let j: number = -1;

        outerloop: for (let k = 0; k < lineArr.length; k++) {
            for (let l = 0; l < lineArr[k].length; l++) {
                if (lineArr[k].charAt(l) === "^") {
                    i = k;
                    j = l;
                    break outerloop;
                }
            }
        }

        let positionSet: Set<string> = new Set();

        //starting position
        console.log("startingI:", i, "startingJ:", j);

        type DirType = {
            south: number;
            east: number;
        };

        let dir: DirType[] = [];
        dir.push({ south: -1, east: 0 });
        dir.push({ south: 0, east: 1 });
        dir.push({ south: 1, east: 0 });
        dir.push({ south: 0, east: -1 });

        let dirIndex: number = 0;

        while (
            i >= 0 &&
            i < lineArr.length &&
            j >= 0 &&
            j < lineArr[0].length
        ) {
            let nextI: number = i + dir[dirIndex].south;
            let nextJ: number = j + dir[dirIndex].east;
            let move: boolean = true;

            const curPosition: string = "i - " + i + " j - " + j;

            if (!positionSet.has(curPosition)) {
                positionSet.add(curPosition);
                result++;
            }

            if (
                nextI >= 0 &&
                nextI < lineArr.length &&
                nextJ >= 0 &&
                nextJ < lineArr[i].length
            ) {
                if (lineArr[nextI].charAt(nextJ) === "#") {
                    dirIndex = (dirIndex + 1) % 4;
                    move = false;
                }
            }

            console.log("i:", i, "j:", j, "result:", result);

            if (move) {
                i = nextI;
                j = nextJ;
            }
        }
    }

    console.log("result:", result);
};

main();
