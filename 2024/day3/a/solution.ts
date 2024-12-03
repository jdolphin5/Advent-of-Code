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
            result += extractAndCalculate(lineArr[i]);
        }
    }

    console.log("result:", result);
};

const extractAndCalculate = (input: string): number => {
    //regex expression for "mul(number,number)" (/g to repeat for whole string, not just first occurence)
    const regex = /mul\(\d{1,3},\d{1,3}\)/g;

    const matches = input.match(regex);

    if (!matches) {
        return 0;
    }

    const sum = matches.reduce((acc, match) => {
        const [x, y] = match
            .slice(4, -1) //remove "mul(" from start and ")" from end
            .split(",") //split by the comma
            .map(Number); //convert to number type
        return acc + x * y;
    }, 0);

    return sum;
};

main();
