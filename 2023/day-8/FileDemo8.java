import java.io.*;
import java.util.*;

public class FileDemo8 {
    public static void main(String[] args) {
        File file = new File("2023/day-8/input.txt");
        List<String> stringPerLineList = new ArrayList<>();

        try {
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine())
                stringPerLineList.add(sc.nextLine());
            sc.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        String directions = stringPerLineList.get(0);
        Map<String, String> leftChoiceDestinationMap = new HashMap<>();
        Map<String, String> rightChoiceDestinationMap = new HashMap<>();

        for (int i = 2; i < stringPerLineList.size(); i++) {
            String[] splitStr = stringPerLineList.get(i).split(" = ", 0);
            String location = splitStr[0];
            String leftChoice = splitStr[1].substring(1, 4);
            String rightChoice = splitStr[1].substring(6, 9);
            leftChoiceDestinationMap.put(location, leftChoice);
            rightChoiceDestinationMap.put(location, rightChoice);
        }

        String location = "AAA";
        String destination = "ZZZ";

        int steps = 0;

        while (!location.equals(destination)) {
            int i = steps % directions.length();
            char direction = directions.charAt(i);

            if (direction == 'L') {
                location = leftChoiceDestinationMap.get(location);
            }
            else {
                location = rightChoiceDestinationMap.get(location);
            }

            steps++;
        }

        System.out.println(steps);
    }
}