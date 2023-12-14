import java.io.*;
import java.util.*;

public class FileDemo3 {

    private static boolean isSymbol(char c) {
        if (!Character.isDigit(c) && c != '.') {
            return true;
        }

        return false;
    }

    private static boolean hasAdjacentSymbol(char[][] mat, int i, int j) {
        if (i - 1 >= 0) {
            if (j - 1 >= 0) {
                if (isSymbol(mat[i-1][j-1])) {
                    return true;
                }
            }
            if (isSymbol(mat[i-1][j])) {
                return true;
            }
            if (j + 1 < mat[0].length) {
                if (isSymbol(mat[i-1][j+1])) {
                    return true;
                }
            }
        }
        if (j - 1 >= 0) {
            if (isSymbol(mat[i][j-1])) {
                return true;
            }
        }
        if (isSymbol(mat[i][j])) {
            return true;
        }
        if (j + 1 < mat[0].length) {
            if (isSymbol(mat[i][j+1])) {
                return true;
            }
        }

        if (i + 1 < mat.length) {
            if (j - 1 >= 0) {
                if (isSymbol(mat[i+1][j-1])) {
                    return true;
                }
            }
            if (isSymbol(mat[i+1][j])) {
                return true;
            }
            if (j + 1 < mat[0].length) {
                if (isSymbol(mat[i+1][j+1])) {
                    return true;
                }
            }
        }

        return false;
    }
    public static void main(String[] args) {
        File file = new File("2023/day-3/input.txt");
        List<String> stringPerLineList = new ArrayList<>();

        try {
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine())
                stringPerLineList.add(sc.nextLine());
            sc.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        int lines = stringPerLineList.size();


        int ret = 0;
        char[][] mat = new char[lines][stringPerLineList.get(0).length()];

        for (int i = 0; i < lines; i++) {
            String line = stringPerLineList.get(i);

            for (int j = 0; j < line.length(); j++) {
                mat[i][j] = line.charAt(j);
            }
        }

        Map<Integer, Integer>[] rootMap = new HashMap[mat.length];
        Map<Integer, Integer>[] valueMap = new HashMap[mat.length];
        boolean[][] isPartNumber = new boolean[mat.length][mat[0].length];

        for (int i = 0; i < rootMap.length; i++) {
            rootMap[i] = new HashMap<>();
            valueMap[i] = new HashMap<>();
        }

        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[0].length; j++) {
                if (Character.isDigit(mat[i][j])) {
                    if ((j > 0 && !Character.isDigit(mat[i][j-1])) || j == 0) {
                        rootMap[i].put(j, j);
                        valueMap[i].put(j, Character.getNumericValue(mat[i][j]));
                    }
                    else if (j > 0 && Character.isDigit(mat[i][j-1])) {
                        rootMap[i].put(j, rootMap[i].get(j-1));
                        valueMap[i].put(rootMap[i].get(j), (valueMap[i].get(rootMap[i].get(j)) * 10) + Character.getNumericValue(mat[i][j]));
                    }
                    if (hasAdjacentSymbol(mat, i, j)) {
                        isPartNumber[i][rootMap[i].get(j)] = true;
                    }
                }
                
            }
        }

        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[0].length; j++) {
                if (isPartNumber[i][j]) {
                    ret += valueMap[i].get(j);
                }
            }
        }

        System.out.println(ret);
    }
}