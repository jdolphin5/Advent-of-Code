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
    let idArr: number[] = [];

    if (lineArr) {
        for (let i = 0; i < lineArr.length; i++) {
            let id: number = 0;

            for (let j = 0; j < lineArr[i].length; j++) {
                const num: number = Number(lineArr[i].charAt(j));

                if (j % 2 == 0) {
                    for (let k = 0; k < num; k++) {
                        idArr.push(id);
                    }

                    id++;
                } else {
                    for (let k = 0; k < num; k++) {
                        idArr.push(-1);
                    }
                }
            }

            let j: number = 0;
            let k: number = idArr.length - 1;

            while (j < k) {
                id = 0;

                if (idArr[j] === -1) {
                    while (idArr[k] === -1) {
                        k--;
                    }

                    if (k > j) id = idArr[k];

                    k--;
                } else {
                    id = idArr[j];
                }

                result += j * id;

                console.log("j:", j, "k:", k, "id:", id, "result:", result);
                j++;
            }

            if (j === k && idArr[j] !== -1) {
                result += j * idArr[j];
            }
        }
    }

    console.log(result);
};

main();
