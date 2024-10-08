package application.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import application.utils.ToolBox;

/**
 * The Class Horaire.
 * 
 * @author Killian
 */
public class Horaire extends JDialog implements ActionListener {

	// Attibuts graphiques
	/**
	 * Le label nombre.
	 */
	private JLabel labelNombre = new JLabel("Entrez l'heure (HH:MM) :");

	/**
	 * Le panel.
	 */
	private JPanel panel = new JPanel();

	/**
	 * Le text field heure.
	 */
	private JTextField textFieldHeure = new JTextField(2);

	/**
	 * Le label de separation.
	 */
	private JLabel labelSeparation = new JLabel(":");

	/**
	 * Le text field minute.
	 */
	private JTextField textFieldMinute = new JTextField(2);

	/**
	 * Le boutton retour.
	 */
	private JButton retourButton = new JButton("Retour");

	/**
	 * Le boutton valider.
	 */
	private JButton validerButton = new JButton("Valider");

	/**
	 * L'Horaire.
	 */
	private int Horaire;

	/**
	 * Instancie un nouvel horaire.
	 *
	 * @param owner la fenêtre principale
	 * 
	 */
	public Horaire(MenuPrincipal owner) {

		super(owner, true); // constructeur de la classe Mère: owner = propriétaire de la fenêtre (son
							// parent), le second paramètre est true pour la rendre modale

		setTitle("Horaire");
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

		Horaire = ToolBox.KEEPVALUE;
	}

	/**
	 * Action perfomé.
	 *
	 * @param action L'action de clique
	 * 
	 */
	public void actionPerformed(ActionEvent action) {
		if (action.getSource() == retourButton) {
			this.setVisible(false); // on ferme la fenêtre
		}
		if (action.getSource() == validerButton) {
			try {
				if (textFieldHeure.getText().isBlank() || textFieldMinute.getText().isBlank()) {
					Horaire = ToolBox.RESETVALUE; // réinitialiser la valeur
				} else if (Integer.parseInt(textFieldHeure.getText()) >= 0
						&& Integer.parseInt(textFieldHeure.getText()) < 24
						&& Integer.parseInt(textFieldMinute.getText()) >= 0
						&& Integer.parseInt(textFieldMinute.getText()) < 60) {
					Horaire = Integer.parseInt(textFieldHeure.getText()) * 100
							+ Integer.parseInt(textFieldMinute.getText()); // change la valeur sous la forme HHMM
				}
			} catch (NumberFormatException e) {
				ToolBox.sendErrorMessage("Erreur : Vous devez saisir un nombre valide !");
			}
			// Met à jour le titre avec l'horaire actuel si défini
			if (Horaire != ToolBox.RESETVALUE) {
				setTitle("Horaire : " + String.format("%02d:%02d", Horaire / 100, Horaire % 100));
			} else {
				setTitle("Horaire"); // réinitialise le titre si valeur réinitialisée
			}
			this.setVisible(false); // on ferme la fenêtre
		}
		dispose();
	}

	/**
	 * Montre le dialogue.
	 *
	 * @return l'horaire
	 * 
	 */
	public int showDialog() {
		setVisible(true);
		return Horaire;
	}
}