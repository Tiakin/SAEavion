package application;

import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Statistique extends JDialog {

	// Attibuts graphiques
	private JPanel panel = new JPanel();

	

	public Statistique(MenuPrincipal owner) {

	    super(owner,true); //constructeur de la classe Mère: owner = propriétaire de la fenêtre (son parent), le second paramètre est true pour la rendre modale
	    
		setTitle("Statistique");
		setSize(300, 200);
	    setResizable(false);
	    
	    
	    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("Degré moyen :"));
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(new JLabel("Nombre de composantes connexes :"));
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(new JLabel("Nombre de noeuds :"));
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(new JLabel("Nombre d'arrètes :"));
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(new JLabel("Diamètre :"));
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(new JLabel("Nombre de conflits :"));
	
	    getContentPane().add(panel);
	}
	public void showDialog() {
		setVisible(true);
	}
}