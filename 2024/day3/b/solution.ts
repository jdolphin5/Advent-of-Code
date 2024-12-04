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
    let isEnabled: boolean = true;

    if (lineArr) {
        for (let i = 0; i < lineArr.length; i++) {
            const res: { result: number; shouldAddMul: boolean } =
                extractAndCalculate(lineArr[i], isEnabled);
            result += res.result;
            isEnabled = res.shouldAddMul;
        }
    }

    console.log("result:", result);
};

const extractAndCalculate = (
    input: string,
    bool: boolean
): { result: number; shouldAddMul: boolean } => {
    const mulRegex = /mul\(\d{1,3},\d{1,3}\)/g;
    const doRegex = /do\(\)/g;
    const dontRegex = /don't\(\)/g;

    const instructions = input.match(
        /mul\(\d{1,3},\d{1,3}\)|do\(\)|don't\(\)/g
    );
    if (!instructions) {
        return { result: 0, shouldAddMul: bool };
    }

    let isEnabled: boolean = bool;
    let sum: number = 0;

    for (const instruction of instructions) {
        console.log(instruction, "isEnabled:", isEnabled);

        if (doRegex.test(instruction)) {
            isEnabled = true;
        } else if (dontRegex.test(instruction)) {
            isEnabled = false;
        } else if (isEnabled) {
            console.log("added");
            const [x, y] = instruction.slice(4, -1).split(",").map(Number);
            sum += x * y;
        }
    }

    return { result: sum, shouldAddMul: isEnabled };
};

main();
