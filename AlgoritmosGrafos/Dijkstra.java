package AlgoritmosGrafos;

import java.util.*;

public class Dijkstra {

    public static Map<String, Object> execute(List<List<Edge>> graph, int source, int destination, int numVertices) {
        int[] distances = new int[numVertices + 1];
        Edge[] predecessors = new Edge[numVertices + 1];
        boolean[] visited = new boolean[numVertices + 1];
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));

        Arrays.fill(distances, Integer.MAX_VALUE);
        distances[source] = 0;
        pq.offer(new int[]{source, 0});

        while (!pq.isEmpty()) {
            int[] current = pq.poll();
            int currentVertex = current[0];

            if (visited[currentVertex]) continue;
            visited[currentVertex] = true;

            for (Edge edge : graph.get(currentVertex)) {
                if (!visited[edge.destino]) {
                    int newDist = distances[currentVertex] + edge.peso;
                    if (newDist < distances[edge.destino]) {
                        distances[edge.destino] = newDist;
                        predecessors[edge.destino] = edge;
                        pq.offer(new int[]{edge.destino, newDist});
                    }
                }
            }
        }

        Map<String, Object> result = new HashMap<>();
        result.put("distances", distances);
        result.put("predecessors", predecessors);
        return result;
    }

    public static List<Edge> reconstructPath(Edge[] predecessors, int destination) {
        List<Edge> path = new ArrayList<>();
        for (int at = destination; predecessors[at] != null; at = predecessors[at].origem) {
            path.add(predecessors[at]);
        }
        Collections.reverse(path);
        return path;
    }
}
