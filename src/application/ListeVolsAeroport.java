package application;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.DefaultListModel;


/**
 * La classe ListAeroport.
 */
public class ListeVolsAeroport extends JDialog implements ActionListener {

	// Attributs graphiques
    /**
     * Le label arrivants.
     */
    private JLabel labelArrivants;
    
    /**
     * Le label partants.
     */
    private JLabel labelPartants;
    
    /**
     * La liste arrivants.
     */
    private JList<String> listArrivants;
    
    /**
     * La liste partants.
     */
    private JList<String> listPartants;
    
    /**
     * Le model arrivants.
     */
    private DefaultListModel<String> modelArrivants;
    
    /**
     * Le model partants.
     */
    private DefaultListModel<String> modelPartants;
    
    /**
     * Le panel.
     */
    private JPanel panel;
    
    /**
     * Le retour button.
     */
    private JButton retourButton;

    /**
     * Instancie un nouveau ListAeroport.
     *
     * @param owner La fenêtre principale
     * @param aeroport l'aéroport pour lequel afficher les vols
     * @param graph le graphe des vols
     */
    public ListeVolsAeroport(MenuPrincipal owner, String aeroport, GraphMap<String, Integer> graph) {

        super(owner, true); // constructeur de la classe Mère: owner = propriétaire de la fenêtre (son parent), le second paramètre est true pour la rendre modale

        setTitle("Affichage de l'aéroport : "+ aeroport);
        setSize(400, 300);
        setResizable(false);

        labelArrivants = new JLabel("Vols arrivant à " + aeroport);
        labelPartants = new JLabel("Vols partant de " + aeroport);
        modelArrivants = new DefaultListModel<>();
        modelPartants = new DefaultListModel<>();
        listArrivants = new JList<>(modelArrivants);
        listPartants = new JList<>(modelPartants);
        
        JScrollPane scrollPaneArrivants = new JScrollPane(listArrivants);
        JScrollPane scrollPanePartants = new JScrollPane(listPartants);
        
        panel = new JPanel(new BorderLayout());

        retourButton = new JButton("Retour");

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(labelArrivants, BorderLayout.NORTH);
        topPanel.add(scrollPaneArrivants, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(labelPartants, BorderLayout.NORTH);
        bottomPanel.add(scrollPanePartants, BorderLayout.CENTER);

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(bottomPanel, BorderLayout.CENTER);
        panel.add(retourButton, BorderLayout.SOUTH);

        getContentPane().add(panel);

        retourButton.addActionListener(this);

        // Récupérer et afficher les vols
        for (GraphMap<String, Integer>.SommetPrinc sp : graph.getNodes()) {
            String val = sp.getVal()+" de "+sp.getTime()+" à "+sp.getTime().plusMinutes(sp.getDuree());
            if (val.contains(aeroport + "-")) {
                // Vol partant de l'aéroport
                modelPartants.addElement(val);
            } else if (val.contains("-" + aeroport)) {
                // Vol arrivant à l'aéroport
                modelArrivants.addElement(val);
            }
        }
    }

    /**
     * Action perfomé.
     *
     * @param action L'action de clique
     */
    public void actionPerformed(ActionEvent action) {
        if (action.getSource() == retourButton) {
            this.setVisible(false); // on ferme la fenêtre
        }
        dispose();
    }
    
    /**
     * Affiche la fenêtre de liste des vols pour un aéroport donné.
     */
    public void showDialog() {
        setVisible(true);
    }
}
