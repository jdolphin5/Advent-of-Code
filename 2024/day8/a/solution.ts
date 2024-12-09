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

type Coordinate = {
    x: number;
    y: number;
};

const main = async () => {
    const lineArr: string[] | void = await readFileLineByLine(
        "./input.txt"
    ).catch((err) => console.error(err));

    const antennaMap: Map<string, Coordinate[]> = new Map();
    const antinodeSet: Set<string> = new Set();

    if (lineArr) {
        for (let i = 0; i < lineArr.length; i++) {
            for (let j = 0; j < lineArr[i].length; j++) {
                const char: string = lineArr[i].charAt(j);

                if (char !== ".") {
                    let arr: Coordinate[] | undefined = antennaMap.get(char);

                    if (!arr) {
                        arr = [];
                    }

                    let coord: Coordinate = { x: i, y: j };
                    arr.push(coord);

                    antennaMap.set(char, arr);
                }
            }
        }

        console.log(antennaMap);

        const xLowerLimit: number = 0;
        const yLowerLimit: number = 0;
        const xUpperLimit: number = lineArr.length;
        const yUpperLimit: number = lineArr[0].length;

        antennaMap.forEach((value: Coordinate[], key: string) => {
            const set: Set<string> = new Set();

            computeAntinodePositions(
                key,
                value,
                set,
                xLowerLimit,
                xUpperLimit,
                yLowerLimit,
                yUpperLimit
            );

            console.log(set);

            //merge set to main antinodeSet
            set.forEach(antinodeSet.add, antinodeSet);
        });
    }

    console.log(antinodeSet.size);
};

const computeAntinodePositions = (
    key: string,
    value: Coordinate[],
    set: Set<string>,
    xLowerLimit: number,
    xUpperLimit: number,
    yLowerLimit: number,
    yUpperLimit: number
) => {
    //for every pair of coordinates, compute their antinode positions

    for (let i = 0; i < value.length; i++) {
        for (let j = i + 1; j < value.length; j++) {
            const coord1: Coordinate = value[i];
            const coord2: Coordinate = value[j];

            const antinode1I: number = coord1.x + (coord1.x - coord2.x);
            const antinode1J: number = coord1.y + (coord1.y - coord2.y);

            const antinode2I: number = coord2.x + (coord2.x - coord1.x);
            const antinode2J: number = coord2.y + (coord2.y - coord1.y);

            if (
                antinode1I >= xLowerLimit &&
                antinode1I < xUpperLimit &&
                antinode1J >= yLowerLimit &&
                antinode1J < yUpperLimit
            ) {
                set.add("i:" + antinode1I + "j:" + antinode1J);
            }
            if (
                antinode2I >= xLowerLimit &&
                antinode2I < xUpperLimit &&
                antinode2J >= yLowerLimit &&
                antinode2J < yUpperLimit
            ) {
                set.add("i:" + antinode2I + "j:" + antinode2J);
            }
        }
    }
};

main();
