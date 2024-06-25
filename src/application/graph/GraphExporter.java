package application.graph;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;

/**
 * La classe GraphExporter.
 * 
 * @author Axel
 * @author Killian
 */
public class GraphExporter {

	/**
	 * Le constructeur GraphExporter.
	 */
	private GraphExporter() {
	}

	/**
	 * exporter le graph.
	 *
	 * @param file      le fichier
	 * @param graph     le graph
	 * @param kmaxValue le kmax
	 * 
	 * 
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

	/**
	 * exporter la coloration du graph.
	 *
	 * @param file le fichier
	 * @param gm   le graph
	 * 
	 * 
	 */
	public static void exportGraphColor(File file, GraphMap<String, Integer> gm) {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
			for (GraphMap<String, Integer>.SommetPrinc p : gm.getNodes()) {
				writer.write(p.getVal() + ";" + p.getCoul());
				writer.newLine();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
