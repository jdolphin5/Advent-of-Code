import java.io.*;
import java.util.*;

public class FileDemo17 {

    private static void generateAdjacentPoints(char[][] mat, int dir, int i, int j, List<Point> myList, int straightSteps, int MAX_STRAIGHT, int MIN_BEFORE_TURNING) {
        switch(dir) {
            case 0:
                if (straightSteps >= MIN_BEFORE_TURNING) {
                    myList.add(new Point(i, j-1, 3, 1));
                    myList.add(new Point(i, j+1, 1, 1));
                }
                if (straightSteps < MAX_STRAIGHT)
                    myList.add(new Point(i-1, j, 0, straightSteps+1));
                break;
            case 1:
                if (straightSteps >= MIN_BEFORE_TURNING) {
                    myList.add(new Point(i-1, j, 0, 1));
                    myList.add(new Point(i+1, j, 2, 1));
                }
                if (straightSteps < MAX_STRAIGHT)
                    myList.add(new Point(i, j+1, 1, straightSteps+1));
                break;
            case 2:
                if (straightSteps >= MIN_BEFORE_TURNING) {
                    myList.add(new Point(i, j+1, 1, 1));
                    myList.add(new Point(i, j-1, 3, 1));
                }
                if (straightSteps < MAX_STRAIGHT)
                    myList.add(new Point(i+1, j, 2, straightSteps+1));
                break;
            case 3:
                if (straightSteps >= MIN_BEFORE_TURNING) {
                    myList.add(new Point(i+1, j, 2, 1));
                    myList.add(new Point(i-1, j, 0, 1));
                }
                if (straightSteps < MAX_STRAIGHT)
                    myList.add(new Point(i, j-1, 3, straightSteps+1));
                break;

            default:
        }

        List<Integer> toRemove = new ArrayList<>();

        for (int a = 0; a < myList.size(); a++) {
            if (myList.get(a).i < 0 || myList.get(a).i >= mat.length || myList.get(a).j < 0 || myList.get(a).j >= mat[0].length) {
                toRemove.add(a);
            }
        }

        for (int a = toRemove.size()-1; a >= 0; a--) {
            myList.remove((int)toRemove.get(a));
        }

        for (Point p : myList) {
            p.cost = Character.getNumericValue(mat[p.i][p.j]);
        }
    }

    public static void main(String[] args) {
        File file = new File("2023/day-17/input.txt");
        List<String> stringPerLineList = new ArrayList<>();

        try {
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine())
                stringPerLineList.add(sc.nextLine());
            sc.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        char[][] mat = new char[stringPerLineList.size()][stringPerLineList.get(0).length()];

        for (int i = 0; i < stringPerLineList.size(); i++) {
            for (int j = 0; j < stringPerLineList.get(i).length(); j++) {
                mat[i][j] = stringPerLineList.get(i).charAt(j);
            }
        }

        final int MAX_STRAIGHT_STEPS = 10;
        final int MIN_BEFORE_TURN_STEPS = 4;

        Point start1 = new Point(0, 0, 1, 0, 0);
        Point start2 = new Point(0, 0, 2, 0, 0);


        boolean[][][][] visited = new boolean[4][MAX_STRAIGHT_STEPS+1][mat.length][mat[0].length];
        PriorityQueue<Point> pq = new PriorityQueue<>((a, b) -> a.cost - b.cost);
        pq.offer(start1);
        pq.offer(start2);
        //distance[start1.index][0] = 0;
        //distance[start2.index][0] = 0;

        while (!pq.isEmpty()) {
            Point p = pq.poll();

            if (visited[p.dir][p.straightSteps][p.i][p.j]) continue;

            visited[p.dir][p.straightSteps][p.i][p.j] = true;

            List<Point> adjacentPoints = new ArrayList<>();
            generateAdjacentPoints(mat, p.dir, p.i, p.j, adjacentPoints, p.straightSteps, MAX_STRAIGHT_STEPS, MIN_BEFORE_TURN_STEPS);
            
            if (p.i == mat.length-1 && p.j == mat[0].length-1)
                System.out.println("i: " + p.i + " j: " + p.j + " cost: " + p.cost);

            for (Point x : adjacentPoints) {
                int edgeCost = x.cost;
                
                if (!visited[x.dir][x.straightSteps][x.i][x.j]) {
                    pq.offer(new Point(x.i, x.j, x.dir, p.cost + edgeCost, x.straightSteps));
                }
            }
        }

    }
    
}
