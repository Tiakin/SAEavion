package application;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;


/**
 * La classe GraphExporter.
 */
public class GraphExporter {

    /**
     * exporter le graph.
     *
     * @param file le fichier
     * @param graph le graph
     * @param kmaxValue le kmax
     */
    public static void exportGraph(File file, Graph graph, int kmaxValue) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            // Écrire la valeur de kmax
            writer.write(kmaxValue);
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
}
