import java.io.*;
import java.util.*;

public class FileDemo5 {

    private static List<Day5RangeSeed> generateOuterLists(Day5RangeSeed original, Day5RangeSeed seed) {
        List<Day5RangeSeed> myList = new ArrayList<>();

        if (seed.start - original.start > 0)
            myList.add(new Day5RangeSeed(original.start, seed.start - original.start, original.rangeNumber));
        if (original.start + original.len > seed.start + seed.len)
            myList.add(new Day5RangeSeed(seed.start + seed.len, original.start + original.len - 1 - (seed.start + seed.len - 1), original.rangeNumber));

        return myList;
    }

    private static long recur(Day5RangeSeed seed, List<Day5Range>[] rangeList) {
        long ret = Long.MAX_VALUE;

        if (seed.len == 0) {
            return Long.MAX_VALUE;
        }

        if (seed.rangeNumber == 7) {
            return seed.start;
        }

        boolean sent = false;

        for (Day5Range range : rangeList[seed.rangeNumber]) {
            Day5RangeSeed p = new Day5RangeSeed(seed.start, seed.len, seed.rangeNumber);

            if (seed.len > 0 && seed.start <= range.source + range.len && seed.start + seed.len > range.source) {
                sent = true;

                long start = Math.max(seed.start, range.source);
                long end = Math.min(seed.start + seed.len, range.source + range.len);
                long len = end - start;
                if (len == 0) {
                    sent = false;
                    continue;
                }

                long offset = range.dest - range.source;

                p = new Day5RangeSeed(start, len, seed.rangeNumber); //+ offset, len, seed.rangeNumber+1);
                //System.out.println(" seed:" + seed);
                //System.out.println(" P: " + p);
                List<Day5RangeSeed> otherRangeList = generateOuterLists(seed, p);
                //System.out.println("test");

                for (Day5RangeSeed x : otherRangeList) {
                    ret = Math.min(ret, recur(x, rangeList));
                    //System.out.println("x: " + x);
                }

                //System.out.println("sent as: " + (p.start + offset) + " : " + p.len + " : " + (p.rangeNumber+1));
                ret = Math.min(ret, recur(new Day5RangeSeed(p.start + offset, p.len, p.rangeNumber + 1), rangeList));

                break;
            }
        }

        if (!sent) {
            //System.out.println("forwarded with same values");
            ret = Math.min(ret, recur(new Day5RangeSeed(seed.start, seed.len, seed.rangeNumber + 1), rangeList));
        }

        return ret;
    }
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

        /*
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
         */

        long ret = Long.MAX_VALUE;

        String seeds = "";
        List<Day5RangeSeed> seedsList = new ArrayList<>();
        List<Day5Range>[] ranges = new ArrayList[7];

        for (int i = 0; i < ranges.length; i++) {
            ranges[i] = new ArrayList<>();
        }

        for (int i = 0; i < stringPerLineList.size(); i++) {
            if (i == 0) {
                seeds = stringPerLineList.get(i);
                String[] split = seeds.split(" ", 0);
                for (int j = 1; j < split.length; j+=2) {
                    seedsList.add(new Day5RangeSeed(Long.parseLong(split[j]), Long.parseLong(split[j+1]), 0));
                }
            }
            //test data ranges: 3 5
            else if (i >= 3 && i < 15) { //actual data ranges: 3 15
                String[] split = stringPerLineList.get(i).split(" ", 0);
                ranges[0].add(new Day5Range(Long.parseLong(split[0]), Long.parseLong(split[1]), Long.parseLong(split[2])));
            }
            //7 10
            else if (i >= 17 && i < 38) { //17 38
                String[] split = stringPerLineList.get(i).split(" ", 0);
                ranges[1].add(new Day5Range(Long.parseLong(split[0]), Long.parseLong(split[1]), Long.parseLong(split[2])));
            }
            //12 16
            else if (i >= 40 && i < 56) { //40 56
                String[] split = stringPerLineList.get(i).split(" ", 0);
                ranges[2].add(new Day5Range(Long.parseLong(split[0]), Long.parseLong(split[1]), Long.parseLong(split[2])));
            }
            //18 20
            else if (i >= 58 && i < 103) { //58 103
                String[] split = stringPerLineList.get(i).split(" ", 0);
                ranges[3].add(new Day5Range(Long.parseLong(split[0]), Long.parseLong(split[1]), Long.parseLong(split[2])));
            }
            //22 25
            else if (i >= 105 && i < 152) { //105 152
                String[] split = stringPerLineList.get(i).split(" ", 0);
                ranges[4].add(new Day5Range(Long.parseLong(split[0]), Long.parseLong(split[1]), Long.parseLong(split[2])));
            }
            //27 29
            else if (i >= 154 && i < 177) { //154 177
                String[] split = stringPerLineList.get(i).split(" ", 0);
                ranges[5].add(new Day5Range(Long.parseLong(split[0]), Long.parseLong(split[1]), Long.parseLong(split[2])));
            }
            //31 33
            else if (i >= 179 && i < 205) { //179 205
                String[] split = stringPerLineList.get(i).split(" ", 0);
                ranges[6].add(new Day5Range(Long.parseLong(split[0]), Long.parseLong(split[1]), Long.parseLong(split[2])));
            }
        }

        for (Day5RangeSeed seed : seedsList) {
            ret = Math.min(ret, recur(seed, ranges));
        }
        
        System.out.println("ret: " + ret);
    }
}

//define a List of ranges for every category
//iterate over the ranges until you find one which matches
//calculate the mapped value

//do this until all the mapped values have been calculated and the location is found
//calc minimum location value