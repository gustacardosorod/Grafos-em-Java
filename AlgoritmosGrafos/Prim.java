package AlgoritmosGrafos;

import java.util.*;

public class Prim {
    public List<Edge> prim(List<List<Edge>> graph) {
        int numVertices = graph.size();
        boolean[] visitado = new boolean[numVertices];
        PriorityQueue<Edge> pq = new PriorityQueue<>(Comparator.comparingInt(e -> e.peso));
        List<Edge> mst = new ArrayList<>();
        int pesoTotal = 0;

        // Conjunto para armazenar os vértices únicos
        Set<Integer> vertices = new HashSet<>();

        // Começa pelo vértice 1 (ajustar conforme necessário)
        visitado[1] = true;
        vertices.add(1);
        pq.addAll(graph.get(1));

        while (!pq.isEmpty()) {
            Edge e = pq.poll();
            if (visitado[e.destino]) continue;

            visitado[e.destino] = true;
            mst.add(e);
            vertices.add(e.destino);
            pesoTotal += e.peso;

            for (Edge next : graph.get(e.destino)) {
                if (!visitado[next.destino]) {
                    pq.add(next);
                }
            }
        }

        // Exibe no console (opcional)
        System.out.println("Árvore Geradora Mínima (MST) usando Prim:");
        for (Edge e : mst) {
            System.out.println("Origem: " + e.origem + ", Destino: " + e.destino + ", Peso: " + e.peso);
        }
        System.out.println("Peso total da MST: " + pesoTotal);

        return mst; // Retorna a MST calculada
    }
}
