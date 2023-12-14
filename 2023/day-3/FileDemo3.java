import java.io.*;
import java.util.*;

public class FileDemo3 {

    private static boolean isSymbol(char c) {
        if (!Character.isDigit(c) && c != '.') {
            return true;
        }

        return false;
    }

    private static void addAdjacentNumbersToList(char[][] mat, int i, int j, List<Integer> adjacentNumberList, Map<Integer, Integer>[] rootMap, Map<Integer, Integer>[] valueMap) {
        Set<Integer> alreadyAdded = new HashSet<>();
        
        if (i - 1 >= 0) {
            if (j - 1 >= 0) {
                if (Character.isDigit(mat[i-1][j-1])) {
                    if (alreadyAdded.add(valueMap[i-1].get(rootMap[i-1].get(j-1)))) {
                        adjacentNumberList.add(valueMap[i-1].get(rootMap[i-1].get(j-1)));
                    }
                }
            }
            if (Character.isDigit(mat[i-1][j])) {
                if (alreadyAdded.add(valueMap[i-1].get(rootMap[i-1].get(j)))) {
                        adjacentNumberList.add(valueMap[i-1].get(rootMap[i-1].get(j)));
                    }
            }
            if (j + 1 < mat[0].length) {
                if (Character.isDigit(mat[i-1][j+1])) {
                    if (alreadyAdded.add(valueMap[i-1].get(rootMap[i-1].get(j+1)))) {
                        adjacentNumberList.add(valueMap[i-1].get(rootMap[i-1].get(j+1)));
                    }
                }
            }
        }
        if (j - 1 >= 0) {
            if (Character.isDigit(mat[i][j-1])) {
                if (alreadyAdded.add(valueMap[i].get(rootMap[i].get(j-1)))) {
                    adjacentNumberList.add(valueMap[i].get(rootMap[i].get(j-1)));
                }
            }
        }
        if (j + 1 < mat[0].length) {
            if (Character.isDigit(mat[i][j+1])) {
                if (alreadyAdded.add(valueMap[i].get(rootMap[i].get(j+1)))) {
                    adjacentNumberList.add(valueMap[i].get(rootMap[i].get(j+1)));
                }
            }
        }

        if (i + 1 < mat.length) {
            if (j - 1 >= 0) {
                if (Character.isDigit(mat[i+1][j-1])) {
                    if (alreadyAdded.add(valueMap[i+1].get(rootMap[i+1].get(j-1)))) {
                        adjacentNumberList.add(valueMap[i+1].get(rootMap[i+1].get(j-1)));
                    }
                }
            }
            if (Character.isDigit(mat[i+1][j])) {
                if (alreadyAdded.add(valueMap[i+1].get(rootMap[i+1].get(j)))) {
                    adjacentNumberList.add(valueMap[i+1].get(rootMap[i+1].get(j)));
                }
            }
            if (j + 1 < mat[0].length) {
                if (Character.isDigit(mat[i+1][j+1])) {
                    if (alreadyAdded.add(valueMap[i+1].get(rootMap[i+1].get(j+1)))) {
                        adjacentNumberList.add(valueMap[i+1].get(rootMap[i+1].get(j+1)));
                    }
                }
            }
        }

        //System.out.println(adjacentNumberList.toString());
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
        Map<Integer, List<Integer>>[] symbolAdjacentNumberMap = new HashMap[mat.length];

        for (int i = 0; i < rootMap.length; i++) {
            rootMap[i] = new HashMap<>();
            valueMap[i] = new HashMap<>();
            symbolAdjacentNumberMap[i] = new HashMap<>();
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
                }
            }
        }

        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[0].length; j++) {
                if (isSymbol(mat[i][j])) {
                    List<Integer> adjacentNumberList = new ArrayList<>();
                    addAdjacentNumbersToList(mat, i, j, adjacentNumberList, rootMap, valueMap);
                    symbolAdjacentNumberMap[i].put(j, adjacentNumberList);
                }
            }
        }

        for (int i = 0; i < symbolAdjacentNumberMap.length; i++) {
            for (Map.Entry<Integer, List<Integer>> entry : symbolAdjacentNumberMap[i].entrySet()) {
                //int key = entry.getKey();
                List<Integer> values = entry.getValue();

                if (values.size() == 2) { //exactly 2 part numbers per gear
                    int product = values.get(0) * values.get(1);
                    ret += product;
                }
            }
        }

        System.out.println(ret);
    }
}