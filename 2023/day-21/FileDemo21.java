import java.io.*;
import java.util.*;

public class FileDemo21 {
    private static long countPositions(boolean[][] mat) {
        long ret = 0;

        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[0].length; j++) {
                if (mat[i][j]) ret++;
            }
        }

        return ret;
    }

    private static void recur(char[][] mat, boolean[][] afterMat, boolean[][][] visited, int i, int j, int steps) {
        if (i < 0 || i >= mat.length || j < 0 || j >= mat[0].length || visited[i][j][steps] || mat[i][j] == '#') {
            return;
        }
        
        if (steps == 0) {
            afterMat[i][j] = true;
            return;
        }

        visited[i][j][steps] = true;

        recur(mat, afterMat, visited, i+1, j, steps-1);
        recur(mat, afterMat, visited, i-1, j, steps-1);
        recur(mat, afterMat, visited, i, j+1, steps-1);
        recur(mat, afterMat, visited, i, j-1, steps-1);

    }
    public static void main(String[] args) {
        File file = new File("2023/day-21/input.txt");
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

        int startI = -1;
        int startJ = -1;

        outerloop:
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[0].length; j++) {
                if (mat[i][j] == 'S') {
                    startI = i;
                    startJ = j;
                    break outerloop;
                }
            }
        }

        char[][] mat2 = new char[mat.length*9][mat[0].length*9];
        for (int i = 0; i < mat2.length; i++) {
            for (int j = 0; j < mat2[0].length; j++) {
                mat2[i][j] = mat[i % mat.length][j % mat[0].length];
            }
        }

        startI = startI + 4 * mat.length;
        startJ = startJ + 4 * mat[0].length;

        long ret = 0;

        boolean[][] afterMat = new boolean[mat2.length][mat2[0].length];
        boolean[][][] visited = new boolean[mat2.length][mat2[0].length][400];

        recur(mat2, afterMat, visited, startI, startJ, 65);

        ret = countPositions(afterMat);

        System.out.println(ret);

        boolean[][] afterMat2 = new boolean[mat2.length][mat2[0].length];
        boolean[][][] visited2 = new boolean[mat2.length][mat2[0].length][400];

        recur(mat2, afterMat2, visited2, startI, startJ, 131+65);

        //26501365 steps = 202300 + 65


        ret = countPositions(afterMat2);

        System.out.println(ret);


        boolean[][] afterMat3 = new boolean[mat2.length][mat2[0].length];
        boolean[][][] visited3 = new boolean[mat2.length][mat2[0].length][400];

        recur(mat2, afterMat3, visited3, startI, startJ, 131+131+65);

        ret = countPositions(afterMat3);

        System.out.println(ret);

        //26501365 steps = 202300 + 65
        //               = 202301 iterations

        //Quadratic formula modelling the pattern using the results from (x = 65, x = 65 + 131, x = 65 + 131*2)
        //3853 - 15527 x + 15615 x^2
        //x = 202301
        //x^2 = 40925694601

        System.out.println(3853L - (15527L * 202301L) + (15615L * 40925694601L));
    }
}