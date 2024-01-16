import java.io.*;
import java.util.*;

public class FileDemo14 {

    private static void tiltNorth(char[][] mat) {
        for (int j = 0; j < mat[0].length; j++) {
            for (int i = 1; i < mat.length; i++) {
                if (i == 0 || mat[i-1][j] == '#' || mat[i-1][j] == 'O') {
                    //do nothing
                }
                else {
                    if (mat[i][j] == 'O') {
                        mat[i][j] = '.';
                        mat[i-1][j] = 'O';
                        i-=2;
                    }
                }
            }
        }
    }

    private static long countWeight(char[][] mat) {
        long ret = 0;

        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[0].length; j++) {
                if (mat[i][j] == 'O') {
                    ret += (mat.length - i);
                }
            }
        }

        return ret;
    }

    private static void rotate90Clockwise(char[][] a) {
    
        int n = a.length;
        int m = a[0].length;

        // Traverse each cycle
        for (int i = 0; i < n / 2; i++) {
            for (int j = i; j < m - i - 1; j++) {
                // Swap elements of each cycle
                // in clockwise direction
                char temp = a[i][j];
                a[i][j] = a[m - 1 - j][i];
                a[m - 1 - j][i] = a[n - 1 - i][m - 1 - j];
                a[n - 1 - i][m - 1 - j] = a[j][n - 1 - i];
                a[j][n - 1 - i] = temp;
            }
        }
    }

    private static void cloneCharArrays(char[][] arr1, char[][] arr2) {
        for (int i = 0; i < arr1.length; i++) {
            for (int j = 0; j < arr1[0].length; j++) {
                arr2[i][j] = arr1[i][j];
            }
        }
    }

    private static void cycle(char[][] mat) {
        tiltNorth(mat);         //tilt north
        rotate90Clockwise(mat);
        tiltNorth(mat);         //tilt east
        rotate90Clockwise(mat);
        tiltNorth(mat);         //tilt south
        rotate90Clockwise(mat);
        tiltNorth(mat);         //tilt west
        rotate90Clockwise(mat); //rotate back to north but do not tilt
    }

    private static int checkContains(List<char[][]> charArrList, char[][] mat) {
        for (int i = 0; i < charArrList.size(); i++) {
            boolean found = true;
            char[][] mat2 = charArrList.get(i);

            outerloop:
            for (int x = 0; x < mat.length; x++) {
                for (int y = 0; y < mat[0].length; y++) {
                    if (mat[x][y] != mat2[x][y]) {
                        found = false;
                        break outerloop;
                    }
                }
            }

            if (found) {
                return i+1;     //offset as t starts at 1 so i == 0 corresponds to mat after first cycle
            }

        }

        return -1;
    }

    public static void main(String[] args) {
        File file = new File("2023/day-14/input.txt");
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

        List<char[][]> matList = new ArrayList<>();

        final int TARGET_CYCLES = 1000000000;

        for (long t = 1; t <= TARGET_CYCLES; t++) {
            cycle(mat);
            char[][] matCopy = new char[mat.length][mat[0].length];
            cloneCharArrays(mat, matCopy);

            int foundRepeat = checkContains(matList, matCopy);

            if (foundRepeat != -1) {
                long cyclesBetweenRepeat = t - foundRepeat;

                while (t + cyclesBetweenRepeat < TARGET_CYCLES) {
                    t = t + cyclesBetweenRepeat;
                }
            }

            matList.add(matCopy);
        }
        

        //for (int i = 0; i < mat.length; i++) {
        //    System.out.println(Arrays.toString(mat[i]));
        //}

        long ret = countWeight(mat);

        System.out.println(ret);
    }
    
}
