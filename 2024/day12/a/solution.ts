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

const recur = (
    lineArr: string[],
    i: number,
    j: number,
    checked: number[][],
    char: string
): { area: number; perimeter: number } => {
    if (
        i < 0 ||
        i >= lineArr.length ||
        j < 0 ||
        j >= lineArr[i].length ||
        lineArr[i].charAt(j) !== char ||
        checked[i][j]
    ) {
        return { area: 0, perimeter: 0 };
    }

    checked[i][j] = 1;

    let perimeterAdd: number = 0;
    let areaAdd: number = 1;

    if (i - 1 < 0 || lineArr[i - 1].charAt(j) !== char) {
        perimeterAdd++;
    }
    if (i + 1 >= lineArr.length || lineArr[i + 1].charAt(j) !== char) {
        perimeterAdd++;
    }
    if (j - 1 < 0 || lineArr[i].charAt(j - 1) !== char) {
        perimeterAdd++;
    }
    if (j + 1 >= lineArr[i].length || lineArr[i].charAt(j + 1) !== char) {
        perimeterAdd++;
    }

    const ret1 = recur(lineArr, i - 1, j, checked, char);
    const ret2 = recur(lineArr, i + 1, j, checked, char);
    const ret3 = recur(lineArr, i, j - 1, checked, char);
    const ret4 = recur(lineArr, i, j + 1, checked, char);

    perimeterAdd +=
        ret1.perimeter + ret2.perimeter + ret3.perimeter + ret4.perimeter;
    areaAdd += ret1.area + ret2.area + ret3.area + ret4.area;

    return { area: areaAdd, perimeter: perimeterAdd };
};

const main = async () => {
    const lineArr: string[] | void = await readFileLineByLine(
        "./input.txt"
    ).catch((err) => console.error(err));

    let result: number = 0;
    let checked: number[][] = [];

    if (lineArr) {
        for (let i = 0; i < lineArr.length; i++) {
            const arr: number[] = [];

            for (let j = 0; j < lineArr[i].length; j++) {
                arr.push(0);
            }

            checked.push(arr);
        }

        for (let i = 0; i < checked.length; i++) {
            for (let j = 0; j < checked[i].length; j++) {
                if (checked[i][j] === 0) {
                    const ret = recur(
                        lineArr,
                        i,
                        j,
                        checked,
                        lineArr[i].charAt(j)
                    );

                    result += ret.area * ret.perimeter;
                }
            }
        }
    }

    console.log(result);
};

main();
