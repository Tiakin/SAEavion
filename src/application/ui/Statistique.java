package application.ui;

import java.awt.Dimension;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.graphstream.graph.Graph;
import org.graphstream.algorithm.Toolkit;
import org.graphstream.algorithm.ConnectedComponents;

/**
 * La classe Statistique.
 * 
 * @author Axel
 * @author Killian
 */
public class Statistique extends JDialog {

	// Attributs graphiques

	/** Le panel. */
	private JPanel panel = new JPanel();

	/** Le label degré moyen. */
	private JLabel degreeMeanLabel = new JLabel("Pas de graph");

	/** Le label composantes connexes. */
	private JLabel connectedComponentsLabel = new JLabel("");

	/** Le label nombre de noeuds. */
	private JLabel nodeCountLabel = new JLabel("");

	/** Le label nombre d'arrètes. */
	private JLabel edgeCountLabel = new JLabel("");

	/** Le label diamètre. */
	private JLabel diameterLabel = new JLabel("");

	/** Le label conflits. */
	private JLabel conflictsLabel = new JLabel("");

	/**
	 * Instancie une fenêtre statistique.
	 *
	 * @param owner La fenêtre principale
	 * 
	 */
	public Statistique(MenuPrincipal owner) {
		super(owner, true); // constructeur de la classe Mère: owner = propriétaire de la fenêtre (son
							// parent), le second paramètre est true pour la rendre modale

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

	/**
	 * Met à jour les statistiques affichées dans la boîte de dialogue.
	 *
	 * @param graph    Le graphe dont les statistiques doivent être calculées.
	 * @param conflits les conflits
	 * 
	 */
	public void updateStatistics(Graph graph, int conflits) {
		double degreeMean = Toolkit.averageDegree(graph);
		ConnectedComponents cc = new ConnectedComponents();
		cc.init(graph);
		int connectedComponentsCount = cc.getConnectedComponentsCount();
		int nodeCount = graph.getNodeCount();
		int edgeCount = graph.getEdgeCount();
		double diameter = Toolkit.diameter(graph);

		degreeMeanLabel.setText("Degré moyen : " + degreeMean);
		connectedComponentsLabel.setText("Nombre de composantes connexes : " + connectedComponentsCount);
		nodeCountLabel.setText("Nombre de noeuds : " + nodeCount);
		edgeCountLabel.setText("Nombre d'arrètes : " + edgeCount);
		diameterLabel.setText("Diamètre : " + ((diameter == Double.MIN_VALUE) ? "+∞" : diameter));
		conflictsLabel.setText("Nombre de conflits : " + conflits);
	}

	/**
	 * Affiche la boîte de dialogue.
	 * 
	 */
	public void showDialog() {
		setVisible(true);
	}
}
