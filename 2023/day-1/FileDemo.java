import java.io.*;
import java.util.*;

public class FileDemo {

    private static int checkSubstring(List<String> stringNumbers, String s) {
        for (int i = 0; i < stringNumbers.size(); i++) {
            if (s.contains(stringNumbers.get(i))) {
                return i+1;
            }
        }

        return -1;
    }
    public static void main(String[] args) {

        File file = new File("2023/day-1/input.txt");
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

        List<String> stringNumbers = new ArrayList<>();

        stringNumbers.add("one");
        stringNumbers.add("two");
        stringNumbers.add("three");
        stringNumbers.add("four");
        stringNumbers.add("five");
        stringNumbers.add("six");
        stringNumbers.add("seven");
        stringNumbers.add("eight");
        stringNumbers.add("nine");

        int lines = stringPerLineList.size();
        
        for (int i = 0; i < lines; i++) {
            int last = ret;
            int len = stringPerLineList.get(i).length();
            
            for (int j = 0; j < len; j++) {
                int cur = checkSubstring(stringNumbers, stringPerLineList.get(i).substring(0, j+1));
                
                if (Character.isDigit(stringPerLineList.get(i).charAt(j))) {
                    ret += 10 * (stringPerLineList.get(i).charAt(j) - '0');
                    break;
                }
                else if (cur != -1) {
                    ret += 10 * cur;
                    break;
                }
            }

            for (int j = len-1; j >= 0; j--) {
                int cur = checkSubstring(stringNumbers, stringPerLineList.get(i).substring(j));

                if (Character.isDigit(stringPerLineList.get(i).charAt(j))) {
                    ret += stringPerLineList.get(i).charAt(j) - '0';
                    break;
                }
                else if (cur != -1) {
                    ret += cur;
                    break;
                }
            }

            System.out.println("line: " + (i+1) + " : " + (ret - last));

        }


        System.out.println(ret);
    }
}