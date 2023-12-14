import java.io.*;
import java.util.*;

public class FileDemo4 {
    public static void main(String[] args) {
        File file = new File("2023/day-4/input.txt");
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
        int[] winningsPerCard = new int[stringPerLineList.size()];
        int[] numberOfCards = new int[stringPerLineList.size()];

        Arrays.fill(numberOfCards, 1);

        for (int i = 0; i < stringPerLineList.size(); i++) {
            String[] strip = stringPerLineList.get(i).split(": ", 0);
            String[] strip2 = strip[1].split(" \\| ", 0);
            String possibleNumbersStr = strip2[0];
            String winningNumbersStr = strip2[1];
            List<Integer> possibleNumbersList = new ArrayList<>();
            List<Integer> winningNumbersList = new ArrayList<>();

            String[] possibleNumbersArr = possibleNumbersStr.split(" ", 0);
            String[] winningNumbersArr = winningNumbersStr.split(" ", 0);

            for (String s : possibleNumbersArr) {
                if (s.equals("")) continue;
                possibleNumbersList.add(Integer.parseInt(s));
            }

            for (String s : winningNumbersArr) {
                if (s.equals("")) continue;
                winningNumbersList.add(Integer.parseInt(s));
            }

            int currentCardWinnings = 0;

            for (int possibleNumber : possibleNumbersList) {
                if (winningNumbersList.contains(possibleNumber)) {
                    currentCardWinnings++;
                }
            }

            winningsPerCard[i] = currentCardWinnings;

            for (int j = i+1; j < Math.min(stringPerLineList.size(), currentCardWinnings+i+1); j++) {
                numberOfCards[j] += numberOfCards[i];
            }

            ret += numberOfCards[i];
        }

        System.out.println(ret);
    }
}