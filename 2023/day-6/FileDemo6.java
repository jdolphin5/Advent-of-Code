import java.io.*;
import java.util.*;

public class FileDemo6 {

    //Hold the button for 1 millisecond at the start of the race. 
    //Then, the boat will travel at a speed of 1 millimeter per millisecond for 6 milliseconds, reaching a total distance traveled of 6 millimeters.
    private static boolean canWin(int distance, int timeHoldingButton, int totalTime) {
        return timeHoldingButton * (totalTime - timeHoldingButton) > distance;
    }
    public static void main(String[] args) {
        File file = new File("2023/day-6/input.txt");
        List<String> stringPerLineList = new ArrayList<>();

        try {
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine())
                stringPerLineList.add(sc.nextLine());
            sc.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        int[] times = new int[4];
        int[] distances = new int[4];

        String[] timesString = stringPerLineList.get(0).split(": ", 0);
        String[] timesStrings = timesString[1].split(" ", 0);

        int j = 0;

        for (int i = 0; i < timesStrings.length; i++) {
            if (timesStrings[i].equals("")) continue;
            int time = Integer.parseInt(timesStrings[i].trim());
            times[j++] = time;
        }

        String[] distancesString = stringPerLineList.get(1).split(": ", 0);
        String[] distancesStrings = distancesString[1].split(" ", 0);

        j = 0;

        for (int i = 0; i < distancesStrings.length; i++) {
            if (distancesStrings[i].equals("")) continue;
            int distance = Integer.parseInt(distancesStrings[i].trim());
            distances[j++] = distance;
        }

        int ret = 1;

        for (int i = 0; i < distances.length; i++) {
            int ct = 0;

            for (int k = 0; k <= times[i]; k++) {
                if (canWin(distances[i], k, times[i])) {
                    ct++;
                }
            }

            ret *= ct;
        }
        

        System.out.println(ret);
    }
}

//define a List of ranges for every category
//iterate over the ranges until you find one which matches
//calculate the mapped value

//do this until all the mapped values have been calculated and the location is found
//calc minimum location value