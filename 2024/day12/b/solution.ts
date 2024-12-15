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

type Edge = {
    index: number;
    isLeft: boolean;
};

const recur = (
    lineArr: string[],
    i: number,
    j: number,
    checked: number[][],
    char: string,
    perimeterRowMap: Map<number, Edge[]>,
    perimeterColMap: Map<number, Edge[]>
): { area: number } => {
    if (
        i < 0 ||
        i >= lineArr.length ||
        j < 0 ||
        j >= lineArr[i].length ||
        lineArr[i].charAt(j) !== char ||
        checked[i][j]
    ) {
        return { area: 0 };
    }

    checked[i][j] = 1;

    let areaAdd: number = 1;

    if (i - 1 < 0 || lineArr[i - 1].charAt(j) !== char) {
        const arr: Edge[] = perimeterColMap.get(j) || [];

        arr.push({ index: i, isLeft: true });

        perimeterColMap.set(j, arr);
    }
    if (i + 1 >= lineArr.length || lineArr[i + 1].charAt(j) !== char) {
        const arr: Edge[] = perimeterColMap.get(j) || [];

        arr.push({ index: i + 1, isLeft: false });

        perimeterColMap.set(j, arr);
    }
    if (j - 1 < 0 || lineArr[i].charAt(j - 1) !== char) {
        const arr: Edge[] = perimeterRowMap.get(i) || [];

        arr.push({ index: j, isLeft: true });
        perimeterRowMap.set(i, arr);
    }
    if (j + 1 >= lineArr[i].length || lineArr[i].charAt(j + 1) !== char) {
        const arr: Edge[] = perimeterRowMap.get(i) || [];

        arr.push({ index: j + 1, isLeft: false });
        perimeterRowMap.set(i, arr);
    }

    const ret1 = recur(
        lineArr,
        i - 1,
        j,
        checked,
        char,
        perimeterRowMap,
        perimeterColMap
    );
    const ret2 = recur(
        lineArr,
        i + 1,
        j,
        checked,
        char,
        perimeterRowMap,
        perimeterColMap
    );
    const ret3 = recur(
        lineArr,
        i,
        j - 1,
        checked,
        char,
        perimeterRowMap,
        perimeterColMap
    );
    const ret4 = recur(
        lineArr,
        i,
        j + 1,
        checked,
        char,
        perimeterRowMap,
        perimeterColMap
    );

    areaAdd += ret1.area + ret2.area + ret3.area + ret4.area;

    return { area: areaAdd };
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
                    let perimeterRowMap: Map<number, Edge[]> = new Map();
                    let perimeterColMap: Map<number, Edge[]> = new Map();

                    const ret = recur(
                        lineArr,
                        i,
                        j,
                        checked,
                        lineArr[i].charAt(j),
                        perimeterRowMap,
                        perimeterColMap
                    );

                    //sort maps so they iterate over keys in sorted order
                    const sortedMap = [...perimeterRowMap].sort(
                        (a, b) => a[0] - b[0]
                    );
                    perimeterRowMap = new Map(sortedMap);
                    const sortedMap2 = [...perimeterColMap].sort(
                        (a, b) => a[0] - b[0]
                    );
                    perimeterColMap = new Map(sortedMap2);

                    let perimeter: number = 0;
                    let last: Edge[] = [];

                    perimeterRowMap.forEach((value: Edge[], key: number) => {
                        value.sort();

                        for (let x = 0; x < value.length; x++) {
                            let sameFound: boolean = false;

                            for (let y = 0; y < last.length; y++) {
                                if (
                                    value[x].index === last[y].index &&
                                    value[x].isLeft === last[y].isLeft
                                ) {
                                    sameFound = true;
                                }
                            }

                            if (!sameFound) {
                                perimeter++;
                            }
                        }

                        last = value;
                        console.log(perimeter);
                    });

                    console.log(
                        "char:",
                        lineArr[i].charAt(j),
                        "i:",
                        i,
                        "j:",
                        j,
                        "rowMap:",
                        perimeterRowMap
                    );

                    last.length = 0;

                    perimeterColMap.forEach((value: Edge[], key: number) => {
                        value.sort();

                        for (let x = 0; x < value.length; x++) {
                            let sameFound: boolean = false;

                            for (let y = 0; y < last.length; y++) {
                                if (
                                    value[x].index === last[y].index &&
                                    value[x].isLeft === last[y].isLeft
                                ) {
                                    sameFound = true;
                                }
                            }

                            if (!sameFound) {
                                perimeter++;
                            }
                        }

                        last = value;
                        console.log(key, perimeter);
                    });

                    console.log(
                        "char:",
                        lineArr[i].charAt(j),
                        "i:",
                        i,
                        "j:",
                        j,
                        "colMap:",
                        perimeterColMap
                    );

                    const val: number = ret.area * perimeter;
                    console.log(
                        "char:",
                        lineArr[i].charAt(j),
                        "per:",
                        perimeter,
                        "val:",
                        val
                    );
                    result += val;
                }
            }
        }
    }

    console.log(result);
};

main();
