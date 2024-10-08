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
 * La classe EditDialog.
 * 
 * @author Killian
 */
public class EditDialog extends JDialog implements ActionListener {

	// Attibuts graphiques
	/**
	 * Le label nombre.
	 */
	private JLabel labelNombre;

	/**
	 * Le panel.
	 */
	private JPanel panel = new JPanel();

	/**
	 * Le text field.
	 */
	private JTextField textField = new JTextField(2);

	/**
	 * Le boutton retour.
	 */
	private JButton retourButton = new JButton("Retour");

	/**
	 * Le boutton valider.
	 */
	private JButton validerButton = new JButton("Valider");

	/**
	 * La valeur.
	 */
	private int valeur;

	/**
	 * Instancie un nouveau EditDialog.
	 *
	 * @param owner La fenêtre principale
	 * @param title le titre
	 * @param label le message pour la modification
	 * 
	 */
	public EditDialog(MenuPrincipal owner, String title, String label) {

		super(owner, true); // constructeur de la classe Mère: owner = propriétaire de la fenêtre (son
							// parent), le second paramètre est true pour la rendre modale

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

		valeur = ToolBox.KEEPVALUE;
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
            if (textField.getText().isBlank()) {
                valeur = ToolBox.RESETVALUE;
            } else {
                try {
                    valeur = Integer.parseInt(textField.getText());
                } catch (NumberFormatException e) {
                    valeur = ToolBox.RESETVALUE; // Définit la valeur de reset en cas d'erreur de conversion
                    ToolBox.sendErrorMessage("Erreur : Vous devez saisir un nombre valide !");
                }
            }
            // Met à jour le titre avec la valeur si elle est définie
            if (valeur != ToolBox.RESETVALUE) {
                setTitle(getTitle().split("\\:")[0].trim() + " : " + valeur);
            } else {
                setTitle(getTitle().split("\\:")[0].trim()); // Réinitialise le titre à l'original
            }
			this.setVisible(false); // on ferme la fenêtre
		}
		dispose();
	}

	/**
	 * Affiche le dialogue.
	 *
	 * @return la marge
	 * 
	 */
	public int showDialog() {
		setVisible(true);
		return valeur;
	}
}