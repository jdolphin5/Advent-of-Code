import java.io.*;
import java.util.*;

public class FileDemo20 {
    private static long gcd (long a, long b) {
        if (a % b == 0)
            return b;
        
        return gcd(b, a % b);
    }

    public static void main(String[] args) {
        File file = new File("2023/day-20/input.txt");
        List<String> stringPerLineList = new ArrayList<>();

        try {
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine())
                stringPerLineList.add(sc.nextLine());
            sc.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        Map<String, Day20Module> moduleMap = new HashMap<>();

        for (String s : stringPerLineList) {
            String[] split = s.split(" -> ", 0);
            String[] destinationString = split[1].split(", ", 0);

            String name = split[0];
            if (split[0].charAt(0) == '%' || split[0].charAt(0) == '&') {
                name = split[0].substring(1, split[0].length());
            }

            Day20Module inputModule = moduleMap.getOrDefault(name, new Day20Module(name, split[0].charAt(0), false, new ArrayList<>(), new ArrayList<>()));

            if (inputModule.type == 'X') {
                inputModule.type = split[0].charAt(0);
            }

            for (String a : destinationString) {
                inputModule.destinationList.add(a);
            }

            moduleMap.put(name, inputModule);

            for (String a : destinationString) {
                Day20Module x = moduleMap.getOrDefault(a, new Day20Module(a, 'X', false, new ArrayList<>(), new ArrayList<>()));
                x.inputList.add(name);
                moduleMap.put(a, x);
            }
        }

        /*
        for (Map.Entry<String, Day20Module> entry : moduleMap.entrySet()) {
            System.out.println(entry.getValue());
        }
        */

        long lowPulseCount = 0;
        long highPulseCount = 0;

        long buttonCount = 0;

        List<Long> firstOccurrenceOfKeyModules = new ArrayList<>();
        //dc - HIGH, rv - HIGH, vp - HIGH, cq - HIGH
        List<String> keyModules = new ArrayList<>();
        keyModules.add("dc");
        keyModules.add("rv");
        keyModules.add("vp");
        keyModules.add("cq");

        while (keyModules.size() > 0) {
            buttonCount++;
            lowPulseCount++;

            Queue<Day20Signal> q = new LinkedList<>();
            
            for (String output : moduleMap.get("broadcaster").destinationList) {
                q.offer(new Day20Signal("broadcaster", output, "low"));
            }

            while (!q.isEmpty()) {
                int size = q.size();

                for (int i = 0; i < size; i++) {
                    Day20Signal s = q.poll();
                    if (s.pulseType.equals("high") && keyModules.contains(s.input) && s.destination.equals("ns")) {
                        keyModules.remove(s.input);
                        System.out.println("first : " + s.input + " bc: " + buttonCount);
                        firstOccurrenceOfKeyModules.add(buttonCount);
                    }
                    Day20Module module = moduleMap.get(s.destination);
                    if (s.pulseType.equals("low")) {
                        lowPulseCount++;
                    }
                    else {
                        highPulseCount++;
                    }

                    if (module.type == '%' && s.pulseType.equals("low")) {
                        if (!module.state) {
                            for (String output : module.destinationList) {
                                q.offer(new Day20Signal(module.name, output, "high"));
                            }
                        }
                        else {
                            for (String output : module.destinationList) {
                                q.offer(new Day20Signal(module.name, output, "low"));
                            }
                        }

                        module.state = !module.state;
                    }
                    else if (module.type == '&') {
                        boolean allHigh = true;

                        for (String input : module.inputList) {
                            if (!moduleMap.get(input).state) {
                                allHigh = false;
                                break;
                            }
                        }

                        if (allHigh) {
                            module.state = false;
                            for (String output : module.destinationList) {
                                q.offer(new Day20Signal(module.name, output, "low"));
                            }
                        }
                        else {
                            module.state = true;
                            for (String output : module.destinationList) {
                                q.offer(new Day20Signal(module.name, output, "high"));
                            }
                        }
                    }
                    moduleMap.put(s.destination, module);
                }
            }
        }

        //Part 2 get LCM of first occurrence of : dc to ns - HIGH, rv to ns - HIGH, vp to ns - HIGH, cq to ns - HIGH
        //LCM = (n1 * n2) / GCD
        long ret = 1;
        for (int i = 0; i < firstOccurrenceOfKeyModules.size(); i++) {
            long x = firstOccurrenceOfKeyModules.get(i);
            ret = ret * x / gcd(ret, x);
        }

        System.out.println(ret);
    }
}
