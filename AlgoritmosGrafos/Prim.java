package AlgoritmosGrafos;

import java.util.*;

public class Prim {
    public List<Edge> prim(List<List<Edge>> graph) {
        int numVertices = graph.size();
        boolean[] visitado = new boolean[numVertices];
        PriorityQueue<Edge> pq = new PriorityQueue<>(Comparator.comparingInt(e -> e.peso));
        List<Edge> mst = new ArrayList<>();
        int pesoTotal = 0;

        visitado[1] = true;
        pq.addAll(graph.get(1));

        while (!pq.isEmpty()) {
            Edge e = pq.poll();
            if (visitado[e.destino]) continue;

            visitado[e.destino] = true;
            mst.add(e);
            pesoTotal += e.peso;

            for (Edge next : graph.get(e.destino)) {
                if (!visitado[next.destino]) {
                    pq.add(next);
                }
            }
        }

        System.out.println("Árvore Geradora Mínima (Prim): " + mst);
        System.out.println("Peso total da MST: " + pesoTotal);

        return mst;
    }
}


