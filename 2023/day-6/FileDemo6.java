import java.io.*;
import java.util.*;

public class FileDemo6 {

    //Hold the button for 1 millisecond at the start of the race. 
    //Then, the boat will travel at a speed of 1 millimeter per millisecond for 6 milliseconds, reaching a total distance traveled of 6 millimeters.
    private static boolean canWin(long distance, long timeHoldingButton, long totalTime) {
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


        String[] timesString = stringPerLineList.get(0).split(": ", 0);
        timesString[1] = timesString[1].replaceAll("\\s+","");

        String[] distancesString = stringPerLineList.get(1).split(": ", 0);
        distancesString[1] = distancesString[1].replaceAll("\\s+","");

        long time = Long.parseLong(timesString[1]);
        long distance = Long.parseLong(distancesString[1]);

        System.out.println("time: " + time + " distance: " + distance);

        long minTimeHold = -1;
        long maxTimeHold = -1;

        for (long i = 0; i <= time; i++) {
            if (canWin(distance, i, time)) {
                minTimeHold = i;
                break;
            }
        }

        for (long i = time; i >= 0; i--) {
            if (canWin(distance, i, time)) {
                maxTimeHold = i;
                break;
            }
        }

        System.out.println(maxTimeHold-minTimeHold+1);
    }
}

//define a List of ranges for every category
//iterate over the ranges until you find one which matches
//calculate the mapped value

//do this until all the mapped values have been calculated and the location is found
//calc minimum location value