/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.graph.Graph;

public class GraphImporter {

    public static Graph importGraph(File file) {
        Graph graph = new SingleGraph("importedGraph");

        if (!file.exists()) {
            System.out.println("File does not exist: " + file.getAbsolutePath());
            return null;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            // Lire la valeur de kmax
            String line = reader.readLine();
            if (line == null) {
                System.out.println("File is empty or invalid format for kmax");
                return null;
            }
            int kmax = Integer.parseInt(line.trim());
            System.out.println("kmax: " + kmax);

            // Lire le nombre de sommets
            line = reader.readLine();
            if (line == null) {
                System.out.println("Invalid format for number of nodes");
                return null;
            }
            int numberOfNodes = Integer.parseInt(line.trim());
            System.out.println("Number of nodes: " + numberOfNodes);

            // Lire les liaisons
            while ((line = reader.readLine()) != null) {
                String[] nodes = line.trim().split(" ");
                if (nodes.length != 2) {
                    System.out.println("Invalid format for edge: " + line);
                    continue;
                }
                String node1 = nodes[0];
                String node2 = nodes[1];

                // Ajouter les sommets au graphe s'ils n'existent pas déjà
                if (graph.getNode(node1) == null) {
                    graph.addNode(node1);
                }
                if (graph.getNode(node2) == null) {
                    graph.addNode(node2);
                }

                // Ajouter l'arête au graphe
                String edgeId = node1 + "-" + node2;
                if (graph.getEdge(edgeId) == null) {
                    graph.addEdge(edgeId, node1, node2);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return graph;
    }

    public static void main(String[] args) {
        // Spécifiez le chemin absolu du fichier à importer
        File file = new File("src/application/graph-test10.txt");

        // Vérifiez si le fichier existe
        if (!file.exists()) {
            System.out.println("File not found: " + file.getAbsolutePath());
            return;
        }

        // Importer le graphe à partir du fichier
        Graph graph = importGraph(file);

        // Afficher les informations du graphe importé
        if (graph != null) {
            System.out.println("Graph imported with " + graph.getNodeCount() + " nodes and " + graph.getEdgeCount() + " edges.");
        } else {
            System.out.println("Failed to import graph.");
        }
    }
}
