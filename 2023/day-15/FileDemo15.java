import java.io.*;
import java.util.*;

public class FileDemo15 {

    /*
        private static long getBoxNumber(String s) {
            long ret = 0;

            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                ret += (int)c;
                ret *= 17;
                ret %= 256;
            }

            return ret;
        }
    */

    private static int getBoxNumber(String s, Map<String, Integer> labelToBoxNumberMap) {
        if (labelToBoxNumberMap.get(s) != null) {
            return labelToBoxNumberMap.get(s);
        }

        int ret = 0;

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            ret += (int)c;
            ret *= 17;
            ret %= 256;
        }

        labelToBoxNumberMap.put(s, ret);

        return ret;
    }

    private static void equalOperation(Map<Integer, List<Lens>> boxMap, int boxNumber, String label, int focalLength) {
        List<Lens> lensList = boxMap.getOrDefault(boxNumber, new ArrayList<>());

        boolean add = true;

        for (int i = 0; i < lensList.size(); i++) {
            if (lensList.get(i).label.equals(label)) {
                lensList.get(i).focalLength = focalLength;
                add = false;
            }
        }

        if (add) {
            lensList.add(new Lens(focalLength, label));
        }

        boxMap.put(boxNumber, lensList);
    }

    private static void minusOperation(Map<Integer, List<Lens>> boxMap, int boxNumber, String label) {
        if (boxMap.get(boxNumber) == null) return;

        List<Lens> lensList = boxMap.get(boxNumber);
        int toRemove = -1;

        for (int i = 0; i < lensList.size(); i++) {
            if (lensList.get(i).label.equals(label)) {
                toRemove = i;
                break;
            }
        }

        if (toRemove != -1) {
            lensList.remove(toRemove);
        }
    }



    public static void main(String[] args) {
        File file = new File("2023/day-15/input.txt");
        List<String> stringPerLineList = new ArrayList<>();

        try {
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine())
                stringPerLineList.add(sc.nextLine());
            sc.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        List<String> initialisationSequence = new ArrayList<>();

        for (int i = 0; i < stringPerLineList.size(); i++) {
            String[] lineStrings = stringPerLineList.get(i).split(",", 0);

            for (String s : lineStrings) {
                if (s.length() > 0) {
                    initialisationSequence.add(s);
                }
            }
        }

        long ret = 0;

        //Part 1:

        //for (String s : initialisationSequence) {
            //ret += getBoxNumber(s);
            //System.out.println(ret);
        //}


        /*Part 2:

        Each step begins with a sequence of letters that indicate the label of the lens on which the step operates.
        The result of running the HASH algorithm on the label indicates the correct box for that step.

        vpq=6
        label = "vpq", operation = '=', focal length = '6', box number = HASH("vpq")
        hxb-
        label = "hxb", operation = '-', box number = HASH("hxb")

        If the operation character is a dash (-), go to the relevant box and remove the lens with the given label if it is
        present in the box. Then, move any remaining lenses as far forward in the box as they can go without changing their order,
        filling any space made by removing the indicated lens.

        If the operation character is an equals sign (=), it will be followed by a number indicating the focal length of the lens
        If there is already a lens in the box with the same label, replace the old lens with the new lens: remove the old lens and
            put the new lens in its place, not moving any other lenses in the box.
        If there is not already a lens in the box with the same label, add the lens to the box to the back of the box.
        /* */

        Map<Integer, List<Lens>> boxMap = new HashMap<>();

        for (String s : initialisationSequence) {
            Map<String, Integer> labelToBoxNumberMap = new HashMap<>();

            String label = "";
            Integer focalLength = -1;

            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) == '=' || s.charAt(i) == '-') {
                    if (s.charAt(i) == '=')
                        focalLength = Character.getNumericValue(s.charAt(i+1));
                    break;
                }
                
                label = label + s.charAt(i);
            }

            int boxNumber = getBoxNumber(label, labelToBoxNumberMap);

            if (s.contains("=")) {
                equalOperation(boxMap, boxNumber, label, focalLength);
            }
            else if (s.contains("-")) {
                minusOperation(boxMap, boxNumber, label);
            }

            System.out.println(boxNumber);
        }

        for (Map.Entry<Integer, List<Lens>> entry : boxMap.entrySet()) {
            int key = entry.getKey();
            List<Lens> lensList = entry.getValue();

            System.out.println("key: " + entry.getKey());

            long cur = 0;

            for (int i = 0; i < lensList.size(); i++) {
                cur += (key+1) * (i+1) * lensList.get(i).focalLength;
                System.out.println(cur);
            }
            

            ret += cur;
        }

        System.out.println(ret);
    }
}
