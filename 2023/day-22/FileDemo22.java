import java.io.*;
import java.util.*;

public class FileDemo22 {

    private static void computeBricksAbove(Set<Day22Brick> bricksAlreadyCounted, Day22Brick brick, boolean allFall) {
        bricksAlreadyCounted.add(brick);

        System.out.println(brick);

        for (Day22Brick brick2 : brick.above) {
            boolean good = true;

            for (Day22Brick brick3 : brick2.below) {
                if (!bricksAlreadyCounted.contains(brick3)) {
                    good = false;
                }
            }

            if (good) {
                computeBricksAbove(bricksAlreadyCounted, brick2, true);
            }
        }
    }
    public static void main(String[] args) {
        File file = new File("2023/day-22/input.txt");
        List<String> stringPerLineList = new ArrayList<>();

        try {
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine())
                stringPerLineList.add(sc.nextLine());
            sc.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        int[][][] mat = new int[10][10][400];
        for (int[][] mat2 : mat) {
            for (int[] mat3 : mat2) {
                Arrays.fill(mat3, -1);
            }
        }

        List<Day22Brick> brickList = new ArrayList<>();
        int brickNumber = 0;

        for (String s : stringPerLineList) {
        
            String[] split = s.split("~", 0);
            String left = split[0];
            String right = split[1];

            String[] leftSplit = left.split(",", 0);
            String[] rightSplit = right.split(",", 0);

            int leftX = Integer.parseInt(leftSplit[0]);
            int leftY = Integer.parseInt(leftSplit[1]);
            int leftZ = Integer.parseInt(leftSplit[2]);
            int rightX = Integer.parseInt(rightSplit[0]);
            int rightY = Integer.parseInt(rightSplit[1]);
            int rightZ = Integer.parseInt(rightSplit[2]);

            for (int i = leftX; i <= rightX; i++) {
                for (int j = leftY; j <= rightY; j++) {
                    for (int k = leftZ; k <= rightZ; k++) {
                        mat[i][j][k] = brickNumber;
                    }
                }
            }

            brickList.add(new Day22Brick(leftX, leftY, leftZ, rightX, rightY, rightZ, brickNumber));
            brickNumber++;
        }

        boolean canFall = true;

        while (canFall) {
            canFall = false;

            for (Day22Brick brick : brickList) {
                boolean underneath = false;

                for (int i = brick.startX; i <= brick.endX; i++) {
                    for (int j = brick.startY; j <= brick.endY; j++) {
                        if (brick.startZ-1 >= 1 && mat[i][j][brick.startZ-1] != -1)
                            underneath = true;
                    }
                }

                if (brick.startZ > 1 && brick.endZ > 1 && !underneath) {
                    System.out.println(brick.brickNumber);
                    canFall = true;
                    for (int i = brick.startX; i <= brick.endX; i++) {
                        for (int j = brick.startY; j <= brick.endY; j++) {
                            mat[i][j][brick.endZ] = -1;
                            mat[i][j][brick.startZ-1] = brick.brickNumber;
                        }
                    }
                    brick.startZ--;
                    brick.endZ--;
                    brickList.set(brick.brickNumber, brick);
                }
            }
        }

        long ret = 0;

        for (Day22Brick brick : brickList) {

            for (int i = brick.startX; i <= brick.endX; i++) {
                for (int j = brick.startY; j <= brick.endY; j++) {
                    if (mat[i][j][brick.endZ+1] != -1)
                        brick.above.add(brickList.get(mat[i][j][brick.endZ+1]));
                    if (mat[i][j][brick.startZ-1] != -1) 
                        brick.below.add(brickList.get(mat[i][j][brick.startZ-1]));
                }
            }

            if (brick.above.size() == 0) {
                brick.counted = true;
                ret++;
            }

            brickList.set(brick.brickNumber, brick);
        }

        for (Day22Brick brick : brickList) {
            if (!brick.counted) {
                boolean good = true;

                outerloop:
                for (Day22Brick brick2 : brick.above) {
                    if (brick2.below.size() <= 1) {
                        good = false;
                        break outerloop;
                    }
                }

                if (good) {
                    ret++;
                    brick.counted = true;
                    brickList.set(brick.brickNumber, brick);
                }
            }
        }

        //      Part 1
        //      drop each unsupported brick by 1 unit on the z axis until all bricks are supported
        //      count the number of bricks that are either
        //      a) not supporting any bricks above
        // or   b) only supporting bricks that are supported by more than one brick

        System.out.println("ret: " + ret);

        //      Part 2
        //      Of the bricks that would not cause any bricks to fall (brick.counted == false)
        //      If all of the bricks below them are falling,
        //      then they are falling too, and the brick supported by them can also be checked

        long ret2 = 0L;

        for (Day22Brick brick : brickList) {
            if (!brick.counted) {
                System.out.println(brick.brickNumber);
                Set<Day22Brick> brickSet = new HashSet<>();
                computeBricksAbove(brickSet, brick, false);
                ret2 += brickSet.size()-1; // do not include current brick
            }
        }

        System.out.println("ret2: " + ret2);
    }
}
