import java.io.*;
import java.util.*;
import java.math.BigInteger;

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
        List<String> currentNodes = new ArrayList<>();

        for (int i = 2; i < stringPerLineList.size(); i++) {
            String[] splitStr = stringPerLineList.get(i).split(" = ", 0);
            String location = splitStr[0];
            String leftChoice = splitStr[1].substring(1, 4);
            String rightChoice = splitStr[1].substring(6, 9);
            leftChoiceDestinationMap.put(location, leftChoice);
            rightChoiceDestinationMap.put(location, rightChoice);

            if (location.charAt(2) == 'A') {
                currentNodes.add(location);
            }
        }

        System.out.println(currentNodes.toString());

        List<Integer> cycles = new ArrayList<>();

        for (int i = 0; i < currentNodes.size(); i++) {
            int steps = 0;
            String firstZ = "";
            int cycleSteps = 0;

            while (true) {
                if (currentNodes.get(i).charAt(2) == 'Z') {
                    if (firstZ.equals("")) {
                        cycleSteps = 0;
                        firstZ = currentNodes.get(i);
                    }
                    else if (firstZ.equals(currentNodes.get(i))) {
                        cycles.add(cycleSteps);
                        break;
                    }
                }

                if (directions.charAt(steps % directions.length()) == 'L') {
                    currentNodes.set(i, leftChoiceDestinationMap.get(currentNodes.get(i)));
                }
                else {
                    currentNodes.set(i, rightChoiceDestinationMap.get(currentNodes.get(i)));
                }

                steps++;
                cycleSteps++;
            }
        }

        System.out.println(cycles.toString());

        BigInteger ret = new BigInteger(String.valueOf(cycles.get(0)));

        for (int x = 1; x < cycles.size(); x++) {
            BigInteger num = new BigInteger(String.valueOf(cycles.get(x)));
            BigInteger gcd = ret.gcd(num);
            BigInteger product = ret.multiply(num);
            ret  = product.divide(gcd);
        }

        System.out.println(ret);
    }
}