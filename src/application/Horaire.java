package application;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Horaire extends JDialog implements ActionListener{

	// Attibuts graphiques
	private JLabel labelNombre = new JLabel("Entrez l'heure (HH:MM) :");
	private JPanel panel = new JPanel();
	private JTextField textFieldHeure = new JTextField(2);
	private JLabel labelSeparation = new JLabel(":");
    private JTextField textFieldMinute = new JTextField(2);
	private JButton retourButton = new JButton("Retour");
	private JButton validerButton = new JButton("Valider");
	private int Horaire;
	

	public Horaire(MenuPrincipal owner) {

	    super(owner,true); //constructeur de la classe Mère: owner = propriétaire de la fenêtre (son parent), le second paramètre est true pour la rendre modale
	    
		setTitle("Kmax");
		setSize(250, 100);
	    setResizable(false);
	
	    panel.add(labelNombre);
	    panel.add(textFieldHeure);
        panel.add(labelSeparation);
        panel.add(textFieldMinute);
	    panel.add(retourButton);
	    panel.add(validerButton);
	
	    getContentPane().add(panel);
        
        retourButton.addActionListener(this);
        validerButton.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {
	    if (e.getSource() == retourButton){
	        this.setVisible(false); //on ferme la fenêtre
	        Horaire = -1;
	    }
	    if (e.getSource() == validerButton){
	    	Horaire = Integer.parseInt(textFieldHeure.getText())*100+Integer.parseInt(textFieldMinute.getText());
	        this.setVisible(false); //on ferme la fenêtre
	    }
	    dispose();
	}
	public int showDialog() {
		setVisible(true);
		return Horaire;
	}
}