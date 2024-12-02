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
                    System.out.println("Digite o vértice de origem para iniciar o Dijkstra:");
                    int source = scan.nextInt();

                    // Verifica se o vértice de origem está dentro do intervalo válido
                    if (source < 1 || source > numVertices) {
                        System.out.println(
                                "Vértice de origem inválido. Certifique-se de que está entre 1 e " + numVertices);
                        break;
                    }

                    // Nome do arquivo de saída
                    String outputFileName = "Dijkstra" + inputFileName + ".txt";

                    try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFileName))) {
                        // Adiciona um cabeçalho ao arquivo
                        writer.write("Resultados do Algoritmo de Dijkstra\n");
                        writer.write("-----------------------------------\n");

                        // Itera automaticamente do vértice de origem até o fim do grafo
                        for (int destination = source + 1; destination <= numVertices; destination++) {
                            // Executa o algoritmo de Dijkstra
                            Map<String, Object> result = Dijkstra.execute(graph, source, destination, numVertices);
                            int[] distances = (int[]) result.get("distances");
                            // Edge[] predecessors = (Edge[]) result.get("predecessors");

                            if (distances[destination] == Integer.MAX_VALUE) {
                                String noPathMessage = "Não há caminho do vértice " + source + " ao vértice "
                                        + destination;
                                System.out.println(noPathMessage);
                                writer.write(noPathMessage + "\n");
                            } else {
                                String summary = String.format(
                                        "Vértice de origem: %d, Vértice final: %d, Distância total: %d",
                                        source, destination, distances[destination]);
                                // System.out.println(summary);
                                writer.write(summary + "\n");

                                // Adiciona detalhes das arestas (se necessário)
                                /*
                                 * List<Edge> path = Dijkstra.reconstructPath(predecessors, destination);
                                 * for (Edge edge : path) {
                                 * String.format("vértice de origem: %d, vértice final: %d, peso: %d",
                                 * edge.origem, edge.destino, edge.peso);
                                 * 
                                 * }
                                 */
                            }
                        }

                        System.out.println("Os resultados foram salvos no arquivo: " + outputFileName);
                    } catch (IOException e) {
                        System.out.println("Erro ao escrever no arquivo: " + e.getMessage());
                    }
                    break;

                case 2:
                    System.out.println("Executando o Algoritmo de Kruskal para encontrar a MST...");

                    // Executa o algoritmo de Kruskal
                    List<Edge> mstKruskal = new Kruskal().kruskal(numVertices, edges);

                    // Caminho do arquivo de saída
                    String outputPathKruskal = "Kruskal" + inputFileName + ".txt";

                    // Conjunto para armazenar vértices únicos
                    Set<Integer> vertices = new HashSet<>();
                    int pesoTotal = 0;

                    // Gera o conjunto de vértices e calcula o peso total
                    for (Edge e : mstKruskal) {
                        vertices.add(e.origem);
                        vertices.add(e.destino);
                        pesoTotal += e.peso;
                    }

                    // Salvando no arquivo
                    try (FileWriter writer = new FileWriter(outputPathKruskal)) {
                        writer.write("Árvore Geradora Mínima (MST) usando Kruskal:\n");
                        for (Edge e : mstKruskal) {
                            writer.write(
                                    "Origem: " + e.origem + ", Destino: " + e.destino + ", Peso: " + e.peso + "\n");
                        }
                        writer.write("\nVértices do Caminho Mínimo:\n");
                        writer.write(vertices.toString() + "\n");
                        writer.write("Peso total da MST: " + pesoTotal + "\n");
                    } catch (IOException ex) {
                        System.err.println("Erro ao salvar o resultado no arquivo: " + ex.getMessage());
                    }

                    System.out.println("O resultado foi salvo no arquivo: " + outputPathKruskal);
                    algorithm = "Kruskal";
                    break;

                case 3:
                    System.out.println("Executando o Algoritmo de Prim para encontrar a MST...");

                    // Executa o algoritmo de Prim
                    List<Edge> mstPrim = new Prim().prim(graph);

                    // Caminho do arquivo de saída
                    String outputPathPrim = "Prim" + inputFileName + ".txt";

                    // Conjunto para armazenar vértices únicos
                    Set<Integer> verticesPrim = new HashSet<>();
                    int pesoTotalPrim = 0;

                    // Gera o conjunto de vértices e calcula o peso total
                    for (Edge e : mstPrim) {
                        verticesPrim.add(e.origem);
                        verticesPrim.add(e.destino);
                        pesoTotalPrim += e.peso;
                    }

                    // Salvando no arquivo
                    try (FileWriter writer = new FileWriter(outputPathPrim)) {
                        writer.write("Árvore Geradora Mínima (MST) usando Prim:\n");
                        for (Edge e : mstPrim) {
                            writer.write(
                                    "Origem: " + e.origem + ", Destino: " + e.destino + ", Peso: " + e.peso + "\n");
                        }
                        writer.write("\nVértices do Caminho Mínimo:\n");
                        writer.write(verticesPrim.toString() + "\n");
                        writer.write("Peso total da MST: " + pesoTotalPrim + "\n");
                    } catch (IOException ex) {
                        System.err.println("Erro ao salvar o resultado no arquivo: " + ex.getMessage());
                    }

                    System.out.println("O resultado foi salvo no arquivo: " + outputPathPrim);
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