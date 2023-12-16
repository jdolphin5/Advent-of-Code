import java.io.*;
import java.util.*;

public class FileDemo7 {
    public static void main(String[] args) {

        File file = new File("2023/day-7/input.txt");
        List<String> handsWithBid = new ArrayList<>();

        try {
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine())
                handsWithBid.add(sc.nextLine());
            sc.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        
        int ret = 0;

        Hand[] hands = new Hand[handsWithBid.size()];

        for (int i = 0; i < handsWithBid.size(); i++) {
            String[] splitStr = handsWithBid.get(i).split(" ", 0);
            hands[i] = new Hand(splitStr[0], Integer.parseInt(splitStr[1]));
        }

        Arrays.sort(hands, (a, b) -> Long.compare(a.priority, b.priority));

        for (int i = 0; i < hands.length; i++) {
            System.out.println(hands[i].hand + " : " + hands[i].priority);
            ret += (i+1) * hands[i].bid;
        }

        System.out.println(ret);
    }
}