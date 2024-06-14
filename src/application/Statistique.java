package application;



import java.awt.Dimension;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.algorithm.Toolkit;
import org.graphstream.algorithm.ConnectedComponents;

public class Statistique extends JDialog {

    // Attributs graphiques
    private JPanel panel = new JPanel();
    private JLabel degreeMeanLabel = new JLabel("Degré moyen :");
    private JLabel connectedComponentsLabel = new JLabel("Nombre de composantes connexes :");
    private JLabel nodeCountLabel = new JLabel("Nombre de noeuds :");
    private JLabel edgeCountLabel = new JLabel("Nombre d'arrètes :");
    private JLabel diameterLabel = new JLabel("Diamètre :");
    private JLabel conflictsLabel = new JLabel("Nombre de conflits :");

    public Statistique(MenuPrincipal owner) {
        super(owner, true); // constructeur de la classe Mère: owner = propriétaire de la fenêtre (son parent), le second paramètre est true pour la rendre modale
        
        setTitle("Statistique");
        setSize(300, 200);
        setResizable(false);

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(degreeMeanLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(connectedComponentsLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(nodeCountLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(edgeCountLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(diameterLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(conflictsLabel);

        getContentPane().add(panel);
    }

    public void updateStatistics(Graph graph) {
        double degreeMean = Toolkit.averageDegree(graph);
        ConnectedComponents cc = new ConnectedComponents();
        cc.init(graph);
        int connectedComponentsCount = cc.getConnectedComponentsCount();
        int nodeCount = graph.getNodeCount();
        int edgeCount = graph.getEdgeCount();
        double diameter = Toolkit.diameter(graph);
        int conflicts = calculateConflicts(graph); // Méthode spécifique pour calculer les conflits

        degreeMeanLabel.setText("Degré moyen : " + degreeMean);
        connectedComponentsLabel.setText("Nombre de composantes connexes : " + connectedComponentsCount);
        nodeCountLabel.setText("Nombre de noeuds : " + nodeCount);
        edgeCountLabel.setText("Nombre d'arrètes : " + edgeCount);
        diameterLabel.setText("Diamètre : " + diameter);
        conflictsLabel.setText("Nombre de conflits : " + conflicts);
    }

    private int calculateConflicts(Graph graph) {
        // Implémenter la logique pour calculer les conflits dans le graphe
        // Cette méthode doit être adaptée à votre contexte spécifique
        return 0; // Exemple
    }

    public void showDialog() {
        setVisible(true);
    }
}
