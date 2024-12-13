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

type FileObj = {
    size: number;
    id: number;
    isFreeSpace: boolean;
};

const main = async () => {
    const lineArr: string[] | void = await readFileLineByLine(
        "./input.txt"
    ).catch((err) => console.error(err));

    let result: number = 0;
    let fileArr: FileObj[] = [];

    if (lineArr) {
        for (let i = 0; i < lineArr.length; i++) {
            let id: number = 0;

            for (let j = 0; j < lineArr[i].length; j++) {
                const num: number = Number(lineArr[i].charAt(j));

                if (j % 2 == 0) {
                    //for (let k = 0; k < num; k++) {
                    //    idArr.push(id);
                    //}
                    fileArr.push({
                        size: num,
                        id: id,
                        isFreeSpace: false
                    });

                    id++;
                } else {
                    fileArr.push({
                        size: num,
                        id: -1,
                        isFreeSpace: true
                    });
                }
            }

            console.log(fileArr.length);

            let k: number = fileArr.length - 1;

            console.log(fileArr);

            for (; k >= 0; k--) {
                for (let j = 0; j < k; j++) {
                    if (
                        !fileArr[k].isFreeSpace &&
                        fileArr[j].isFreeSpace &&
                        fileArr[j].size >= fileArr[k].size
                    ) {
                        //delete fileArr[j], replace with fileArr[k] + leftover freespace
                        const sizeDiff: number =
                            fileArr[j].size - fileArr[k].size;

                        //move file on right to free space on left
                        fileArr.splice(j, 1, fileArr[k]);
                        fileArr.splice(k, 1, {
                            size: fileArr[k].size,
                            isFreeSpace: true,
                            id: -1
                        });

                        //add excess file with free space
                        if (sizeDiff > 0) {
                            const excess: FileObj = {
                                size: sizeDiff,
                                id: -1,
                                isFreeSpace: true
                            };

                            fileArr.splice(j + 1, 0, excess);

                            //increase k by one as the array size has increased
                            k++;
                        }
                    }
                }
            }

            //merge free space objects
            /*
            for (let i = 0; i < fileArr.length - 1; i++) {
                if (fileArr[i].isFreeSpace && fileArr[i + 1].isFreeSpace) {
                    const mergedObj: FileObj = {
                        size: fileArr[i].size + fileArr[i + 1].size,
                        id: -1,
                        isFreeSpace: true
                    };

                    fileArr.splice(i, 2, mergedObj);
                    i--;
                }
            }
            */

            let x: number = 0;

            for (let i = 0; i < fileArr.length; i++) {
                if (fileArr[i].id >= 0) {
                    for (let y = 0; y < fileArr[i].size; y++) {
                        result += x * fileArr[i].id;
                        console.log("x:", x, "id:", fileArr[i].id);
                        x++;
                    }
                } else {
                    x += fileArr[i].size;
                }
            }
        }
    }

    console.log(fileArr);

    console.log(result);
};

main();
