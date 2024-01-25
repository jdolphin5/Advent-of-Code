import java.io.*;
import java.util.*;

public class FileDemo10 {
    private static Day10Coordinate getStartCoordinate(char[][] mat) {
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[0].length; j++) {
                if (mat[i][j] == 'S')
                    return new Day10Coordinate(i, j, 'S', new Day10Coordinate(-1, -1, 'X', null));
            }
        }

        return new Day10Coordinate(0, 0, 'S', null);
    }

    private static List<Day10Coordinate> getNextSteps(Day10Coordinate currentCoordinate, char[][] matrix) {
        List<Day10Coordinate> result = new ArrayList<>();
    
        int currentX = currentCoordinate.x;
        int currentY = currentCoordinate.y;
        char currentChar = matrix[currentX][currentY];
    
        switch (currentChar) {
            case 'F':
                if (isValidCoordinate(currentX + 1, currentY, matrix)) {
                    result.add(new Day10Coordinate(currentX + 1, currentY, matrix[currentX + 1][currentY], currentCoordinate));
                }
                if (isValidCoordinate(currentX, currentY + 1, matrix)) {
                    result.add(new Day10Coordinate(currentX, currentY + 1, matrix[currentX][currentY + 1], currentCoordinate));
                }
                break;
    
            case 'J':
                if (isValidCoordinate(currentX - 1, currentY, matrix)) {
                    result.add(new Day10Coordinate(currentX - 1, currentY, matrix[currentX - 1][currentY], currentCoordinate));
                }
                if (isValidCoordinate(currentX, currentY - 1, matrix)) {
                    result.add(new Day10Coordinate(currentX, currentY - 1, matrix[currentX][currentY - 1], currentCoordinate));
                }
                break;
    
            case 'L':
                if (isValidCoordinate(currentX - 1, currentY, matrix)) {
                    result.add(new Day10Coordinate(currentX - 1, currentY, matrix[currentX - 1][currentY], currentCoordinate));
                }
                if (isValidCoordinate(currentX, currentY + 1, matrix)) {
                    result.add(new Day10Coordinate(currentX, currentY + 1, matrix[currentX][currentY + 1], currentCoordinate));
                }
                break;
    
            case '7':
                if (isValidCoordinate(currentX + 1, currentY, matrix)) {
                    result.add(new Day10Coordinate(currentX + 1, currentY, matrix[currentX + 1][currentY], currentCoordinate));
                }
                if (isValidCoordinate(currentX, currentY - 1, matrix)) {
                    result.add(new Day10Coordinate(currentX, currentY - 1, matrix[currentX][currentY - 1], currentCoordinate));
                }
                break;
    
            case '|':
                if (isValidCoordinate(currentX - 1, currentY, matrix)) {
                    result.add(new Day10Coordinate(currentX - 1, currentY, matrix[currentX - 1][currentY], currentCoordinate));
                }
                if (isValidCoordinate(currentX + 1, currentY, matrix)) {
                    result.add(new Day10Coordinate(currentX + 1, currentY, matrix[currentX + 1][currentY], currentCoordinate));
                }
                break;
    
            case '-':
                if (isValidCoordinate(currentX, currentY - 1, matrix)) {
                    result.add(new Day10Coordinate(currentX, currentY - 1, matrix[currentX][currentY - 1], currentCoordinate));
                }
                if (isValidCoordinate(currentX, currentY + 1, matrix)) {
                    result.add(new Day10Coordinate(currentX, currentY + 1, matrix[currentX][currentY + 1], currentCoordinate));
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
        File file = new File("2023/day-10/input.txt");
        List<String> stringPerLineList = new ArrayList<>();

        try {
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine())
                stringPerLineList.add(sc.nextLine());
            sc.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        char[][] mat = new char[stringPerLineList.size()][stringPerLineList.get(0).length()];

        for (int i = 0; i < stringPerLineList.size(); i++) {
            for (int j = 0; j < stringPerLineList.get(i).length(); j++) {
                mat[i][j] = stringPerLineList.get(i).charAt(j);
            }
        }

        List<Day10Coordinate>[] loopCoordinates = new ArrayList[mat.length];

        for (int i = 0; i < loopCoordinates.length; i++) {
            loopCoordinates[i] = new ArrayList<>();
        }

        Day10Coordinate start = getStartCoordinate(mat);
        int stepCount = 0;

        Stack<Day10Coordinate> nextStack = new Stack<>();
        nextStack.push(new Day10Coordinate(start.x+1, start.y, mat[start.x+1][start.y], start));

        while (!nextStack.isEmpty()) {
            Day10Coordinate x = nextStack.pop();
            if (x.step != '-')
                loopCoordinates[x.x].add(x);
            stepCount++;

            if (x.x == start.x && x.y == start.y) break;

            List<Day10Coordinate> getNextSteps = getNextSteps(x, mat);

            for (Day10Coordinate y : getNextSteps) {
                if (x.last.x != y.x || x.last.y != y.y) {
                    nextStack.push(y);
                    System.out.println("stepCount" + stepCount);
                    System.out.println(y.step);
                    //break;
                }
                
            }
        }

        //Part 1 answer:
        System.out.println(stepCount/2);


        /*
         *  Part 2 notes from https://www.reddit.com/r/adventofcode/comments/18evyu9/2023_day_10_solutions/
         * 
         *  Initialize boolean variable 'inside loop' to false
            Change state in one of the following cases:
            a vertical bar of the loop
            a NW loop corner if the previous was a SE loop corner
            a SW loop corner if the previous was a NE loop corner
            Mark a point as inside loop if the variable is true.
         * 
         * 
         * On this occasion, I didn't go for the simplest solution. I used the dijkstra algo
         * from the starting point to calculate the farthest position on the loop.
            For part 2, you can use a scanline algorithm either vertically or horizontally.
            First determine the type of starting position, then scan each line, reversing the 
            inside/outside state when an edge is detected (starting with the outside).
            A horizontal border can be the sequences '|', 'L--7', 'F--J'
         */
        
        long area = 0;

        for (int i = 0; i < loopCoordinates.length; i++) {// 2; i++) {//
            boolean insideLoop = false;
            char last = ' ';

            Collections.sort(loopCoordinates[i], (a, b) -> a.y - b.y);

            if (loopCoordinates[i].size() > 0) {
                if (loopCoordinates[i].get(0).step == '|')
                    insideLoop = true;
                last = loopCoordinates[i].get(0).step;
            }


            System.out.println("i: " + i);
            for (int j = 0; j < loopCoordinates[i].size(); j++) {
                System.out.println("y: " + loopCoordinates[i].get(j).y + " step: " + loopCoordinates[i].get(j).step);
            }

            for (int j = 1; j < loopCoordinates[i].size(); j++) {
                Day10Coordinate x = loopCoordinates[i].get(j);

                if (insideLoop && (x.step != 'J' && last != 'F') && (x.step != '7' && last != 'L')) {
                    area += Math.max(0, x.y - loopCoordinates[i].get(j-1).y - 1);
                    System.out.println("y: " + x.y + " area: " + area + " insideLoop" + insideLoop);
                }

                if (x.step == '|' || (x.step == 'J' && last == 'F') || (x.step == '7' && last == 'L'))
                    insideLoop = !insideLoop;

                last = x.step;
            }

            System.out.println("area:" + area);

        }

        //Part 2 answer
        System.out.println(area);
        
    }
}