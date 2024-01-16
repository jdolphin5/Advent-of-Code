import java.io.*;
import java.util.*;

public class FileDemo13 {

    private static boolean isMirroredRow(char[][] mat, int i, int j, int smudgeI, int smudgeJ) {
        if (i >= 0 && i < mat.length && j >= 0 && j < mat.length) {
            for (int x = 0; x < mat[0].length; x++) {
                if (mat[i][x] != mat[j][x])
                    return false;
            }

            if (i == smudgeI || j == smudgeI)
                smudgeI = -1;

            return isMirroredRow(mat, i-1, j+1, smudgeI, smudgeJ);
        }

        return smudgeI == -1;
    }

    private static boolean isMirroredCol(char[][] mat, int i, int j, int smudgeI, int smudgeJ) {
        if (i >= 0 && i < mat[0].length && j >= 0 && j < mat[0].length) {
            for (int x = 0; x < mat.length; x++) {
                if (mat[x][i] != mat[x][j])
                    return false;
            }

            if (i == smudgeJ || j == smudgeJ)
                smudgeJ = -1;

            return isMirroredCol(mat, i-1, j+1, smudgeI, smudgeJ);
        }

        return smudgeJ == -1;
    }

    private static long summarisePattern(Pattern pattern) {
        long ret = 0;

        for (int i = 0; i < pattern.mat.length-1; i++) {
            if (isMirroredRow(pattern.mat, i, i+1, pattern.smudgeI, pattern.smudgeJ)) {
                //System.out.println("i: " + i);
                ret += 100 * (i+1);
            }
        }
        
        for (int i = 0; i < pattern.mat[0].length-1; i++) {
            if (isMirroredCol(pattern.mat, i, i+1, pattern.smudgeI, pattern.smudgeJ)) {
                //System.out.println("j: " + i);
                ret += (i+1);
            }
        }

        return ret;
    }

    private static void cloneCharArrays(char[][] arr1, char[][] arr2) {
        for (int i = 0; i < arr1.length; i++) {
            for (int j = 0; j < arr1[0].length; j++) {
                arr2[i][j] = arr1[i][j];
            }
        }
    }

    private static List<Pattern> generatePossiblePatterns(Pattern pattern) {
        List<Pattern> ret = new ArrayList<>();

        for (int i = 0; i < pattern.mat.length; i++) {
            for (int j = 0; j < pattern.mat[0].length; j++) {
                if (pattern.mat[i][j] == '#') {
                    /*
                     * 
                     *      Using Object.clone() is the issue. I need to create a new 2D array and copy each value individually
                            Java array internally implements a Cloneable interface and thus it is easy to clone a Java array.
                            You can clone one-dimensional as well as two-dimensional arrays. When you clone one-dimensional array,
                            it makes a deep copy of array elements which is copying the values.

                            On the other hand, when you clone two dimensional or multi-dimensional arrays, a shallow copy of elements is made i.e.
                            only references are copied. This cloning of arrays is done by the ‘Clone ()’ method provided by the arrays.
                     */

                    char[][] matCopy = new char[pattern.mat.length][pattern.mat[0].length];
                    cloneCharArrays(pattern.mat, matCopy);
                    matCopy[i][j] = '.';
                    ret.add(new Pattern(matCopy, i, j));
                }
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

        //for (int i = 0; i < patternList.size(); i++) {
            //System.out.println("pattern no: " + i);
            //ret += summarisePattern(patternList.get(i));
        //}

        //Part 1 Answer
        //System.out.println(ret);

        //Part 2

        for (int i = 0; i < patternList.size(); i++) {//i < 1; i++) {//
            System.out.println("pattern no: " + i);
            List<Pattern> potentialDifferentPatternList = generatePossiblePatterns(patternList.get(i));

            for (int j = 0; j < potentialDifferentPatternList.size(); j++) {
                ret += summarisePattern(potentialDifferentPatternList.get(j));
                System.out.println("j: " + j + " ret : " + ret);
                System.out.println(Arrays.deepToString(potentialDifferentPatternList.get(j).mat));
            }
        }

        System.out.println(ret);
    }
}
