import java.io.*;
import java.util.*;

public class FileDemo7 {
    public static void main(String[] args) {
        File file = new File("2023/day-7/input.txt");
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

        System.out.println(ret);
    }
}

//define a List of ranges for every category
//iterate over the ranges until you find one which matches
//calculate the mapped value

//do this until all the mapped values have been calculated and the location is found
//calc minimum location value