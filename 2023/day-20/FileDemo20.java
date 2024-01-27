import java.io.*;
import java.util.*;

public class FileDemo20 {
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

        int buttonCount = 0;

        while (buttonCount++ < 1000) {
            lowPulseCount++;

            Queue<Day20Signal> q = new LinkedList<>();
            
            for (String output : moduleMap.get("broadcaster").destinationList) {
                q.offer(new Day20Signal(output, "low"));
            }

            while (!q.isEmpty()) {
                int size = q.size();

                for (int i = 0; i < size; i++) {
                    Day20Signal s = q.poll();
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
                                q.offer(new Day20Signal(output, "high"));
                            }
                        }
                        else {
                            for (String output : module.destinationList) {
                                q.offer(new Day20Signal(output, "low"));
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
                                q.offer(new Day20Signal(output, "low"));
                            }
                        }
                        else {
                            module.state = true;
                            for (String output : module.destinationList) {
                                q.offer(new Day20Signal(output, "high"));
                            }
                        }
                    }
                    moduleMap.put(s.destination, module);
                }
            }
        }

        System.out.println(lowPulseCount * highPulseCount);
        
    }
}
