import java.io.*;
import java.util.*;

public class FileDemo3 {
    public static void main(String[] args) {
        File file = new File("2023/day-3/input.txt");
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


        int ret = 0;
        char[][] mat = new char[lines][stringPerLineList.get(0).length()];

        for (int i = 0; i < lines; i++) {
            String line = stringPerLineList.get(i);

            for (int j = 0; j < line.length(); j++) {
                mat[i][j] = line.charAt(j);
            }

        }

        

        System.out.println(ret);
    }
}