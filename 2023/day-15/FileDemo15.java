import java.io.*;
import java.util.*;

public class FileDemo15 {
    public static void main(String[] args) {
        File file = new File("2023/day-15/input.txt");
        List<String> stringPerLineList = new ArrayList<>();

        try {
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine())
                stringPerLineList.add(sc.nextLine());
            sc.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        List<String> initialisationSequence = new ArrayList<>();

        for (int i = 0; i < stringPerLineList.size(); i++) {
            String[] lineStrings = stringPerLineList.get(i).split(",", 0);

            for (String s : lineStrings) {
                if (s.length() > 0) {
                    initialisationSequence.add(s);
                }
            }
        }

        long ret = 0;

        for (String s : initialisationSequence) {
            long cur = 0;

            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                cur += (int)c;
                cur *= 17;
                cur %= 256;
            }

            ret += cur;
            System.out.println(ret);
        }

    }
}
