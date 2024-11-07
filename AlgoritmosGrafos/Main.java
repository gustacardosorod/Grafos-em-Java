package AlgoritmosGrafos;
import java.io.*;
import java.util.*;

public class Main {
    
    static List<List<Edge>> graph;
    static List<Edge> edges;
    static int numVertices;

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        while (true) {
            System.out.println("");
            System.out.println(
                    "Escolha o algoritmo de grafo -> \n" +
                    "1: Algoritmo de Dijkstra \n" +
                    "2: Algoritmo de Kruskal \n" +
                    "3: Algoritmo de Prim \n" +
                    "Digite 4 para fechar a aplicação");

            int choice = scan.nextInt();

            if (choice == 4) {
                break;
            }

            System.out.println(
                    "Selecione a estrada que deseja percorrer: \n" +
                    "1: Colorado \n" +
                    "2: San Francisco Bay Area \n" +
                    "3: New York City");

            int filechoice = scan.nextInt();
            String inputFileName = "";

            switch (filechoice) {
                case 1:
                    inputFileName = "USA-road-d.COL.gr";
                    break;
                case 2:
                    inputFileName = "USA-road-d.BAY.gr";
                    break;
                case 3:
                    inputFileName = "USA-road-d.NY.gr";
                    break;
                default:
                    System.out.println("Opção inválida. O programa será encerrado");
                    continue;
            }

            // Configura o grafo dinamicamente com base no arquivo selecionado
            setupGraphFromFile(inputFileName);

            String algorithm = "";
            double tempoInicial = System.currentTimeMillis();

            switch (choice) {
                case 1:
                    System.out.println("Digite o vértice de origem para o Dijkstra:");
                    int source = scan.nextInt();

                    // Executa o algoritmo de Dijkstra
                    int[] distancias = new Dijkstra().dijkstra(graph, source);

                    // Exibe as distâncias calculadas
                    System.out.println("Distâncias a partir do vértice " + source + ":");
                    for (int i = 1; i < distancias.length; i++) {
                        System.out.println("Vértice " + i + ": " + distancias[i]);
                    }
                    algorithm = "Dijkstra";
                    break;

                case 2:
                    System.out.println("Executando o Algoritmo de Kruskal para encontrar a MST...");

                    // Executa o algoritmo de Kruskal
                    List<Edge> mstKruskal = new Kruskal().kruskal(numVertices, edges);

                    // Exibe as arestas da MST
                    System.out.println("Arestas da Árvore Geradora Mínima (MST) usando Kruskal:");
                    for (Edge e : mstKruskal) {
                        System.out.println("Origem: " + e.origem + ", Destino: " + e.destino + ", Peso: " + e.peso);
                    }
                    algorithm = "Kruskal";
                    break;

                case 3:
                    System.out.println("Executando o Algoritmo de Prim para encontrar a MST...");

                    // Executa o algoritmo de Prim
                    List<Edge> mstPrim = new Prim().prim(graph);

                    // Exibe as arestas da MST
                    System.out.println("Arestas da Árvore Geradora Mínima (MST) usando Prim:");
                    for (Edge e : mstPrim) {
                        System.out.println("Origem: " + e.origem + ", Destino: " + e.destino + ", Peso: " + e.peso);
                    }
                    algorithm = "Prim";
                    break;

                default:
                    System.out.println("Opção inválida.");
                    continue;
            }

            double tempoFinal = System.currentTimeMillis();
            double tempoExecucao = (tempoFinal - tempoInicial) / 1000;
            System.out.println("Algoritmo: " + algorithm);
            System.out.println("Tempo de execução em segundos: " + tempoExecucao);
        }

        scan.close();
    }

    public static void setupGraphFromFile(String inputFileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(inputFileName))) {
            String line;
            edges = new ArrayList<>();
            graph = new ArrayList<>();

            while ((line = br.readLine()) != null) {
                if (line.startsWith("p")) {
                    String[] parts = line.split(" ");
                    numVertices = Integer.parseInt(parts[2]);

                    // Inicializa o grafo dinamicamente com base no número de vértices
                    graph = new ArrayList<>(numVertices + 1);
                    for (int i = 0; i <= numVertices; i++) {
                        graph.add(new ArrayList<>());
                    }
                }

                if (line.startsWith("a")) {
                    String[] parts = line.split(" ");
                    int origem = Integer.parseInt(parts[1]);
                    int destino = Integer.parseInt(parts[2]);
                    int peso = Integer.parseInt(parts[3]);

                    Edge edge = new Edge(origem, destino, peso);
                    graph.get(origem).add(edge);
                    edges.add(edge); // Adiciona a aresta para uso no algoritmo de Kruskal
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}