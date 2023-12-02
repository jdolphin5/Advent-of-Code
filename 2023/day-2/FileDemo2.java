import java.io.*;
import java.util.*;

public class FileDemo2 {
    public static void main(String[] args) {
        File file = new File("2023/day-2/input.txt");
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

        //final int MAX_BLUE = 14;
        //final int MAX_GREEN = 13;
        //final int MAX_RED = 12;

        int ret = 0;

        for (int i = 0; i < lines; i++) {
            String[] arrOfStr1 = stringPerLineList.get(i).split(": ", 0);
            System.out.println(arrOfStr1[1]);
            String[] arrOfStr2 = arrOfStr1[1].split("; ", 0);

            int maxBlue = 0;
            int maxGreen = 0;
            int maxRed = 0;

            for (String s : arrOfStr2) {
                String[] arrOfStringS = s.split(", ", 0);

                for (String t : arrOfStringS) {
                    String[] r = t.split(" ", 0);
                    int val = Integer.parseInt(r[0]);
                    //int val = 0;
                    //System.out.println(r[0].trim());
                    System.out.println(r[1]);

                    if (r[1].contains("blue")) {
                        maxBlue = Math.max(maxBlue, val);
                    }
                    else if (r[1].contains("green")) {
                        maxGreen = Math.max(maxGreen, val);
                    }
                    else if (r[1].contains("red")) {
                        maxRed = Math.max(maxRed, val);
                    }
                }
            }
            
            ret += maxBlue*maxGreen*maxRed;
        }

        System.out.println(ret);
    }
}