package AlgoritmosGrafos;

import java.util.*;

public class Dijkstra {
    public int[] dijkstra(List<List<Edge>> graph, int source) {
        int numVertices = graph.size();
        int[] distancias = new int[numVertices];
        Arrays.fill(distancias, Integer.MAX_VALUE); // Inicializa as distâncias com infinito
        distancias[source] = 0;

        PriorityQueue<Edge> pq = new PriorityQueue<>(Comparator.comparingInt(e -> e.peso));
        pq.add(new Edge(source, source, 0)); // Adiciona o vértice de origem

        while (!pq.isEmpty()) {
            Edge current = pq.poll();
            int u = current.destino;

            for (Edge vizinho : graph.get(u)) {
                int v = vizinho.destino;
                int peso = vizinho.peso;

                if (distancias[u] + peso < distancias[v]) {
                    distancias[v] = distancias[u] + peso;
                    pq.add(new Edge(u, v, distancias[v]));
                }
            }
        }
        return distancias;
    }
}