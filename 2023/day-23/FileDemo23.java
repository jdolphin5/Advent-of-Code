import java.io.*;
import java.util.*;

public class FileDemo23 {

    private static long recur(char[][] mat, boolean[][] visited, int i, int j) {
        long ret = 0;

        if (i < 0 || i >= mat.length || j < 0 || j >= mat[0].length || visited[i][j] || mat[i][j] == '#') {
            return Integer.MIN_VALUE;
        }

        visited[i][j] = true;

        if (i == mat.length-1) {
            return 0;
        }

        System.out.println("i: "+  i + " j: " + j + " mat: " + mat[i][j]);

        if (mat[i][j] == '.' || mat[i][j] == '^') {
            boolean[][] visitedCopy = new boolean[mat.length][mat[0].length];
            for (int x = 0; x < visited.length; x++) {
                for (int y = 0; y < visited[0].length; y++) {
                    visitedCopy[x][y] = visited[x][y];
                }
            }
            ret = Math.max(ret, 1 + recur(mat, visitedCopy, i-1, j));
        }
        if (mat[i][j] == '.' || mat[i][j] == '>') {
            boolean[][] visitedCopy = new boolean[mat.length][mat[0].length];
            for (int x = 0; x < visited.length; x++) {
                for (int y = 0; y < visited[0].length; y++) {
                    visitedCopy[x][y] = visited[x][y];
                }
            }
            ret = Math.max(ret, 1 + recur(mat, visitedCopy, i, j+1));
        }
        if (mat[i][j] == '.' || mat[i][j] == '<') {
            boolean[][] visitedCopy = new boolean[mat.length][mat[0].length];
            for (int x = 0; x < visited.length; x++) {
                for (int y = 0; y < visited[0].length; y++) {
                    visitedCopy[x][y] = visited[x][y];
                }
            }
            ret = Math.max(ret, 1 + recur(mat, visitedCopy, i, j-1));
        }
        if (mat[i][j] == '.' || mat[i][j] == 'v') {
            boolean[][] visitedCopy = new boolean[mat.length][mat[0].length];
            for (int x = 0; x < visited.length; x++) {
                for (int y = 0; y < visited[0].length; y++) {
                    visitedCopy[x][y] = visited[x][y];
                }
            }
            ret = Math.max(ret, 1 + recur(mat, visitedCopy, i+1, j));
        }

        return ret;
    }
    public static void main(String[] args) {
        File file = new File("2023/day-23/input.txt");
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

        boolean[][] visited = new boolean[mat.length][mat[0].length];

        long ret = recur(mat, visited, 0, 1);

        System.out.println(ret);
    }
}
