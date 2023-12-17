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

        int ret = 0;

        for (int i = 0; i < stringPerLineList.size(); i++) {
            List<List<Integer>> bonusLines = new ArrayList<>();

            String[] splitLine = stringPerLineList.get(i).split(" ", 0);

            List<Integer> firstLine = new ArrayList<>();

            for (String s : splitLine) {
                firstLine.add(Integer.parseInt(s));
            }

            bonusLines.add(firstLine);

            boolean allZeros = false;
            int x = 0;

            while (true) {
                allZeros = true;
                List<Integer> nextLine = new ArrayList<>();

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

            int magicNumber = -1;

            for (int y = bonusLines.size()-2; y >= 0; y--) {
                int size1 = bonusLines.get(y+1).size();
                int size2 = bonusLines.get(y).size();
                int magicNumber1 = bonusLines.get(y+1).get(size1-1);
                int magicNumber2 = bonusLines.get(y).get(size2-1);
                bonusLines.get(y).add(magicNumber1 + magicNumber2);
                magicNumber = magicNumber1 + magicNumber2;
            }

            System.out.println("magic number: " + magicNumber);

            ret += magicNumber;
        }

        System.out.println(ret);
    }
}