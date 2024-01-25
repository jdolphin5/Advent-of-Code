import java.io.*;
import java.util.*;

public class FileDemo18 {

    private static char getCornerValue(String[][] mat2, int x, int y) {
        char ret = '&';

        boolean top = false;
        boolean left = false;
        boolean right = false;
        boolean bottom = false;

        if (x-1 >= 0 && mat2[x-1][y] != null) {
            top = true;
        }

        if (y-1 >= 0 && mat2[x][y-1] != null) {
            left = true;
        }

        if (x+1 < mat2.length && mat2[x+1][y] != null) {
            bottom = true;
        }

        if (y+1 < mat2[0].length && mat2[x][y+1] != null) {
            right = true;
        }

        if (top && left) {
            return 'J';
        }

        if (top && right) {
            return 'L';
        }

        if (bottom && left) {
            return '7';
        }

        if (bottom && right) {
            return 'F';
        }

        if (bottom && top) {
            return '|';
        }

        if (left && right) {
            return '-';
        }

        return ret;
    }

    private static Day18Coordinate getStartCoordinate(char[][] symbolMapping, String[][] mat2) {
        for (int i = 0; i < symbolMapping.length; i++) {
            for (int j = 0; j < symbolMapping.length; j++) {
                if (symbolMapping[i][j] != '&') {
                    return new Day18Coordinate(i, j, symbolMapping[i][j], mat2[i][j], null);
                }
            }
        }

        return null;
    }

    private static List<Day18Coordinate> getNextSteps(Day18Coordinate currentCoordinate, String[][] mat2, char[][] matrix) {
        List<Day18Coordinate> result = new ArrayList<>();
    
        int currentX = currentCoordinate.x;
        int currentY = currentCoordinate.y;
        char currentChar = matrix[currentX][currentY];
    
        switch (currentChar) {
            case 'F':
                if (isValidCoordinate(currentX + 1, currentY, matrix)) {
                    result.add(new Day18Coordinate(currentX + 1, currentY, matrix[currentX + 1][currentY], mat2[currentX + 1][currentY], currentCoordinate));
                }
                if (isValidCoordinate(currentX, currentY + 1, matrix)) {
                    result.add(new Day18Coordinate(currentX, currentY + 1, matrix[currentX][currentY + 1], mat2[currentX][currentY + 1], currentCoordinate));
                }
                break;
    
            case 'J':
                if (isValidCoordinate(currentX - 1, currentY, matrix)) {
                    result.add(new Day18Coordinate(currentX - 1, currentY, matrix[currentX - 1][currentY], mat2[currentX - 1][currentY], currentCoordinate));
                }
                if (isValidCoordinate(currentX, currentY - 1, matrix)) {
                    result.add(new Day18Coordinate(currentX, currentY - 1, matrix[currentX][currentY - 1], mat2[currentX][currentY - 1], currentCoordinate));
                }
                break;
    
            case 'L':
                if (isValidCoordinate(currentX - 1, currentY, matrix)) {
                    result.add(new Day18Coordinate(currentX - 1, currentY, matrix[currentX - 1][currentY], mat2[currentX - 1][currentY], currentCoordinate));
                }
                if (isValidCoordinate(currentX, currentY + 1, matrix)) {
                    result.add(new Day18Coordinate(currentX, currentY + 1, matrix[currentX][currentY + 1], mat2[currentX][currentY + 1], currentCoordinate));
                }
                break;
    
            case '7':
                if (isValidCoordinate(currentX + 1, currentY, matrix)) {
                    result.add(new Day18Coordinate(currentX + 1, currentY, matrix[currentX + 1][currentY], mat2[currentX + 1][currentY], currentCoordinate));
                }
                if (isValidCoordinate(currentX, currentY - 1, matrix)) {
                    result.add(new Day18Coordinate(currentX, currentY - 1, matrix[currentX][currentY - 1], mat2[currentX][currentY - 1], currentCoordinate));
                }
                break;
    
            case '|':
                if (isValidCoordinate(currentX - 1, currentY, matrix)) {
                    result.add(new Day18Coordinate(currentX - 1, currentY, matrix[currentX - 1][currentY], mat2[currentX - 1][currentY], currentCoordinate));
                }
                if (isValidCoordinate(currentX + 1, currentY, matrix)) {
                    result.add(new Day18Coordinate(currentX + 1, currentY, matrix[currentX + 1][currentY], mat2[currentX + 1][currentY], currentCoordinate));
                }
                break;
    
            case '-':
                if (isValidCoordinate(currentX, currentY - 1, matrix)) {
                    result.add(new Day18Coordinate(currentX, currentY - 1, matrix[currentX][currentY - 1], mat2[currentX][currentY - 1], currentCoordinate));
                }
                if (isValidCoordinate(currentX, currentY + 1, matrix)) {
                    result.add(new Day18Coordinate(currentX, currentY + 1, matrix[currentX][currentY + 1], mat2[currentX][currentY + 1], currentCoordinate));
                }
                break;
    
            default:
                break;
        }
    
        return result;
    }

    private static boolean isValidCoordinate(int x, int y, char[][] matrix) {
        return x >= 0 && x < matrix.length && y >= 0 && y < matrix[0].length;
    }

    public static void main(String[] args) {
        File file = new File("2023/day-18/input.txt");
        List<String> stringPerLineList = new ArrayList<>();

        try {
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine())
                stringPerLineList.add(sc.nextLine());
            sc.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        
        String[][] mat = new String[10000][10000];
        int i = 500;
        int j = 500;

        for (String s : stringPerLineList) {
            String[] split1 = s.split(" ", 0);
            char dir = split1[0].charAt(0);
            int amt = Integer.parseInt(split1[1]);
            String colour = split1[2].substring(1, split1[2].length()-1);//e.g. (#272dc0)
            
            switch(dir) {
                case 'U':
                    for (int x = 1; x <= amt; x++) {
                        i--;
                        mat[i][j] = colour;
                    }
                    break;
                case 'L':
                    for (int x = 1; x <= amt; x++) {
                        j--;
                        mat[i][j] = colour;
                    }
                    break;
                case 'R':
                    for (int x = 1; x <= amt; x++) {
                        j++;
                        mat[i][j] = colour;
                    }
                    break;
                case 'D':
                    for (int x = 1; x <= amt; x++) {
                        i++;
                        mat[i][j] = colour;
                    }
                    break;
                default:
            }

            //System.out.println("i: " + i + " j: " + j);
        }

        int minY = Integer.MAX_VALUE;
        int maxY = Integer.MIN_VALUE;
        int minX = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;

        for (int x = 0; x < mat.length; x++) {
            for (int y = 0; y < mat[0].length; y++) {
                if (mat[x][y] != null) {
                    minY = Math.min(minY, y);
                    maxY = Math.max(maxY, y);
                    minX = Math.min(minX, x);
                    maxX = Math.max(maxX, x);
                }
            }
        }

        String[][] mat2 = new String[maxX - minX + 1][maxY - minY + 1];

        int a = 0;

        for (int x = minX; x <= maxX; x++) {
            int b = 0;

            for (int y = minY; y <= maxY; y++) {
                mat2[a][b++] = mat[x][y];
            }

            a++;
        }

        System.out.println(mat2[2][212]);

        char[][] symbolMapping = new char[mat2.length][mat2[0].length];

        for (int x = 0; x < mat2.length; x++) {
            for (int y = 0; y < mat2[0].length; y++) {
                symbolMapping[x][y] = getCornerValue(mat2, x, y);
            }
        }

        List<Day18Coordinate>[] loopCoordinates = new ArrayList[mat.length];

        for (int x = 0; x < loopCoordinates.length; x++) {
            loopCoordinates[x] = new ArrayList<>();
        }

        Day18Coordinate start = getStartCoordinate(symbolMapping, mat2);

        System.out.println(start.toString());

        Stack<Day18Coordinate> nextStack = new Stack<>();
        nextStack.push(new Day18Coordinate(start.x+1, start.y, symbolMapping[start.x+1][start.y], mat2[start.x+1][start.y], start));

        int stepCount = 0;

        while (!nextStack.isEmpty()) {
            Day18Coordinate x = nextStack.pop();
            if (x.symbol != '-')
                loopCoordinates[x.x].add(x);

            stepCount++;

            System.out.println(x.toString());

            if (x.x == start.x && x.y == start.y) break;

            List<Day18Coordinate> getNextSteps = getNextSteps(x, mat2, symbolMapping);

            for (Day18Coordinate y : getNextSteps) {
                if (x.last.x != y.x || x.last.y != y.y) {
                    nextStack.push(y);
                    System.out.println(y.symbol);
                }
                
            }
        }

        System.out.println(stepCount);

        long area = 0;

        for (int x = 0; x < loopCoordinates.length; x++) {// 2; i++) {//
            boolean insideLoop = false;
            char last = ' ';

            Collections.sort(loopCoordinates[x], (m, n) -> m.y - n.y);

            if (loopCoordinates[x].size() > 0) {
                if (loopCoordinates[x].get(0).symbol == '|')
                    insideLoop = true;
                last = loopCoordinates[x].get(0).symbol;
            }

            for (int y = 0; y < loopCoordinates[x].size(); y++) {
                //System.out.println("y: " + loopCoordinates[x].get(y).y + " step: " + loopCoordinates[x].get(y).symbol);
            }

            for (int y = 1; y < loopCoordinates[x].size(); y++) {
                Day18Coordinate z = loopCoordinates[x].get(y);

                if (insideLoop && (z.symbol != 'J' && last != 'F') && (z.symbol != '7' && last != 'L')) {
                    area += Math.max(0, z.y - loopCoordinates[x].get(y-1).y - 1);
                    //System.out.println("y: " + z.y + " area: " + area + " insideLoop" + insideLoop);
                }

                if (z.symbol == '|' || (z.symbol == 'J' && last == 'F') || (z.symbol == '7' && last == 'L'))
                    insideLoop = !insideLoop;

                last = z.symbol;
            }

        }

        System.out.println(area);

        //Part 1:
        System.out.println(stepCount + area);
    }
}
