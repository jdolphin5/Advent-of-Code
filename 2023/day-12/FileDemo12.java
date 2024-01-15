import java.io.*;
import java.util.*;

public class FileDemo12 {

    private static long calculateOptions(Long[][][][] dp, List<Character> arrangementList, List<Integer> groupIntegersList, int i, int j, int k, int last) {
        long ret = 0;

        if (dp[i][j][k][last] != null) {
            return dp[i][j][k][last];
        }

        if (k == 0) {
            if (j < groupIntegersList.size() && last == groupIntegersList.get(j)) {
                return calculateOptions(dp, arrangementList, groupIntegersList, i, j+1, 0, 0);
            }
            else if (last > 0) {
                if (j < groupIntegersList.size() && last != groupIntegersList.get(j))
                    return 0;
            }
        }
        else {
            if (j == groupIntegersList.size())
                return 0;
        }

        if (i == arrangementList.size()) {
            if (j < groupIntegersList.size() && k == groupIntegersList.get(j)) {
                return calculateOptions(dp, arrangementList, groupIntegersList, i, j+1, 0, 0);
            }
        }

        if (i == arrangementList.size()) {
            if (j == groupIntegersList.size() && k == 0 && last == 0) {
                return 1;
            }

            return 0;
        }

        if (arrangementList.get(i) == '?') {
            ret += calculateOptions(dp, arrangementList, groupIntegersList, i+1, j, k+1, 0);
            ret += calculateOptions(dp, arrangementList, groupIntegersList, i+1, j, 0, k);
        }
        else if (arrangementList.get(i) == '#') {
            ret += calculateOptions(dp, arrangementList, groupIntegersList, i+1, j, k+1, 0);
        }
        else {
            ret += calculateOptions(dp, arrangementList, groupIntegersList, i+1, j, 0, k);
        }
        
        return dp[i][j][k][last] = ret;
    }
    public static void main(String[] args) {
        File file = new File("2023/day-12/input.txt");
        List<String> stringPerLineList = new ArrayList<>();

        try {
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine())
                stringPerLineList.add(sc.nextLine());
            sc.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        int lineCount = stringPerLineList.size();

        List<Character>[] rowArrangementLists = new ArrayList[lineCount];
        List<Integer>[] rowGroupLists = new ArrayList[lineCount];

        long ret = 0;

        for (int i = 0; i < lineCount; i++) {
            //int i = 3;
            rowArrangementLists[i] = new ArrayList<>();
            rowGroupLists[i] = new ArrayList<>();

            String line = stringPerLineList.get(i);
            String[] split1 = line.split(" ", 0);

            for (int j = 0; j < split1[0].length(); j++) {
                rowArrangementLists[i].add(split1[0].charAt(j));
            }

            String[] split2 = split1[1].split(",", 0);

            for (int j = 0; j < split2.length; j++) {
                rowGroupLists[i].add(Integer.parseInt(split2[j]));
            }

            List<Character> rowArrangementListRepeated = new ArrayList<>();
            List<Integer> rowGroupListRepeated = new ArrayList<>();

            for (int x = 0; x < 5; x++) {

                for (int y = 0; y < rowArrangementLists[i].size(); y++) {
                    rowArrangementListRepeated.add(rowArrangementLists[i].get(y));
                }
                
                if (x < 4)
                    rowArrangementListRepeated.add('?');

                for (int z = 0; z < rowGroupLists[i].size(); z++) {
                    rowGroupListRepeated.add(rowGroupLists[i].get(z));
                }
            }

            System.out.println(rowArrangementListRepeated.toString());

            Long[][][][] dp = new Long[rowArrangementListRepeated.size()+1][rowGroupListRepeated.size()+1][rowArrangementListRepeated.size()+1][rowArrangementListRepeated.size()+1];

            ret += calculateOptions(dp, rowArrangementListRepeated, rowGroupListRepeated, 0, 0, 0, 0);
            System.out.println(ret);
        }

        //Part 1 - Backtracking solution
        //Part 2 - Memoisation / DP solution

        System.out.println("Part 2 Answer: " + ret);
    }
}
