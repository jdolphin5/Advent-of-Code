import java.io.*;
import java.util.*;

public class FileDemo25 {

    //Bellman-Ford algorithm for computing the shortest paths from:
    //https://www.geeksforgeeks.org/shortest-path-unweighted-graph/
    private static boolean BFS(List<Integer>[] adj, int src,
    int dest, int v, int[] pred, int[] dist)
        {
        // a queue to maintain queue of vertices whose
        // adjacency list is to be scanned as per normal
        // BFS algorithm using LinkedList of Integer type
        LinkedList<Integer> queue = new LinkedList<Integer>();

        // boolean array visited[] which stores the
        // information whether ith vertex is reached
        // at least once in the Breadth first search
        boolean visited[] = new boolean[v];

        // initially all vertices are unvisited
        // so v[i] for all i is false
        // and as no path is yet constructed
        // dist[i] for all i set to infinity
        for (int i = 0; i < v; i++) {
            visited[i] = false;
            dist[i] = Integer.MAX_VALUE;
            pred[i] = -1;
        }

        // now source is first to be visited and
        // distance from source to itself should be 0
        visited[src] = true;
        dist[src] = 0;
        queue.add(src);

        // bfs Algorithm
        while (!queue.isEmpty()) {
        int u = queue.remove();

        for (int i = 0; i < adj[u].size(); i++) {
            if (visited[adj[u].get(i)] == false) {
                visited[adj[u].get(i)] = true;
                dist[adj[u].get(i)] = dist[u] + 1;
                pred[adj[u].get(i)] = u;
                queue.add(adj[u].get(i));

                // stopping condition (when we find
                // our destination)
                if (adj[u].get(i) == dest)
                    return true;
                }
            }
        }
        return false;
    }
    public static void main(String[] args) {
        File file = new File("2023/day-25/input.txt");
        List<String> stringPerLineList = new ArrayList<>();

        try {
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine())
                stringPerLineList.add(sc.nextLine());
            sc.close();
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        //Union find brute force is too heavy
        //Try Monte Carlo approach: randomly choose two vertices and find the shortest path between them
        //Increment the traffic counter of each edge every time it is visited
        //Remove the most common 3 edges (2 * 3 = 6 total)
        //Unionfind the remaining nodes to find the count of each of the 2 final groups
        //Multiply the result

        Map<String, Integer> componentNumberMap = new HashMap<>();
        List<List<String>> connectedList = new ArrayList<>();
        Map<String, Integer> edgeMap = new HashMap<>();

        int componentNumber = 0;

        for (String s : stringPerLineList) {
            String[] split = s.split(": ", 0);
            String componentOne = split[0];
            String[] split2 = split[1].split(" ", 0);

            List<String> connectedComponentsList = new ArrayList<>();

            if (componentNumberMap.get(componentOne) == null) {
                componentNumberMap.put(componentOne, componentNumber++);
            }

            for (String t : split2) { 
                connectedComponentsList.add(componentOne);
                connectedComponentsList.add(t);
                connectedList.add(connectedComponentsList);
                connectedComponentsList = new ArrayList<>();

                if (componentNumberMap.get(t) == null) {
                    componentNumberMap.put(t, componentNumber++);
                }
            }
        }

        List<Integer>[] graph = new ArrayList[componentNumber];

        for (int i = 0; i < componentNumber; i++) {
            graph[i] = new ArrayList<>();
        }

        for (int i = 0; i < connectedList.size(); i++) {
            int connectedNumOne = componentNumberMap.get(connectedList.get(i).get(0));
            int connectedNumTwo = componentNumberMap.get(connectedList.get(i).get(1));
            
            graph[connectedNumOne].add(connectedNumTwo);
            graph[connectedNumTwo].add(connectedNumOne);
        }

        final int NUM_OF_RANDOM_PAIRS = 100;

        for (int i = 0; i < NUM_OF_RANDOM_PAIRS; i++) {
            Random rand = new Random();
            int randomInt1 = rand.nextInt(componentNumber);
            int randomInt2 = rand.nextInt(componentNumber);

            if (randomInt1 == randomInt2) continue;

            int pred[] = new int[componentNumber];
            int dist[] = new int[componentNumber];

            if (BFS(graph, randomInt1, randomInt2, componentNumber, pred, dist) == false) {
                System.out.println("Given source and destination" + "are not connected");
            }

            // LinkedList to store path
            LinkedList<Integer> path = new LinkedList<Integer>();

            int crawl = randomInt2;
            String last = String.valueOf(crawl);
            path.add(crawl);
            while (pred[crawl] != -1) {
                path.add(pred[crawl]);
                crawl = pred[crawl];
                String cur = String.valueOf(crawl);
                edgeMap.put(last + " to " + cur, edgeMap.getOrDefault(last + " to " + cur, 0) + 1);
                edgeMap.put(cur + " to " + last, edgeMap.getOrDefault(cur + " to " + last, 0) + 1);
                last = cur;
            }
    
            // Print path
            System.out.println("\nPath is ::");
            for (int x = path.size() - 1; x >= 0; x--) {
                System.out.print(path.get(x) + " ");
            }
        }

        //Remove the 3 most common edges (both directions for each)
        for (int x = 0; x < 6; x++) {
            int max = 0;
            String toRemove = "";
            for (Map.Entry<String, Integer> entry : edgeMap.entrySet()) {
                String key = entry.getKey();
                int value = entry.getValue();

                if (value > max) {
                    max = value;
                    toRemove = key;
                }
            }

            System.out.println("\n" + toRemove);
            String[] splitString = toRemove.split(" to ", 0);
            int a = Integer.parseInt(splitString[0]);
            int b = Integer.parseInt(splitString[1]);
            graph[a].remove(Integer.valueOf(b));
            graph[b].remove(Integer.valueOf(a));
            edgeMap.remove(toRemove);
        }

        //Group components
        UnionFind uf = new UnionFind(componentNumber);
        for (int i = 0; i < graph.length; i++) {
            for (int j = 0; j < graph[i].size(); j++) {
                uf.union(i, graph[i].get(j));
            }
        }

        Map<Integer, Integer> groupSizeMap = new HashMap<>();

        for (int i = 0; i < graph.length; i++) {
            groupSizeMap.put(uf.find(i), groupSizeMap.getOrDefault(uf.find(i), 0) + 1);
        }

        int p = 0;

        for (Map.Entry<Integer, Integer> entry : groupSizeMap.entrySet()) {
            System.out.println("group " + (p++) + " size: " + entry.getValue());
        }
    }
}


/*
//in the main class:
UnionFind uf = new UnionFind(n);

//check if edges are connected
if(uf.isConnected(edge[0], edge[1])){
    continue;
}

// union the two edges build the graph
uf.union(edge[0], edge[1]);

 */
