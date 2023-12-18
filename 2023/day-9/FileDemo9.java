import java.io.*;
import java.util.*;

public class FileDemo9 {
    public static void main(String[] args) {
        File file = new File("2023/day-9/input.txt");
        List<String> stringPerLineList = new ArrayList<>();

        try {
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine())
                stringPerLineList.add(sc.nextLine());
            sc.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        long ret = 0;

        for (int i = 0; i < stringPerLineList.size(); i++) {
            List<List<Long>> bonusLines = new ArrayList<>();

            String[] splitLine = stringPerLineList.get(i).split(" ", 0);

            List<Long> firstLine = new ArrayList<>();

            for (String s : splitLine) {
                firstLine.add(Long.parseLong(s));
            }


            System.out.println(firstLine.toString());
            bonusLines.add(firstLine);

            boolean allZeros = false;
            int x = 0;

            while (true) {
                allZeros = true;
                List<Long> nextLine = new ArrayList<>();

                for (int y = 0; y < bonusLines.get(x).size()-1; y++) {
                    if (bonusLines.get(x).get(y) != 0) {
                        allZeros = false;
                    }

                    nextLine.add(bonusLines.get(x).get(y+1) - bonusLines.get(x).get(y));
                }

                if (allZeros) break;

                x++;

                bonusLines.add(nextLine);
                System.out.println(nextLine.toString());
                
            }

            List<Long> myList = bonusLines.get(bonusLines.size()-1);
            myList.add(0, myList.get(0));
            bonusLines.set(bonusLines.size()-1, myList);

            long magicNumber = -1;

            for (int y = bonusLines.size()-2; y >= 0; y--) {
                long magicNumber1 = bonusLines.get(y+1).get(0);
                long magicNumber2 = bonusLines.get(y).get(0);
                bonusLines.get(y).add(0, magicNumber2 - magicNumber1);
            }

            magicNumber = bonusLines.get(0).get(0);

            System.out.println("magic number: " + magicNumber);

            ret += magicNumber;
        }

        System.out.println(ret);
    }
}