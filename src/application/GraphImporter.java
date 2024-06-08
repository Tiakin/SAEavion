package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.graph.Graph;

public class GraphImporter {

    public static Graph importGraph(File file) {
        Graph graph = new MultiGraph("importedGraph");

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
    
    public static <T, E> Graph importGraph(GraphMap<T, E> gm) {
        Graph graph = new MultiGraph("importedGraph");

        for(GraphMap<T, E>.SommetPrinc p : gm.getNodes()) {
        	String node1 = p.getId()+"";

            // Ajouter les sommets au graphe s'ils n'existent pas déjà
            if (graph.getNode(node1) == null) {
                graph.addNode(node1);
            }
            for(GraphMap<T, E>.SommetAdj a : gm.getAdj(p)) {
	            String node2 = a.getId()+"";
	            if (graph.getNode(node2) == null) {
	                graph.addNode(node2);
	            }
	
	            // Ajouter l'arête au graphe
	            if(p.getId() < a.getId()) {
		            String edgeId = node1 + "-" + node2;
		            if (graph.getEdge(edgeId) == null) {
		                graph.addEdge(edgeId, node1, node2);
		            }
		            
		            GraphMap<T, E>.SommetPrinc p2 = null;
		            for(GraphMap<T, E>.SommetPrinc p3 : gm.getNodes()) {
		            	if(p3.getId() == a.getId()) {
		            		p2 = p3;
		            	}
		            }
		            System.out.println(edgeId+"  :  "+p.getVal()+"  "+p2.getVal());
	            }
            }
        }

        return graph;
    }

}
