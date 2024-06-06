/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package application;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.graphstream.graph.*;


public class GraphExporter {

    public static void exportGraph(File file, Graph graph) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            // Écrire la valeur de kmax (ici toujours 0)
            writer.write("0");
            writer.newLine();

            // Écrire le nombre de sommets
            writer.write(String.valueOf(graph.getNodeCount()));
            writer.newLine();

            // Écrire les arêtes
            
            for (Edge edge : graph.edges().toList()) {
                writer.write(edge.getNode0() + " " + edge.getNode1());
                writer.newLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Spécifiez le chemin du fichier à importer
        File importFile = new File("src/application/graph-test10.txt");

        // Importer le graphe à partir du fichier
        Graph graph = GraphImporter.importGraph(importFile);

        // Vérifiez si l'importation a réussi
        if (graph != null) {
            // Spécifiez le chemin du fichier à exporter
            File exportFile = new File("src/application/exported-graph.txt");

            // Exporter le graphe vers le fichier
            exportGraph(exportFile, graph);

            System.out.println("Graph exported to " + exportFile.getAbsolutePath());
        } else {
            System.out.println("Failed to import graph.");
        }
    }
}
