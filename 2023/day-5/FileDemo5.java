import java.io.*;
import java.util.*;

public class FileDemo5 {
    public static void main(String[] args) {
        File file = new File("2023/day-5/input.txt");
        List<String> stringPerLineList = new ArrayList<>();

        try {
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine())
                stringPerLineList.add(sc.nextLine());
            sc.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        long ret = Long.MAX_VALUE;
        String seeds = "";
        List<Long> seedsList = new ArrayList<>();
        List<long[]>[] ranges = new ArrayList[7];
        List<String>[] rangeStrings = new ArrayList[7];

        for (int i = 0; i < ranges.length; i++) {
            ranges[i] = new ArrayList<>();
            rangeStrings[i] = new ArrayList<>();
        }


        for (int i = 0; i < stringPerLineList.size(); i++) {
            if (i == 0) {
                seeds = stringPerLineList.get(i);
            }
            else if (i >= 3 && i < 15) {
                rangeStrings[0].add(stringPerLineList.get(i));
            }
            else if (i >= 17 && i < 38) {
                rangeStrings[1].add(stringPerLineList.get(i));
            }
            else if (i >= 40 && i < 56) {
                rangeStrings[2].add(stringPerLineList.get(i));
            }
            else if (i >= 58 && i < 103) {
                rangeStrings[3].add(stringPerLineList.get(i));
            }
            else if (i >= 105 && i < 152) {
                rangeStrings[4].add(stringPerLineList.get(i));
            }
            else if (i >= 154 && i < 177) {
                rangeStrings[5].add(stringPerLineList.get(i));
            }
            else if (i >= 179 && i < 205) {
                rangeStrings[6].add(stringPerLineList.get(i));
            }
        }

        String[] splitSeedsStr = seeds.split(": ", 0);
        String[] seedsStrings = splitSeedsStr[1].split(" ", 0);
        for (String s : seedsStrings) {
            if (s.equals("")) continue;
            seedsList.add(Long.parseLong(s));
        }

        for (int i = 0; i < rangeStrings.length; i++) {
            for (int j = 0; j < rangeStrings[i].size(); j++) {
                String[] splitStr = rangeStrings[i].get(j).split(" ", 0);
                ranges[i].add(new long[] { Long.parseLong(splitStr[0]), Long.parseLong(splitStr[1]), Long.parseLong(splitStr[2]) });
            }
        }

        for (int i = 0; i < seedsList.size(); i++) {
            long cur = seedsList.get(i);

            for (int j = 0; j < ranges.length; j++) {
                for (int k = 0; k < ranges[j].size(); k++) {
                    //minLeftRange + diff ==> minRightRange + diff
                    long destinationMin = ranges[j].get(k)[0];
                    long sourceMin = ranges[j].get(k)[1];
                    long rangeLength = ranges[j].get(k)[2] - 1;
                    
                    if (cur >= sourceMin && cur <= sourceMin + rangeLength) {
                        long diff = cur - sourceMin;
                        cur = destinationMin + diff;
                        break;
                    }
                }
            }

            ret = Math.min(ret, cur);
        }

        System.out.println(ret);
    }
}

//define a List of ranges for every category
//iterate over the ranges until you find one which matches
//calculate the mapped value

//do this until all the mapped values have been calculated and the location is found
//calc minimum location value