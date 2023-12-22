import java.io.*;
import java.util.*;

public class FileDemo10 {
    private static Coordinate getStartCoordinate(char[][] mat) {
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[0].length; j++) {
                if (mat[i][j] == 'S')
                    return new Coordinate(i, j, 'S', new Coordinate(-1, -1, 'X', null));
            }
        }

        return new Coordinate(0, 0, 'S', null);
    }

    private static List<Coordinate> getNextSteps(Coordinate currentCoordinate, char[][] matrix) {
        List<Coordinate> result = new ArrayList<>();
    
        int currentX = currentCoordinate.x;
        int currentY = currentCoordinate.y;
        char currentChar = matrix[currentX][currentY];
    
        switch (currentChar) {
            case 'F':
                if (isValidCoordinate(currentX + 1, currentY, matrix)) {
                    result.add(new Coordinate(currentX + 1, currentY, matrix[currentX + 1][currentY], currentCoordinate));
                }
                if (isValidCoordinate(currentX, currentY + 1, matrix)) {
                    result.add(new Coordinate(currentX, currentY + 1, matrix[currentX][currentY + 1], currentCoordinate));
                }
                break;
    
            case 'J':
                if (isValidCoordinate(currentX - 1, currentY, matrix)) {
                    result.add(new Coordinate(currentX - 1, currentY, matrix[currentX - 1][currentY], currentCoordinate));
                }
                if (isValidCoordinate(currentX, currentY - 1, matrix)) {
                    result.add(new Coordinate(currentX, currentY - 1, matrix[currentX][currentY - 1], currentCoordinate));
                }
                break;
    
            case 'L':
                if (isValidCoordinate(currentX - 1, currentY, matrix)) {
                    result.add(new Coordinate(currentX - 1, currentY, matrix[currentX - 1][currentY], currentCoordinate));
                }
                if (isValidCoordinate(currentX, currentY + 1, matrix)) {
                    result.add(new Coordinate(currentX, currentY + 1, matrix[currentX][currentY + 1], currentCoordinate));
                }
                break;
    
            case '7':
                if (isValidCoordinate(currentX + 1, currentY, matrix)) {
                    result.add(new Coordinate(currentX + 1, currentY, matrix[currentX + 1][currentY], currentCoordinate));
                }
                if (isValidCoordinate(currentX, currentY - 1, matrix)) {
                    result.add(new Coordinate(currentX, currentY - 1, matrix[currentX][currentY - 1], currentCoordinate));
                }
                break;
    
            case '|':
                if (isValidCoordinate(currentX - 1, currentY, matrix)) {
                    result.add(new Coordinate(currentX - 1, currentY, matrix[currentX - 1][currentY], currentCoordinate));
                }
                if (isValidCoordinate(currentX + 1, currentY, matrix)) {
                    result.add(new Coordinate(currentX + 1, currentY, matrix[currentX + 1][currentY], currentCoordinate));
                }
                break;
    
            case '-':
                if (isValidCoordinate(currentX, currentY - 1, matrix)) {
                    result.add(new Coordinate(currentX, currentY - 1, matrix[currentX][currentY - 1], currentCoordinate));
                }
                if (isValidCoordinate(currentX, currentY + 1, matrix)) {
                    result.add(new Coordinate(currentX, currentY + 1, matrix[currentX][currentY + 1], currentCoordinate));
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

        Coordinate start = getStartCoordinate(mat);
        int stepCount = 0;

        Stack<Coordinate> nextStack = new Stack<>();
        nextStack.push(new Coordinate(start.x+1, start.y, mat[start.x+1][start.y], start));

        while (!nextStack.isEmpty()) {
            Coordinate x = nextStack.pop();
            stepCount++;

            if (x.x == start.x && x.y == start.y) break;

            List<Coordinate> getNextSteps = getNextSteps(x, mat);

            for (Coordinate y : getNextSteps) {
                if (x.last.x != y.x || x.last.y != y.y) {
                    nextStack.push(y);
                    System.out.println("stepCount" + stepCount);
                    System.out.println(y.step);
                    //break;
                }
                
            }
        }

        System.out.println(stepCount/2);
    }
}