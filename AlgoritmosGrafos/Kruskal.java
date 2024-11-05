package AlgoritmosGrafos;

import java.util.*;

public class Kruskal {
    public List<Edge> kruskal(int numVertices, List<Edge> edges) {
        Collections.sort(edges, Comparator.comparingInt(e -> e.peso)); // Ordena as arestas pelo peso
        UnionFind uf = new UnionFind(numVertices);
        List<Edge> mst = new ArrayList<>();

        for (Edge e : edges) {
            if (uf.find(e.origem) != uf.find(e.destino)) { // Verifica se a aresta forma um ciclo
                uf.union(e.origem, e.destino);
                mst.add(e); // Adiciona a aresta à MST
            }
        }
        return mst;
    }
}

class UnionFind {
    private int[] parent;
    private int[] rank;

    public UnionFind(int n) {
        parent = new int[n + 1];
        rank = new int[n + 1];
        for (int i = 0; i <= n; i++) {
            parent[i] = i;
        }
    }

    public int find(int u) {
        if (u != parent[u]) {
            parent[u] = find(parent[u]); // Compressão de caminho
        }
        return parent[u];
    }

    public void union(int u, int v) {
        int rootU = find(u);
        int rootV = find(v);

        if (rootU != rootV) {
            if (rank[rootU] > rank[rootV]) {
                parent[rootV] = rootU;
            } else if (rank[rootU] < rank[rootV]) {
                parent[rootU] = rootV;
            } else {
                parent[rootV] = rootU;
                rank[rootU]++;
            }
        }
    }
}

