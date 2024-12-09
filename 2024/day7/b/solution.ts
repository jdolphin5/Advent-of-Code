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
        for (let i = 0; i < lineArr.length; i++) {
            const line: string[] = lineArr[i].split(": ");
            const testValue: number = Number(line[0]);
            const equationNums: number[] = line[1].split(" ").map(Number);

            //console.log(testValue);
            //console.log(equationNums);

            const possibleResults: number[] = [];

            computePossibleResults(
                equationNums,
                1,
                possibleResults,
                equationNums[0]
            );

            //console.log(possibleResults);

            if (possibleResults.includes(testValue)) {
                result += testValue;
            }
        }
    }

    console.log("result:", result);
};

const computePossibleResults = (
    equationNums: number[],
    i: number,
    possibleResults: number[],
    sum: number
): void => {
    if (i === equationNums.length) {
        possibleResults.push(sum);
        return;
    }

    //+
    computePossibleResults(
        equationNums,
        i + 1,
        possibleResults,
        sum + equationNums[i]
    );

    //*
    computePossibleResults(
        equationNums,
        i + 1,
        possibleResults,
        sum * equationNums[i]
    );

    //concat
    const concat: string = String(sum).concat(String(equationNums[i]));
    computePossibleResults(
        equationNums,
        i + 1,
        possibleResults,
        Number(concat)
    );
};

main();
