import java.io.*;
import java.util.*;

public class FileDemo24 {
    public static void main(String[] args) {
        File file = new File("2023/day-24/input.txt");
        List<String> stringPerLineList = new ArrayList<>();

        try {
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine())
                stringPerLineList.add(sc.nextLine());
            sc.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        List<Day24Line> lineList = new ArrayList<>();

        for (String s : stringPerLineList) {
            String[] split = s.split(" @ ", 0);
            String[] positions = split[0].split(", ", 0);
            String[] velocities = split[1].split(", ", 0);
            lineList.add(new Day24Line(Long.parseLong(positions[0]), Long.parseLong(positions[1]), Long.parseLong(positions[2]), Long.parseLong(velocities[0]), Long.parseLong(velocities[1]), Long.parseLong(velocities[2])));
        }

        /*

        final double TEST_AREA_MIN = 200000000000000.0;
        final double TEST_AREA_MAX = 400000000000000.0;

        long ret = 0;

        //formula for line ax*x + by*y + c = 0 where ax = vx, by = vy, c = px + py
        //intersection happens at
        //x = ((b1c2 - b2c1) / (a1b2 - a2b1)), y = ((c1a2 - c2a1) / (a1b2 - a2b1))
        for (int i = 0; i < lineList.size(); i++) {
            for (int j = i+1; j < lineList.size(); j++) {
                Day24Line lineOne = lineList.get(i);
                Day24Line lineTwo = lineList.get(j);

                System.out.println("i: " + i + " j: " + j);

                double vx1 = lineOne.vx;
                double vy1 = lineOne.vy;
                double px1 = lineOne.px;
                double py1 = lineOne.py;
                
                //slope = py / px = -1/2
                //c1 = (y = mx + b) ==> c1 = y - (slope *x)
                //                         = 13 - (-1/2 * 19)
                //                         = 13 + 9.5 = 22.5

                double m1 = vy1 / vx1;
                double c1 = py1 - (m1 * px1);

                //System.out.println("py1: " + py1 + " m1 : " + m1 + " : px1:" + px1);

                double vx2 = lineTwo.vx;
                double vy2 = lineTwo.vy;
                double px2 = lineTwo.px;
                double py2 = lineTwo.py;

                double m2 = vy2 / vx2;
                double c2 = py2 - (m2 * px2);

                double a1 = m1;
                double a2 = m2;
                a1 *= -1;
                a2 *= -1;
                c1 *= -1;
                c2 *= -1;
                double b1 = 1;
                double b2 = 1;

                System.out.println("a1: " + a1 + " b1: " + b1 + " c1: " + c1);

                if (((a1 * b2) - (a2 * b1)) == 0) { 
                    continue;
                }

                double x = ((b1 * c2) - (b2 * c1)) / ((a1 * b2) - (a2 * b1));
                double y = ((c1 * a2) - (c2 * a1)) / ((a1 * b2) - (a2 * b1));

                System.out.println("x: " + x + " y: " + y);

                boolean firstBool = false;
                boolean secondBool = false;
                boolean thirdBool = false;
                boolean fourthBool = false;

                if (x >= TEST_AREA_MIN && x <= TEST_AREA_MAX && y >= TEST_AREA_MIN && y <= TEST_AREA_MAX) {
                    if ((vx1 >= 0 && x >= px1) || (vx1 < 0 && x <= px1)) {
                        firstBool = true;
                    }
                    if ((vy1 >= 0 && y >= py1) || (vy1 <0 && y <= py1)) {
                        secondBool = true;
                    }
                    if ((vx2 >= 0 && x >= px2) || (vx2 < 0 && x <= px2)) {
                        thirdBool = true;
                    }
                    if ((vy2 >= 0 && y >= py2) || (vy2 <0 && y <= py2)) {
                        fourthBool = true;
                    }
                    if (firstBool && secondBool && thirdBool && fourthBool)
                        ret++;
                }

            }
        }

        System.out.println(ret);

        */

        Day24Line h1 = lineList.get(0);
        Day24Line h2 = lineList.get(2);
 
        int range = 500;
        for (int vx = -range; vx <= range; vx++) {
            for (int vy = -range; vy <= range; vy++) {
                for (int vz = -range; vz <= range; vz++) {
 
                    if (vx == 0 || vy == 0 || vz == 0) {
                        continue;
                    }
 
                    long A = h1.px, a = h1.vx - vx;
                    long B = h1.py, b = h1.vy - vy;
                    long C = h2.px, c = h2.vx - vx;
                    long D = h2.py, d = h2.vy - vy;

                    if (c == 0 || (a * d) - (b * c) == 0) {
                        continue;
                    }
 
                    long t = (d * (C - A) - c * (D - B)) / ((a * d) - (b * c));
 
                    long x = h1.px + h1.vx * t - vx * t;
                    long y = h1.py + h1.vy * t - vy * t;
                    long z = h1.pz + h1.vz * t - vz * t;
 
                    boolean success = true;
                    for (int i = 0; i < lineList.size(); i++) {
 
                        Day24Line h = lineList.get(i);
                        long u;
                        if (h.vx != vx) {
                            u = (x - h.px) / (h.vx - vx);
                        } else if (h.vy != vy) {
                            u = (y - h.py) / (h.vy - vy);
                        } else if (h.vz != vz) {
                            u = (z - h.pz) / (h.vz - vz);
                        } else {
                            throw new RuntimeException(); //throw exception if u is not initialised
                        }
 
                        if ((x + u * vx != h.px + u * h.vx) || (y + u * vy != h.py + u * h.vy) || ( z + u * vz != h.pz + u * h.vz)) {
                            success = false;
                            break;
                        }
                    }
 
                    if (success) {
                        System.out.println(x + y + z);
                    }
                }
            }
        }

        System.out.println("fin");
    }
}
