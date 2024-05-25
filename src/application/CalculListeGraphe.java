package application;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class CalculListeGraphe extends JDialog implements ActionListener{

	// Attibuts graphiques
	private JLabel labelNom = new JLabel("Nom");
	private JTextField nomField = new JTextField("Nouvelle Pizza", 20);
	
	private JLabel labelPrix = new JLabel("Prix");
	private JTextField prixField = new JTextField("8", 5);
	
	private JLabel labelIngredients = new JLabel("Ingredients");
	private JTextArea ingredientsArea = new JTextArea("Tomate, ...", 5, 30);
	
	private JButton btAnnuler = new JButton("ANNULER");
    private JButton btValider = new JButton("VALIDER");
	

	public CalculListeGraphe(MenuPrincipal owner) {

	    super(owner,true); //constructeur de la classe Mère: owner = propriétaire de la fenêtre (son parent), le second paramètre est true pour la rendre modale
	    
	    
	    
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx=0;
        gbc.gridy=0;
        this.add(labelNom, gbc);
        gbc.gridx=1;
        gbc.gridwidth=2;
        this.add(nomField, gbc);
        
        gbc.gridx=0;
        gbc.gridy=1;
        gbc.gridwidth=1;
        this.add(labelPrix, gbc);
        gbc.gridx=1;
        gbc.anchor = GridBagConstraints.WEST;
        this.add(prixField, gbc);
        
        gbc.gridx=0;
        gbc.gridy=2;
        gbc.gridwidth =4;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        this.add(labelIngredients, gbc);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridy=3;
        this.add(ingredientsArea, gbc);

        gbc.gridx=1;
        gbc.gridy=4;
        gbc.gridwidth=1;
        btAnnuler.setBackground(Color.RED);
        this.add(btAnnuler, gbc);
        gbc.gridx=2;
        gbc.gridwidth=3;
        gbc.fill = GridBagConstraints.BOTH;
        btValider.setBackground(Color.GREEN);
        this.add(btValider, gbc);
        
        if(p != null) {
        	nomField.setText(p.getNom());
        	ingredientsArea.setText(p.getIngredients());
        	prixField.setText(p.getTarif()+"");
        	setTitle("Modification");
        } else {
        	
        	setTitle("Ajout");
        }
        
        this.pack();
        
        btAnnuler.addActionListener(this);
        btValider.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {
	    if (e.getSource() == btAnnuler){
	        pizza=null; //aucune pizza
	        this.setVisible(false); //on ferme la fenêtre
	    }
	    if (e.getSource() == btValider){
	        pizza= new Pizza(nomField.getText(), ingredientsArea.getText(), Double.parseDouble(prixField.getText())); // on envoie la pizza
	        this.setVisible(false); //on ferme la fenêtre
	    }
	    dispose();
	}
}