import java.io.*;
import java.util.*;

public class FileDemo18 {

    /*

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

    */

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

        /*
        
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

            if (x.x == start.x && x.y == start.y) break;

            List<Day18Coordinate> getNextSteps = getNextSteps(x, mat2, symbolMapping);

            for (Day18Coordinate y : getNextSteps) {
                if (x.last.x != y.x || x.last.y != y.y) {
                    nextStack.push(y);
                    //System.out.println(y.symbol);
                }
                
            }
        }

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

        //Part 1:
        System.out.println(stepCount + area);

        */

        //Part 2:
        //The Shoelace Formula Suppose the n vertices of a simple polygon in the Euclidean plane are listed in
        //counterclockwise order as (x0, y0), . . . , (xn−1, yn−1). Then the area A of the polygon may be calculated as:
        //A = 1/2 (x0y1 − x1y0 + . . . + xn−2yn−1 − xn−1yn−2 + xn−1y0 − x0yn−1)

        long a = 0;
        long b = 0;
        long perimeter = 0;

        List<Day18Vertex> vertexList = new ArrayList<>();
        long minX = 0;
        long minY = Integer.MAX_VALUE;

        for (String s : stringPerLineList) {
            String[] split1 = s.split(" ", 0);
            String instruction = split1[2];

            String distanceString = instruction.substring(2, 1+6);
            int dir = instruction.charAt(7) - '0';
            long dist = Long.parseLong(distanceString, 16);
            
            switch(dir) {
                case 0:
                    b += dist;
                    break;
                case 1:
                    a += dist;
                    break;
                case 2:
                    b -= dist;
                    break;
                case 3:
                    a -= dist;
                    break;

            }
            perimeter += dist;

            minX = Math.min(minX, a);
            minY = Math.min(minY, b);

            vertexList.add(new Day18Vertex(a, b));
        }

        Collections.reverse(vertexList);

        for (Day18Vertex x : vertexList) {
            x.x -= minX;
            x.y -= minY;
        }

        long ret = 0;

        for (int m = 0; m < vertexList.size(); m++) {
            int cur = m;
            int next = (m + 1) % vertexList.size();
            
            ret += vertexList.get(cur).x * vertexList.get(next).y;
            ret -= vertexList.get(next).x * vertexList.get(cur).y;
            //System.out.println(ret);
        }

        ret /= 2;

        System.out.println(ret);
        //Formula for perimeter + area with Shoelace:
        //shoelace + (perimeter / 2) + 1
        System.out.println("Part 2: " + (ret + (perimeter/2L) + 1L));
    }
}
