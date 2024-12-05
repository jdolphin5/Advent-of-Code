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
    let br: number = -1;

    if (lineArr) {
        for (let i = 0; i < lineArr.length; i++) {
            if (lineArr[i] === "") {
                br = i;
            }
        }

        let numbersThatComeAfterNumberMap: Map<Number, number[]> = new Map();

        console.log("br:", br);

        for (let i = 0; i < br; i++) {
            const line: string[] = lineArr[i].split("|");
            const first: number = Number(line[0]);
            const second: number = Number(line[1]);

            if (numbersThatComeAfterNumberMap.get(first) === undefined) {
                numbersThatComeAfterNumberMap.set(first, []);
            }

            let arr: number[] | undefined =
                numbersThatComeAfterNumberMap.get(first);

            if (arr) {
                arr.push(second);
                numbersThatComeAfterNumberMap.set(first, arr);
            }
        }

        console.log(numbersThatComeAfterNumberMap);

        for (let i = br + 1; i < lineArr.length; i++) {
            let good: boolean = false;

            const arr: number[] = lineArr[i].split(",").map(Number);

            outerloop: for (let j = 0; j < arr.length; j++) {
                const first: number = arr[j];
                const arr2: number[] | undefined =
                    numbersThatComeAfterNumberMap.get(first);

                if (arr2) {
                    for (let k = j - 1; k >= 0; k--) {
                        for (const num of arr2) {
                            if (arr[k] === num) {
                                break outerloop;
                            }
                        }
                    }
                }

                if (j == arr.length - 1) {
                    good = true;
                }
            }

            if (good) {
                result += arr[(arr.length / 2) | 0];
                console.log(
                    "i:",
                    i,
                    "added:",
                    arr[(arr.length / 2) | 0],
                    "arr:",
                    arr
                );
            } else {
                console.log("DID NOT ADD");
            }
        }
    }

    console.log("result:", result);
};

main();
