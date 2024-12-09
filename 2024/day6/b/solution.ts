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

let global: number = 0;

type DirType = {
    south: number;
    east: number;
};

const main = async () => {
    const lineArr: string[] | void = await readFileLineByLine(
        "./input.txt"
    ).catch((err) => console.error(err));

    if (lineArr) {
        let i: number = -1;
        let j: number = -1;

        //let rowMap: Map<number, number[]> = new Map();
        //let columnMap: Map<number, number[]> = new Map();

        for (let k = 0; k < lineArr.length; k++) {
            for (let l = 0; l < lineArr[k].length; l++) {
                if (lineArr[k].charAt(l) === "^") {
                    i = k;
                    j = l;
                } /* 
                else if (lineArr[k].charAt(l) === "#") {
                    if (rowMap.get(k) === undefined) {
                        rowMap.set(k, []);
                    }

                    let row: number[] | undefined = rowMap.get(k);

                    if (row) {
                        row.push(l);
                        rowMap.set(k, row);
                    }

                    if (columnMap.get(l) === undefined) {
                        columnMap.set(l, []);
                    }

                    let column: number[] | undefined = columnMap.get(l);

                    if (column) {
                        column.push(k);
                        columnMap.set(l, column);
                    }
                }
                    */
            }
        }

        let positionSet: Set<string> = new Set();
        let resultSet: Set<string> = new Set();

        //starting position
        console.log("startingI:", i, "startingJ:", j);

        let dir: DirType[] = [];
        dir.push({ south: -1, east: 0 });
        dir.push({ south: 0, east: 1 });
        dir.push({ south: 1, east: 0 });
        dir.push({ south: 0, east: -1 });

        nextMove(
            lineArr,
            i,
            j,
            dir,
            0,
            positionSet,
            new Set(),
            resultSet,
            i,
            j
        );

        console.log("resultSet.size:", resultSet.size, resultSet);
        console.log(global);
    }
};

const checkPosition = (
    lineArr: string[],
    i: number,
    j: number,
    dirArr: DirType[],
    dirIndex: number,
    visited: Set<string>
): boolean => {
    const curPosition: string = "i:" + i + "j:" + j + "dir:" + dirIndex;

    //console.log(curPosition);

    if (visited.has(curPosition)) {
        return true;
    }

    //console.log("did not return true");

    //console.log("did not return false");

    visited.add(curPosition);

    //console.log("a");

    const nextI = i + dirArr[dirIndex].south;
    const nextJ = j + dirArr[dirIndex].east;

    // If out of bounds, stop the check
    if (
        nextI < 0 ||
        nextI >= lineArr.length ||
        nextJ < 0 ||
        nextJ >= lineArr[nextI].length
    ) {
        return false;
    }

    if (lineArr[nextI].charAt(nextJ) === "#") {
        const newDirIndex = (dirIndex + 1) % dirArr.length;
        return checkPosition(lineArr, i, j, dirArr, newDirIndex, visited);
    }

    return checkPosition(lineArr, nextI, nextJ, dirArr, dirIndex, visited);
};

const nextMove = (
    lineArr: string[],
    i: number,
    j: number,
    dirArr: DirType[],
    dirIndex: number,
    positionSet: Set<string>,
    checkedSet: Set<string>,
    resultSet: Set<string>,
    startI: number,
    startJ: number
) => {
    console.log("next call");
    if (i >= 0 && i < lineArr.length && j >= 0 && j < lineArr[0].length) {
        let nextI: number = i + dirArr[dirIndex].south;
        let nextJ: number = j + dirArr[dirIndex].east;

        const curPosition: string = "i - " + i + " j - " + j;
        const nextPosition: string = "i - " + nextI + " j - " + nextJ;

        const shouldCheck: boolean = !checkedSet.has(nextPosition);

        if (!positionSet.has(curPosition)) {
            positionSet.add(curPosition);
        }

        console.log("i:", i, "j:", j, "positionSetSize:", positionSet.size);

        console.log(
            "nextI:",
            nextI,
            "nextJ:",
            nextJ,
            lineArr.length,
            lineArr[i].length
        );

        //let nextDirIndex = (dirIndex + 1) % 4;

        if (
            nextI >= 0 &&
            nextI < lineArr.length &&
            nextJ >= 0 &&
            nextJ < lineArr[i].length
        ) {
            console.log("enters if1");
            if (lineArr[nextI].charAt(nextJ) === "#") {
                console.log("enters if2");
                dirIndex = (dirIndex + 1) % 4;
                nextI = i;
                nextJ = j;
            } else if (!(nextI === startI && nextJ === startJ) && shouldCheck) {
                checkedSet.add(nextPosition);
                console.log("enters else");
                let nextDirIndex = (dirIndex + 1) % 4;
                console.log(dirIndex, nextDirIndex);

                let checkSet: Set<string> = new Set();
                const curPosition2: string =
                    "i:" + i + "j:" + j + "dir:" + dirIndex;
                console.log(curPosition2);
                checkSet.add(curPosition2);

                //update lineArr position to obstacle (# instead of .)
                lineArr[nextI] = replaceChar(lineArr[nextI], nextJ, "#");

                if (
                    checkPosition(lineArr, i, j, dirArr, nextDirIndex, checkSet)
                ) {
                    resultSet.add("i:" + nextI + "j:" + nextJ);
                }

                //revert/update lineArr position to obstacle (. instead of #)
                lineArr[nextI] = replaceChar(lineArr[nextI], nextJ, ".");

                //console.log("ends else");
            }

            console.log(
                "nextI:",
                nextI,
                "nextJ:",
                nextJ,
                "dirIndex:",
                dirIndex
            );

            nextMove(
                lineArr,
                nextI,
                nextJ,
                dirArr,
                dirIndex,
                positionSet,
                checkedSet,
                resultSet,
                startI,
                startJ
            );
        }
    }
};

const replaceChar = (str: string, i: number, char: string): string => {
    const arr = str.split("");

    arr[i] = char;

    return arr.join("");
};

main();
