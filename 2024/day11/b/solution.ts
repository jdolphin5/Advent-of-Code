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
    const BLINK_COUNT: number = 75;

    let stonesMap: Map<number, number> = new Map();

    if (lineArr) {
        if (lineArr[0]) {
            stones = lineArr[0].split(" ").map(Number);
        }

        for (const stone of stones) {
            stonesMap.set(stone, (stonesMap.get(stone) || 0) + 1);
        }

        for (let x = 0; x < BLINK_COUNT; x++) {
            const add: { value: number; key: number }[] = [];

            stonesMap.forEach((value: number, key: number) => {
                //console.log("val:", value, "key:", key);
                //console.log(count, value);
                const stonesStr: string = String(key);

                if (key === 0) {
                    add.push({ value: value, key: 1 });
                    /*
                    stonesMap.set(1, (stonesMap.get(1) || 0) + count);
                    */
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

                    add.push({
                        value: value,
                        key: leftNum
                    });
                    add.push({
                        value: value,
                        key: rightNum
                    });

                    /*stonesMap.set(
                        leftNum,
                        (stonesMap.get(leftNum) || 0) + count
                    );
                    stonesMap.set(
                        rightNum,
                        (stonesMap.get(rightNum) || 0) + count
                    );
                    */
                } else {
                    add.push({
                        value: value,
                        key: key * 2024
                    });
                    /*
                    stonesMap.set(
                        value * 2024,
                        (stonesMap.get(value * 2024) || 0) + count
                    );
                    */
                }
            });

            //console.log(add);

            let newStonesMap: Map<number, number> = new Map();

            for (const obj of add) {
                newStonesMap.set(
                    obj.key,
                    (newStonesMap.get(obj.key) || 0) + obj.value
                );
            }

            stonesMap = newStonesMap;
            //console.log("stonesMap:", stonesMap);

            //console.log(console.log(x), stonesMap.size);

            result = 0;

            stonesMap.forEach((value: number, key: number) => {
                result += value;
                //console.log(key, value);
                //console.log(result);
            });

            console.log(result);
        }
    }

    console.log(result);
};

main();
