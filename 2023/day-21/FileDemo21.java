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

        boolean[][] afterMat = new boolean[mat.length][mat[0].length];
        boolean[][][] visited = new boolean[mat.length][mat[0].length][65];

        recur(mat, afterMat, visited, startI, startJ, 64);

        long ret = 0;

        ret = countPositions(afterMat);

        System.out.println(ret);
    }
}