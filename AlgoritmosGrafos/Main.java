package AlgoritmosGrafos;

import java.io.*;
import java.util.*;

public class Main {

    private static List<List<Edge>> graph;
    private static List<Edge> edges;
    private static int numVertices;

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        while (true) {
            exibirMenuPrincipal();
            int choice = scan.nextInt();

            if (choice == 4) {
                System.out.println("Encerrando a aplicação...");
                break;
            }

            String inputFileName = obterArquivoEntrada(scan);
            if (inputFileName.isEmpty()) {
                System.out.println("Opção inválida. O programa será encerrado.");
                continue;
            }

            inicializarGrafo(inputFileName);

            long tempoInicial = System.currentTimeMillis();
            executarAlgoritmo(choice, scan, inputFileName);
            long tempoFinal = System.currentTimeMillis();

            double tempoExecucao = (tempoFinal - tempoInicial) / 1000.0;
            System.out.printf("Tempo de execução: %.3f segundos\n", tempoExecucao);
        }

        scan.close();
    }

    private static void exibirMenuPrincipal() {
        System.out.println("\nEscolha o algoritmo de grafo:");
        System.out.println("1: Algoritmo de Dijkstra");
        System.out.println("2: Algoritmo de Kruskal");
        System.out.println("3: Algoritmo de Prim");
        System.out.println("4: Fechar aplicação");
    }

    private static String obterArquivoEntrada(Scanner scan) {
        System.out.println("Selecione a estrada que deseja percorrer:");
        System.out.println("1: Colorado");
        System.out.println("2: San Francisco Bay Area");
        System.out.println("3: New York City");

        int fileChoice = scan.nextInt();
        return switch (fileChoice) {
            case 1 -> "USA-road-d.COL.gr";
            case 2 -> "USA-road-d.BAY.gr";
            case 3 -> "USA-road-d.NY.gr";
            default -> "";
        };
    }

    private static void inicializarGrafo(String inputFileName) {
        try (BufferedReader br = new BufferedReader(new FileReader(inputFileName))) {
            String line;
            edges = new ArrayList<>();
            graph = new ArrayList<>();

            while ((line = br.readLine()) != null) {
                if (line.startsWith("p")) {
                    configurarVertices(line);
                } else if (line.startsWith("a")) {
                    adicionarAresta(line);
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao carregar o arquivo: " + e.getMessage());
        }
    }

    private static void configurarVertices(String line) {
        String[] parts = line.split(" ");
        numVertices = Integer.parseInt(parts[2]);

        graph = new ArrayList<>(numVertices + 1);
        for (int i = 0; i <= numVertices; i++) {
            graph.add(new ArrayList<>());
        }
    }

    private static void adicionarAresta(String line) {
        String[] parts = line.split(" ");
        int origem = Integer.parseInt(parts[1]);
        int destino = Integer.parseInt(parts[2]);
        int peso = Integer.parseInt(parts[3]);

        Edge edge = new Edge(origem, destino, peso);
        graph.get(origem).add(edge);
        edges.add(edge);
    }

    private static void executarAlgoritmo(int choice, Scanner scan, String inputFileName) {
        switch (choice) {
            case 1 -> executarDijkstra(scan, inputFileName);
            case 2 -> executarKruskal(inputFileName);
            case 3 -> executarPrim(inputFileName);
            default -> System.out.println("Opção inválida.");
        }
    }

    private static void executarDijkstra(Scanner scan, String inputFileName) {
        System.out.print("Digite o vértice de origem para iniciar o Dijkstra: ");
        int source = scan.nextInt();

        if (source < 1 || source > numVertices) {
            System.out.println("Vértice de origem inválido. Tente novamente.");
            return;
        }

        String outputFileName = "Dijkstra" + inputFileName + ".txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFileName))) {
            writer.write("Resultados do Algoritmo de Dijkstra\n-----------------------------------\n");

            for (int destination = source + 1; destination <= numVertices; destination++) {
                Map<String, Object> result = Dijkstra.execute(graph, source, destination, numVertices);
                int[] distances = (int[]) result.get("distances");

                if (distances[destination] == Integer.MAX_VALUE) {
                    writer.write(String.format("Não há caminho de %d para %d\n", source, destination));
                } else {
                    writer.write(String.format("Origem: %d, Destino: %d, Distância: %d\n",
                            source, destination, distances[destination]));
                }
            }

            System.out.println("Resultados salvos em " + outputFileName);
        } catch (IOException e) {
            System.err.println("Erro ao salvar o arquivo: " + e.getMessage());
        }
    }

    private static void executarKruskal(String inputFileName) {
        System.out.println("Executando Kruskal...");
        List<Edge> mst = new Kruskal().kruskal(numVertices, edges);

        salvarResultadoMST(mst, "Kruskal" + inputFileName + ".txt");
    }

    private static void executarPrim(String inputFileName) {
        System.out.println("Executando Prim...");
        List<Edge> mst = new Prim().prim(graph);

        salvarResultadoMST(mst, "Prim" + inputFileName + ".txt");
    }

    private static void salvarResultadoMST(List<Edge> mst, String outputFileName) {
        int pesoTotal = mst.stream().mapToInt(e -> e.peso).sum();
        Set<Integer> vertices = new HashSet<>();
        mst.forEach(e -> {
            vertices.add(e.origem);
            vertices.add(e.destino);
        });

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFileName))) {
            writer.write("Árvore Geradora Mínima (MST):\n");
            for (Edge e : mst) {
                writer.write(String.format("Origem: %d, Destino: %d, Peso: %d\n", e.origem, e.destino, e.peso));
            }
            writer.write("\nVértices: " + vertices + "\n");
            writer.write("Peso total: " + pesoTotal + "\n");
            System.out.println("Resultados salvos em " + outputFileName);
        } catch (IOException e) {
            System.err.println("Erro ao salvar o arquivo: " + e.getMessage());
        }
    }
}
