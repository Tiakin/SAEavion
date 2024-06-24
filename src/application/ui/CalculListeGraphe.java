package application.ui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * la Classe CalculListeGraphe.
 * 
 * @author Killian
 */
public class CalculListeGraphe extends JDialog implements ActionListener{

	/**
	 * La fenêtre principale.
	 */
	private MenuPrincipal owner;
	
	
	// Attibuts graphiques
	
	/**
	 * Le panel.
	 */
	private JPanel panel;
	
	/**
	 * Le panel source.
	 */
	private JPanel panelSource;
	
	/**
	 * Le label source.
	 */
	private JLabel labelSource;
	
	/**
	 * Le repertoire source.
	 */
	private JTextField repertoireSource;
	
	/**
	 * Le boutton source.
	 */
	private JButton sourceButton;
	
	/**
	 * Le pattern source.
	 */
	private JTextField patternSource;
	
	/**
	 * Le label pour pattern source.
	 */
	private JLabel labelPatternSource;
	
	/**
	 * Le panel sortie.
	 */
	private JPanel panelSortie;
	
	/**
	 * Le label sortie.
	 */
	private JLabel labelSortie;
	
	/**
	 * Le repertoire sortie.
	 */
	private JTextField repertoireSortie;
	
	/**
	 * Le boutton sortie.
	 */
	private JButton sortieButton;
	
	/**
	 * Le pattern sortie.
	 */
	private JTextField patternSortie;
	
	/**
	 * Le label pour pattern sortie.
	 */
	private JLabel labelPatternSortie;
	
	/**
	 * Le button panel.
	 */
	private JPanel buttonPanel;
	
	/**
	 * Le boutton retour.
	 */
	private JButton retourButton;
	
	/**
	 * Le boutton valider.
	 */
	private JButton validerButton;
	
	/**
	 * Le boolean valider.
	 */
	private boolean valider;
	
	/**
	 * Instancie un JDialog de calcul de listes de graphes.
	 *
	 * @param owner the owner
	 * 
	 * @author Killian
	 */
	public CalculListeGraphe(MenuPrincipal owner) {

	    super(owner,true); //constructeur de la classe Mère: owner = propriétaire de la fenêtre (son parent), le second paramètre est true pour la rendre modale
	    
	    this.owner = owner;
	    
	    setTitle("Calcul d'une liste de graphe");
    	setSize(350, 300);
    	setResizable(false);
    	
    	panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        
        panelSource = new JPanel(new FlowLayout(FlowLayout.LEFT));
        labelSource = new JLabel("Source :");
        panelSource.add(labelSource);
        repertoireSource = new JTextField("Répertoire source", 20);
        panelSource.add(repertoireSource);
        sourceButton = new JButton("Choisir");
        panelSource.add(sourceButton);
        
        labelPatternSource = new JLabel("nom des fichiers (inserez 0 pour le nombre) :");
        panelSource.add(labelPatternSource);
        patternSource = new JTextField("graph-eval0.txt", 20);
        panelSource.add(patternSource);
        panel.add(panelSource);

        
        panelSortie = new JPanel(new FlowLayout(FlowLayout.LEFT));
        labelSortie = new JLabel("Sortie :");
        panelSortie.add(labelSortie);
        repertoireSortie = new JTextField("Répertoire sortie", 20);
        panelSortie.add(repertoireSortie);
        sortieButton = new JButton("Choisir");
        panelSortie.add(sortieButton);
        labelPatternSortie = new JLabel("nom des fichiers (inserez 0 pour le nombre) :");
        panelSortie.add(labelPatternSortie);
        patternSortie = new JTextField("colo-eval0.txt", 20);
        panelSortie.add(patternSortie);
        panel.add(panelSortie);

        buttonPanel = new JPanel();
        retourButton = new JButton("Retour");
        validerButton = new JButton("Valider");
        buttonPanel.add(retourButton);
        buttonPanel.add(validerButton);

        panel.add(buttonPanel);

        getContentPane().add(panel);
        
        sourceButton.addActionListener(this);
        sortieButton.addActionListener(this);
        
        retourButton.addActionListener(this);
        validerButton.addActionListener(this);
	}

	/**
	 * Action perfomé.
	 *
	 * @param action L'action de clique
	 * 
	 * @author Killian
	 */
	public void actionPerformed(ActionEvent action) {
		if (action.getSource() == sourceButton){
			Choix charger = new Choix(owner, true);
			int option = charger.showOpenDialog(this);
            if (option == JFileChooser.APPROVE_OPTION) {
                File file = charger.getSelectedFile();
                repertoireSource.setText(file.getAbsolutePath()+"\\");
            }
		}
		if (action.getSource() == sortieButton){
			Choix sauver = new Choix(owner, true);
			int option = sauver.showSaveDialog(this);
            if (option == JFileChooser.APPROVE_OPTION) {
                File file = sauver.getSelectedFile();
                repertoireSortie.setText(file.getAbsolutePath()+"\\");
            }
		}
		
	    if (action.getSource() == retourButton){
	        
	        this.setVisible(false); //on ferme la fenêtre
	        dispose();
	    }
	    if (action.getSource() == validerButton){
	        valider = true;
	        this.setVisible(false); //on ferme la fenêtre
	        dispose();
	    }
	}
	
	/**
	 * montre le JDialog.
	 * 
	 * @author Killian
	 */
	public void showDialog() {
		setVisible(true);
	}
	
	/**
	 * indique si l'utilisateur à appuyé sur le bouton valider.
	 *
	 * @return true si l'utilisateur à appuyé sur le bouton valider
	 * 
	 * @author Killian
	 */
	public boolean isValid() {
		return valider;
		
	}
	
	/**
	 * récupère le répertoire source.
	 *
	 * @return le répertoire source
	 * 
	 * @author Killian
	 */
	public String getSourcePath() {
		return repertoireSource.getText();
	}
	
	/**
	 * récupère le pattern de fichier source.
	 *
	 * @return le fichier source
	 * 
	 * @author Killian
	 */
	public String getSourcePattern() {
		return patternSource.getText();
	}
	
	/**
	 * récupère le répertoire sortie.
	 *
	 * @return le répertoire sortie
	 * 
	 * @author Killian
	 */
	public String getSortiePath() {
		return repertoireSortie.getText();
	}
	
	/**
	 * récupère le pattern de fichier sortie.
	 *
	 * @return le fichier sortie
	 * 
	 * @author Killian
	 */
	public String getSortiePattern() {
		return patternSortie.getText();
	}
}