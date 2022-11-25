import java.util.*;

class Pair implements Comparable<Pair> {
    int vertex;
    int weight;

    Pair(int vertex, int weight) {
        this.vertex = vertex;
        this.weight = weight;
    }

    public int compareTo(Pair that) {
        return this.weight - that.weight;
    }
}

class Solution {
    // Function to find the shortest distance of all the vertices
    // from the source vertex S.
    static int[] dijkstra(int V, ArrayList<ArrayList<ArrayList<Integer>>> adj, int S) {

        boolean[] visited = new boolean[V];
        PriorityQueue<Pair> queue = new PriorityQueue<>();
        int[] distance = new int[V];

        Arrays.fill(distance, Integer.MAX_VALUE);
        distance[S] = 0;
        queue.add(new Pair(S, 0));

        while (!queue.isEmpty()) {
            Pair pair = queue.poll();
            int v = pair.vertex;
            int wt = pair.weight;

            if (visited[v])
                continue;

            visited[v] = true;

            ArrayList<ArrayList<Integer>> neighbors = adj.get(v);
            for (ArrayList<Integer> list : neighbors) {
                int newVertex = list.get(0);
                int newWeight = list.get(1);

                if (distance[newVertex] > distance[v] + newWeight) {
                    distance[newVertex] = distance[v] + newWeight;
                    queue.add(new Pair(newVertex, distance[newVertex]));
                }

            }
        }

        return distance;
    }
}