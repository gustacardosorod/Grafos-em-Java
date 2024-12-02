package AlgoritmosGrafos;

import java.util.*;

public class Kruskal {
    public List<Edge> kruskal(int numVertices, List<Edge> edges) {
        Collections.sort(edges, Comparator.comparingInt(e -> e.peso));
        UnionFind uf = new UnionFind(numVertices);
        List<Edge> mst = new ArrayList<>();
        int pesoTotal = 0;

        // Conjunto para armazenar os vértices únicos
        Set<Integer> vertices = new HashSet<>();

        for (Edge e : edges) {
            if (uf.find(e.origem) != uf.find(e.destino)) {
                uf.union(e.origem, e.destino);
                mst.add(e);
                vertices.add(e.origem);
                vertices.add(e.destino);
                pesoTotal += e.peso;
            }
        }

        // Exibe no console (apenas para depuração)
        System.out.println("Árvore Geradora Mínima (MST) usando Kruskal:");
        for (Edge e : mst) {
            System.out.println("Origem: " + e.origem + ", Destino: " + e.destino + ", Peso: " + e.peso);
        }
        System.out.println("Peso total da MST: " + pesoTotal);

        return mst; // Retorna apenas as arestas da MST
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