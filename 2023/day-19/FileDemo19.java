import java.io.*;
import java.util.*;

public class FileDemo19 {

    private static long countPart(Day19Part p) {
        long ret = 0;

        ret += p.x;
        ret += p.m;
        ret += p.a;
        ret += p.s;

        return ret;
    }

    private static boolean checkCondition(Day19Part p, Day19Condition condition) {

        if (condition.auto) {
            return true;
        }

        switch (condition.partType) {
            case 'x':
                return condition.mustBeGreaterThanValue ? p.x > condition.value : p.x < condition.value;
            case 'm':
                return condition.mustBeGreaterThanValue ? p.m > condition.value : p.m < condition.value;
            case 'a':
                return condition.mustBeGreaterThanValue ? p.a > condition.value : p.a < condition.value;
            case 's':
                return condition.mustBeGreaterThanValue ? p.s > condition.value : p.s < condition.value;
        }

        return false;
    }

    private static boolean computePart(Day19Part p, Map<String, List<Day19Condition>> workflowMap, String workflow) {
        List<Day19Condition> conditionList = workflowMap.get(workflow);

        for (Day19Condition condition : conditionList) {
            if (checkCondition(p, condition)) {
                if (condition.ret.equals("A")) {
                    return true;
                }
                else if (condition.ret.equals("R")) {
                    return false;
                }
                else {
                    return computePart(p, workflowMap, condition.ret);
                }
            }
        }

        return false;
    }
    public static void main(String[] args) {
        File file = new File("2023/day-19/input.txt");
        List<String> stringPerLineList = new ArrayList<>();

        try {
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine())
                stringPerLineList.add(sc.nextLine());
            sc.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        boolean isPart = false;
        Map<String, List<Day19Condition>> workflowMap = new HashMap<>();
        List<Day19Part> partList = new ArrayList<>();

        for (String s : stringPerLineList) {
            System.out.println(s);
            if (s.equals("")) {
                isPart = true;
                continue;
            }

            if (!isPart) {
                String[] split = s.split("\\{", 0);
                String workflowName = split[0];
                String[] split2 = split[1].split("}", 0);
                String[] conditionSplit = split2[0].split(",", 0);

                List<Day19Condition> conditionList = new ArrayList<>();

                for (String t : conditionSplit) {
                    if (!t.contains(":")) {
                        conditionList.add(new Day19Condition('&', false, -1, t, true));
                        continue;
                    }
                    String[] tSplit = t.split(":", 0);
                    String conditionRet = tSplit[1];
                    char c = tSplit[0].charAt(0);
                    char greaterThan = tSplit[0].charAt(1);
                    String[] valueSplit = tSplit[0].split("[><]", 0); //split by either '>' or '<'
                    String valueString = valueSplit[1];
                    Integer value = Integer.parseInt(valueString);

                    conditionList.add(new Day19Condition(c, greaterThan == '<' ? false : true, value, conditionRet, false));
                }

                for (Day19Condition c : conditionList) {
                    System.out.println(c.partType);
                }

                workflowMap.put(workflowName, conditionList);
            }
            else {
                String[] split = s.split("[\\{\\}]", 0); // split by either '{' or '}'
                String[] split2 = split[1].split(",", 0);
                int x = -1;
                int m = -1;
                int a = -1;
                int sChar = -1;
                

                for (String t : split2) {
                    char c = t.charAt(0);
                    String valueString = t.substring(2, t.length());
                    int value = Integer.parseInt(valueString);
                    if (c == 'x') {
                        x = value;
                    }
                    else if (c == 'm') {
                        m = value;
                    }
                    else if (c == 'a') {
                        a = value;
                    }
                    else if (c == 's') {
                        sChar = value;
                    }
                }

                partList.add(new Day19Part(x, m, a, sChar));
            }
        }

        long ret = 0;

        for (Day19Part p : partList) {
            if (computePart(p, workflowMap, "in")) {
                ret += countPart(p);
            }
        }

        System.out.println(ret);

    }
}
