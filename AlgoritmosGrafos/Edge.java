package AlgoritmosGrafos;

public class Edge {
    public int origem;
    public int destino;
    public int peso;

    public Edge(int origem, int destino, int peso) {
        this.origem = origem;
        this.destino = destino;
        this.peso = peso;
    }

    @Override
    public String toString() {
        return "(" + origem + " -> " + destino + ", peso: " + peso + ")";
    }
}
