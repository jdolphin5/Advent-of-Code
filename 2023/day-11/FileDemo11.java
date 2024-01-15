import java.io.*;
import java.util.*;

public class FileDemo11 {

    private static void populateEmptyRow(char[][] mat, int i) {
        for (int x = mat.length-1; x > i; x--) {
            for (int j = 0; j < mat[0].length; j++) {
                mat[x][j] = mat[x-1][j];
            }
        }
        for (int j = 0; j < mat[0].length; j++) {
            mat[i][j] = '.';
        }
    }

    private static void populateEmptyCol(char[][] mat, int j) {
        for (int x = mat[0].length-1; x > j; x--) {
            for (int i = 0; i < mat.length; i++) {
                mat[i][x] = mat[i][x-1];
            }
        }

        for (int i = 0; i < mat.length; i++) {
            mat[i][j] = '.';
        }
    }

    public static void main(String[] args) {
        File file = new File("2023/day-11/input.txt");
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
         * 
         *  Sum of the shortest path from each galaxy to each other galaxy
         *  Need to expand the input first (double size of empty rows and cols)
         *  Can map the input into a 2-D char matrix, noting galaxy coordinates,
         *  the then use abs(x1-x2) + abs(y1-y2) to find distance between each galaxy.
         * 
         */

         List<Integer> emptyRows = new ArrayList<>();
         List<Integer> emptyCols = new ArrayList<>();

         int lineLength = stringPerLineList.get(0).length();

         //find empty rows
         for (int i = 0; i < stringPerLineList.size(); i++) {
            int emptyCount = 0;
            
            for (int j = 0; j < lineLength; j++) {
                if (stringPerLineList.get(i).charAt(j) == '.') {
                    emptyCount++;
                }
            }

            if (emptyCount == lineLength) {
                emptyRows.add(i);
            }
         }

         //find empty cols
         for (int i = 0; i < lineLength; i++) {
            int emptyCount = 0;

            for (int j = 0; j < stringPerLineList.size(); j++) {
                if (stringPerLineList.get(j).charAt(i) == '.') {
                    emptyCount++;
                }
            }

            if (emptyCount == lineLength) {
                emptyCols.add(i);
            }
         }

         //populate char matrix
         char[][] mat = new char[stringPerLineList.size() + emptyRows.size()][lineLength + emptyCols.size()];
         
         for (int i = 0; i < stringPerLineList.size(); i++) {
            for (int j = 0; j < lineLength; j++) {
                mat[i][j] = stringPerLineList.get(i).charAt(j);
            }
         }

        //expand rows
        int rowOffset = 0;
        for (int i = 0; i < mat.length; i++) {
            if (emptyRows.contains(i - rowOffset)) {
                populateEmptyRow(mat, i);
                rowOffset++;
                emptyRows.remove(0);
                System.out.println(i);
            }
        }

        //expand cols
        int colOffset = 0;
        for (int j = 0; j < mat[0].length; j++) {
            if (emptyCols.contains(j - colOffset)) {
                populateEmptyCol(mat, j);
                colOffset++;
                emptyCols.remove(0);
                System.out.println("j: " + j);
            }
        }

        for (int i = 0; i < mat.length; i++) {
            System.out.println("row: " + i);
            System.out.println(mat[i]);
        }

        List<Coordinate> galaxyCoordinatesList = new ArrayList<>();

        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[0].length; j++) {
                if (mat[i][j] == '#') {
                    galaxyCoordinatesList.add(new Coordinate(i, j));
                }
            }
        }

        int ret = 0;

        for (int i = 0; i < galaxyCoordinatesList.size(); i++) {
            for (int j = i+1; j < galaxyCoordinatesList.size(); j++) {
                Coordinate a = galaxyCoordinatesList.get(i);
                Coordinate b = galaxyCoordinatesList.get(j);

                ret += Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
            }
        }

        System.out.println(ret);

    }
}
