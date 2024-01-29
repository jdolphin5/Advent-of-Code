import java.io.*;
import java.util.*;

public class FileDemo23 {

    /*
    private static long recur(char[][] mat, boolean[][] visited, int i, int j) {
        long ret = 0;

        if (i < 0 || i >= mat.length || j < 0 || j >= mat[0].length || visited[i][j] || mat[i][j] == '#') {
            return Integer.MIN_VALUE;
        }

        visited[i][j] = true;

        if (i == mat.length-1) {
            return 0;
        }

        System.out.println("i: "+  i + " j: " + j + " mat: " + mat[i][j]);

        if (mat[i][j] != '#') {
            boolean[][] visitedCopy = new boolean[mat.length][mat[0].length];
            for (int x = 0; x < visited.length; x++) {
                for (int y = 0; y < visited[0].length; y++) {
                    visitedCopy[x][y] = visited[x][y];
                }
            }
            ret = Math.max(ret, 1 + recur(mat, visitedCopy, i-1, j));
        }
        if (mat[i][j] != '#') {
            boolean[][] visitedCopy = new boolean[mat.length][mat[0].length];
            for (int x = 0; x < visited.length; x++) {
                for (int y = 0; y < visited[0].length; y++) {
                    visitedCopy[x][y] = visited[x][y];
                }
            }
            ret = Math.max(ret, 1 + recur(mat, visitedCopy, i, j+1));
        }
        if (mat[i][j] != '#') {
            boolean[][] visitedCopy = new boolean[mat.length][mat[0].length];
            for (int x = 0; x < visited.length; x++) {
                for (int y = 0; y < visited[0].length; y++) {
                    visitedCopy[x][y] = visited[x][y];
                }
            }
            ret = Math.max(ret, 1 + recur(mat, visitedCopy, i, j-1));
        }
        if (mat[i][j] != '#') {
            boolean[][] visitedCopy = new boolean[mat.length][mat[0].length];
            for (int x = 0; x < visited.length; x++) {
                for (int y = 0; y < visited[0].length; y++) {
                    visitedCopy[x][y] = visited[x][y];
                }
            }
            ret = Math.max(ret, 1 + recur(mat, visitedCopy, i+1, j));
        }

        return ret;
    }
     */

    private static int ret = 0;

    private static void recur(List<Day23Edge>[][] adjList, int e, int i, int j, Set<Integer> visited, int weight) {
        if (i < 0 || i >= adjList.length || j < 0 || j >= adjList[0].length) {
            return;
        }

        if (i == 140) { // last row
            ret = Math.max(ret, weight);
            return;
        }

        visited.add((i * 1000) + j);

        for (Day23Edge edge : adjList[i][j]) {
            if (!visited.contains((edge.i * 1000) + (edge.j)))
                recur(adjList, edge.index, edge.i, edge.j, visited, edge.weight + weight);
        }

        visited.remove((i * 1000) + j);
    }

    public static void main(String[] args) {
        File file = new File("2023/day-23/input.txt");
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

        /*
        Part 1:
        boolean[][] visited = new boolean[mat.length][mat[0].length];

        long ret = recur(mat, visited, 0, 1);

        System.out.println(ret);
        */

        /*
        Part 2:
        I put allowed edges into a graph and then for every node with only two edges, i removed the central node
        and connected the other two nodes. This reduced the graph edges significantly. 
        Tracking visited vertices using an integer index used to reduce computation time.
        */

        List<Day23Edge>[][] adjList = new ArrayList[mat.length][mat[0].length];

        int[][] dirArr = new int[][]{{0, 1}, {1, 0}, {-1, 0}, {0, -1}};

        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[0].length; j++) {
                adjList[i][j] = new ArrayList<>();
            }
        }
        
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[0].length; j++) {
                for (int[] dir : dirArr) {
                    if (i + dir[0] >= 0 && i + dir[0] < mat.length && j + dir[1] >= 0 && j + dir[1] < mat[0].length && mat[i][j] != '#' && mat[i + dir[0]][j + dir[1]] != '#' ) {
                        adjList[i][j].add(new Day23Edge(i + dir[0], j + dir[1]));
                    }
                }
            }
        }

        boolean found = true;
        while (found) {
            found = false;

            for (int i = 0; i < mat.length; i++) {
                for (int j = 0; j < mat[0].length; j++) {
                    //System.out.println(adjList[i][j].size());
                    
                    if (adjList[i][j].size() == 2) {
                        found = true;

                        Day23Edge edge1 = adjList[i][j].get(0);
                        Day23Edge edge2 = adjList[i][j].get(1);
    
                        int node1i = edge1.i;
                        int node1j = edge1.j;
                        int node2i = edge2.i;
                        int node2j = edge2.j;
    
                        int toRemove1 = 0;
    
                        for (Day23Edge edge : adjList[node1i][node1j]) {
                            if (edge.i == i && edge.j == j) {
                                break;
                            }
                            toRemove1++;
                        }

                        int toRemove2 = 0;

                        for (Day23Edge edge : adjList[node2i][node2j]) {
                            if (edge.i == i && edge.j == j) {
                                break;
                            }
                            toRemove2++;
                        }

                        adjList[node1i][node1j].remove(toRemove1);
                        adjList[node2i][node2j].remove(toRemove2);
                        
                        adjList[node1i][node1j].add(new Day23Edge(node2i, node2j, edge1.weight + edge2.weight));
                        adjList[node2i][node2j].add(new Day23Edge(node1i, node1j, edge1.weight + edge2.weight));

                        adjList[i][j].remove(0);
                        adjList[i][j].remove(0);
                    }
                }
            }
        }

        recur(adjList, 1, 0, 1, new HashSet<>(), 0);

        System.out.println(ret);
    }
}
