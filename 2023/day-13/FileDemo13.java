import java.io.*;
import java.util.*;

public class FileDemo13 {

    private static boolean isMirroredRow(char[][] mat, int i, int j) {
        if (i >= 0 && i < mat.length && j >= 0 && j < mat.length) {
            for (int x = 0; x < mat[0].length; x++) {
                if (mat[i][x] != mat[j][x])
                    return false;
            }

            return isMirroredRow(mat, i-1, j+1);
        }

        return true;
    }

    private static boolean isMirroredCol(char[][] mat, int i, int j) {
        if (i >= 0 && i < mat[0].length && j >= 0 && j < mat[0].length) {
            for (int x = 0; x < mat.length; x++) {
                if (mat[x][i] != mat[x][j])
                    return false;
            }

            return isMirroredCol(mat, i-1, j+1);
        }

        return true;
    }

    private static long summarisePattern(Pattern pattern) {
        long ret = 0;

        for (int i = 0; i < pattern.mat.length-1; i++) {
            if (isMirroredRow(pattern.mat, i, i+1)) {
                System.out.println("i: " + i);
                ret += 100 * (i+1);
            }
        }
        
        for (int i = 0; i < pattern.mat[0].length-1; i++) {
            if (isMirroredCol(pattern.mat, i, i+1)) {
                System.out.println("j: " + i);
                ret += (i+1);
            }
        }

        return ret;
    }
    public static void main(String[] args) {
        File file = new File("2023/day-13/input.txt");
        List<String> stringPerLineList = new ArrayList<>();

        try {
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine())
                stringPerLineList.add(sc.nextLine());
            sc.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }


        List<Pattern> patternList = new ArrayList<>();
        List<List<Character>> rowsCharList = new ArrayList<>();

        for (int i = 0; i < stringPerLineList.size(); i++) {
            String s = stringPerLineList.get(i);
            if (s.equals("")) {
                patternList.add(new Pattern(rowsCharList));
                rowsCharList = new ArrayList<>();
            }
            else {
                List<Character> row = new ArrayList<>();
                for (int j = 0; j < s.length(); j++) {
                    row.add(s.charAt(j));
                }

                rowsCharList.add(row);
            }
        }
        patternList.add(new Pattern(rowsCharList));

        long ret = 0;

        for (int i = 0; i < patternList.size(); i++) {
            System.out.println("pattern no: " + i);
            ret += summarisePattern(patternList.get(i));
        }

        System.out.println(ret);
    }
}
