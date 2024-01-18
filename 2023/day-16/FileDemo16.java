import java.io.*;
import java.util.*;

public class FileDemo16 {

    private static long countEnergised(char[][] litMat) {
        long ret = 0;
        
        for (int i = 0; i < litMat.length; i++) {
            for (int j = 0; j < litMat[0].length; j++) {
                if (litMat[i][j] == '#') {
                    ret++;
                }
            }
        }

        return ret;
    }

    private static void recur(Boolean[][][] dp, char[][] mat, char[][] litMat, int i, int j, int dir) {
        //do not count repeats
        if (i < 0 || j < 0 || i >= mat.length || j >= mat[0].length || dp[i][j][dir] != null)
            return;

        char c = mat[i][j];
        litMat[i][j] = '#';
        dp[i][j][dir] = true;
        //System.out.println("i: " + i + " j: " + j + "dir: " + dir);

        switch (c) {
            case '-':
                if (dir == 0) {
                    recur(dp, mat, litMat, i, j+1, 1);
                    recur(dp, mat, litMat, i, j-1, 3);
                }
                else if (dir == 1) {
                    recur(dp, mat, litMat, i, j+1, dir);
                }
                else if (dir == 2) {
                    recur(dp, mat, litMat, i, j+1, 1);
                    recur(dp, mat, litMat, i, j-1, 3);
                }
                else {
                    recur(dp, mat, litMat, i, j-1, dir);
                }
                break;
            case '|':
                if (dir == 0) {
                    recur(dp, mat, litMat, i-1, j, dir);
                }
                else if (dir == 1) {
                    recur(dp, mat, litMat, i-1, j, 0);
                    recur(dp, mat, litMat, i+1, j, 2);
                }
                else if (dir == 2) {
                    recur(dp, mat, litMat, i+1, j, dir);
                }
                else {
                    recur(dp, mat, litMat, i-1, j, 0);
                    recur(dp, mat, litMat, i+1, j, 2);
                }
                break;
            case '\\':
                //clockwise
                if (dir == 0) {
                    recur(dp, mat, litMat, i, j-1, 3);
                }
                else if (dir == 1) {
                    recur(dp, mat, litMat, i+1, j, 2);
                }
                else if (dir == 2) {
                    recur(dp, mat, litMat, i, j+1, 1);
                }
                else {
                    recur(dp, mat, litMat, i-1, j, 0);
                }
                break;
            case '/':
                if (dir == 0) {
                    recur(dp, mat, litMat, i, j+1, 1);
                }
                else if (dir == 1) {
                    recur(dp, mat, litMat, i-1, j, 0);
                }
                else if (dir == 2) {
                    recur(dp, mat, litMat, i, j-1, 3);
                }
                else {
                    recur(dp, mat, litMat, i+1, j, 2);
                }
                break;
            case '.':
                if (dir == 0) {
                    recur(dp, mat, litMat, i-1, j, dir);
                }
                else if (dir == 1) {
                    recur(dp, mat, litMat, i, j+1, dir);
                }
                else if (dir == 2) {
                    recur(dp, mat, litMat, i+1, j, dir);
                }
                else {
                    recur(dp, mat, litMat, i, j-1, dir);
                }
                break;

            default:
                
        }

        //dir: 0 - north, 1 - east, 2 - south, 3 - west
    }

    public static void main(String[] args) {
        File file = new File("2023/day-16/input.txt");
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

        Boolean[][][] dp = new Boolean[mat.length][mat[0].length][4];

        long ret = 0;

        char[][] litMat = new char[mat.length][mat[0].length];

        recur(dp, mat, litMat, 0, 0, 1);

        System.out.println(Arrays.deepToString(litMat));

        ret = countEnergised(litMat);

        System.out.println(ret);

    }

}
