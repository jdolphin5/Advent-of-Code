import java.io.*;
import java.util.*;

public class FileDemo12 {

    private static long calculateOptions(List<Character> arrangementList, List<Integer> groupIntegersList, int i, int j, int k, int last, String chars) {
        long ret = 0;

        if (k == 0) {
            if (j < groupIntegersList.size() && last == groupIntegersList.get(j)) {
                return calculateOptions(arrangementList, groupIntegersList, i, j+1, 0, 0, chars);
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
                return calculateOptions(arrangementList, groupIntegersList, i, j+1, 0, 0, chars);
            }
        }

        if (i == arrangementList.size()) {
            if (j == groupIntegersList.size() && k == 0 && last == 0) {
                System.out.println(chars);
                return 1;
            }

            return 0;
        }

        if (arrangementList.get(i) == '?') {
            ret += calculateOptions(arrangementList, groupIntegersList, i+1, j, k+1, 0, chars + "#");
            ret += calculateOptions(arrangementList, groupIntegersList, i+1, j, 0, k, chars + ".");
        }
        else if (arrangementList.get(i) == '#') {
            ret += calculateOptions(arrangementList, groupIntegersList, i+1, j, k+1, 0, chars + "#");
        }
        else {
            ret += calculateOptions(arrangementList, groupIntegersList, i+1, j, 0, k,  chars + ".");
        }
        
        return ret;
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

            ret += calculateOptions(rowArrangementLists[i], rowGroupLists[i], 0, 0, 0, 0, "");
            System.out.println(ret);
        }

        System.out.println("Part 1 Answer: " + ret);
    }
}
