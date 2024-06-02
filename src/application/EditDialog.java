package application;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class EditDialog extends JDialog implements ActionListener{

	// Attibuts graphiques
	private JLabel labelNombre;
	private JPanel panel = new JPanel();
	private JTextField textField = new JTextField(2);
	private JButton retourButton = new JButton("Retour");
	private JButton validerButton = new JButton("Valider");
	private int marge;
	

	public EditDialog(MenuPrincipal owner, String title, String label) {

	    super(owner,true); //constructeur de la classe Mère: owner = propriétaire de la fenêtre (son parent), le second paramètre est true pour la rendre modale
	    
		setTitle(title);
		setSize(200, 100);
	    setResizable(false);
	    
	    labelNombre = new JLabel(label);
	    
	    panel.add(labelNombre);
	    panel.add(textField);
	    panel.add(retourButton);
	    panel.add(validerButton);
	
	    getContentPane().add(panel);
        
        retourButton.addActionListener(this);
        validerButton.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {
	    if (e.getSource() == retourButton){
	        this.setVisible(false); //on ferme la fenêtre
	        marge = -1;
	    }
	    if (e.getSource() == validerButton){
	    	marge = Integer.parseInt(textField.getText());
	        this.setVisible(false); //on ferme la fenêtre
	    }
	    dispose();
	}
	public int showDialog() {
		setVisible(true);
		return marge;
	}
}