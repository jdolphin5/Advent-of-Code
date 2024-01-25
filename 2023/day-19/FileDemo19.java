import java.io.*;
import java.util.*;

public class FileDemo19 {
    public static void main(String[] args) {
        File file = new File("2023/day-19/input.txt");
        List<String> stringPerLineList = new ArrayList<>();

        try {
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine())
                stringPerLineList.add(sc.nextLine());
            sc.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}
