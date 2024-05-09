package application;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;

public class Main {
    public static void main(String[] args) {
            JFrame frame = new JFrame("Gestionnaire des Vols");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(450, 500);
            
            try {
    			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    			UIManager.put("FileChooser.noPlacesBar", Boolean.TRUE);
    		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
    				| UnsupportedLookAndFeelException e) {
    			e.printStackTrace();
    		}

            JMenuBar menuBar = new JMenuBar();

            // Menu Fichier
            JMenu fichierMenu = new JMenu("Fichier");
            
            JMenuItem aeroport = new JMenuItem("Charger les aéroports");
            aeroport.addActionListener(e -> openAeroport());
            fichierMenu.add(aeroport);
            
            fichierMenu.add(new JMenuItem("Charger les vols"));
            
            fichierMenu.add(new JMenuItem("Charger un graphe"));
            
            
            fichierMenu.addSeparator();
            
            
            fichierMenu.add(new JMenuItem("Exporter le graphe"));
            
            
            fichierMenu.addSeparator();
            
            
            JMenuItem listegraphe = new JMenuItem("Calcul d'une liste de graphe");
            listegraphe.addActionListener(e -> openListe());
            fichierMenu.add(listegraphe);
            
            menuBar.add(fichierMenu);

            // Menu Affichage
            JMenu afficherMenu = new JMenu("Affichage");
            
            JMenuItem horaire = new JMenuItem("Horaire");
            horaire.addActionListener(e -> openHoraire());
            afficherMenu.add(horaire);
            
            JMenuItem niveau = new JMenuItem("Niveau");
            niveau.addActionListener(e -> openNiveau());
            afficherMenu.add(niveau);
            
            afficherMenu.addSeparator();
            
            JMenuItem statistique = new JMenuItem("Statistique");
            statistique.addActionListener(e -> openStatistique());
            afficherMenu.add(statistique);
            
            menuBar.add(afficherMenu);

            // Menu Édition
            JMenu editionMenu = new JMenu("Édition");
            
            JMenuItem kmax = new JMenuItem("Kmax");
            kmax.addActionListener(e -> openKmax());
            editionMenu.add(kmax);
            
            JMenuItem marge = new JMenuItem("Marge de sécurité");
            marge.addActionListener(e -> openMarge());
            editionMenu.add(marge);
            
            menuBar.add(editionMenu);
            
            JLabel lbl=new JLabel();
            lbl.setIcon(new ImageIcon(Main.class.getClassLoader().getResource("resources/img.png")));
            frame.add(lbl);
            
            
            frame.setJMenuBar(menuBar);
            frame.setVisible(true);
    }
    
    private static void openListe() {
    	JFrame frame = new JFrame("Calcul d'une liste de graphe");
    	frame.setSize(350, 300);
    	frame.setResizable(false);
    	
    	JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JPanel panelSource = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelSource.add(new JLabel("Source :"));
        JTextField repetroireSource = new JTextField("Répertoire source", 20);
        panelSource.add(repetroireSource);
        JButton sourceButton = new JButton("Choisir");
        panelSource.add(sourceButton);
        sourceButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int option = fileChooser.showOpenDialog(frame);
            if (option == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                repetroireSource.setText(file.getAbsolutePath());
            }
        });
        
        panelSource.add(new JLabel("nom des fichiers (inserez 0 pour le nombre) :"));
        JTextField patternSource = new JTextField("graph-eval0.txt", 20);
        panelSource.add(patternSource);
        panel.add(panelSource);

        JPanel panelSortie = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelSortie.add(new JLabel("Sortie :"));
        JTextField sortiePatternField = new JTextField("Répertoire sortie", 20);
        panelSortie.add(sortiePatternField);
        JButton sortieButton = new JButton("Choisir");
        panelSortie.add(sortieButton);
        sortieButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int option = fileChooser.showOpenDialog(frame);
            if (option == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                sortiePatternField.setText(file.getAbsolutePath());
            }
        });
        
        panelSortie.add(new JLabel("nom des fichiers (inserez 0 pour le nombre) :"));
        JTextField patternSortie = new JTextField("colo-eval0.txt", 20);
        panelSortie.add(patternSortie);
        panel.add(panelSortie);

        JPanel buttonPanel = new JPanel();
        JButton retourButton = new JButton("Retour");
        JButton validerButton = new JButton("Valider");
        buttonPanel.add(retourButton);
        buttonPanel.add(validerButton);

        panel.add(buttonPanel);

        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }
    
	private static void openAeroport() {
		JFrame frame = new JFrame("Charger");
        frame.setSize(700, 400);
        
		JFileChooser jfile = new JFileChooser();
		
		jfile.setFileFilter(new FileFilter() {

			   public String getDescription() {
			       return "*.csv, *.txt";
			   }

			   public boolean accept(File f) {
			       if (f.isDirectory()) {
			           return false;
			       } else {
			           String filename = f.getName().toLowerCase();
			           return filename.endsWith(".csv") || filename.endsWith(".txt") ;
			       }
			   }
			});
		
		jfile.setDialogTitle("Ouvrir");
		
		jfile.setAcceptAllFileFilterUsed(false);
		jfile.setVisible(true);
		
		frame.getContentPane().add(jfile);
        frame.setVisible(true);
	}

	private static void openHoraire() {
		JDialog dialog = new JDialog();
		dialog.setTitle("Horaire");
        dialog.setSize(250, 100);
        dialog.setResizable(false);
        
        JPanel panel = new JPanel();
        JTextField textFieldHeure = new JTextField(2);
        JTextField textFieldMinute = new JTextField(2);
        JButton retourButton = new JButton("Retour");
        JButton validerButton = new JButton("Valider");
        

        panel.add(new JLabel("Entrez l'heure (HH:MM) :"));
        panel.add(textFieldHeure);
        panel.add(new JLabel(":"));
        panel.add(textFieldMinute);
        panel.add(retourButton);
        panel.add(validerButton);

        dialog.getContentPane().add(panel);
        dialog.setVisible(true);
	}
	
	private static void openNiveau() {
		JDialog dialog = new JDialog();
		dialog.setTitle("Niveau");
        dialog.setSize(200, 100);
        dialog.setResizable(false);
        JPanel panel = new JPanel();
        JTextField textField = new JTextField(2);
        JButton retourButton = new JButton("Retour");
        JButton validerButton = new JButton("Valider");
        

        panel.add(new JLabel("Entrez un niveau :"));
        panel.add(textField);
        panel.add(retourButton);
        panel.add(validerButton);

        dialog.getContentPane().add(panel);
        dialog.setVisible(true);
	}
	
	private static void openStatistique() {
		JFrame frame = new JFrame("Statistique");
        frame.setSize(300, 200);
        frame.setResizable(false);
        JPanel panel = new JPanel();
        
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(new JLabel("Degré moyen :"));
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(new JLabel("Nombre de composantes connexes :"));
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(new JLabel("Nombre de noeuds :"));
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(new JLabel("Nombre d'arrêtes :"));
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(new JLabel("Diamètre :"));
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(new JLabel("Nombre de conflits :"));

        frame.getContentPane().add(panel);
        frame.setVisible(true);
	}
	
	private static void openKmax() {
		JDialog dialog = new JDialog();
		dialog.setTitle("Kmax");
	    dialog.setSize(200, 100);
	    dialog.setResizable(false);
	    JPanel panel = new JPanel();
	    JTextField textField = new JTextField(2);
	    JButton retourButton = new JButton("Retour");
	    JButton validerButton = new JButton("Valider");
	
	    panel.add(new JLabel("Entrez un nombre :"));
	    panel.add(textField);
	    panel.add(retourButton);
	    panel.add(validerButton);
	
	    dialog.getContentPane().add(panel);
	    dialog.setVisible(true);
	}
	
	private static void openMarge() {
		JDialog dialog = new JDialog();
		dialog.setTitle("Marge de sécurité");
	    dialog.setSize(250, 100);
	    dialog.setResizable(false);
	    JPanel panel = new JPanel();
	    JTextField textField = new JTextField(3);
	    JButton retourButton = new JButton("Retour");
	    JButton validerButton = new JButton("Valider");
	
	    panel.add(new JLabel("Entrez un temps (en minutes) :"));
	    panel.add(textField);
	    panel.add(retourButton);
	    panel.add(validerButton);
	
	    dialog.getContentPane().add(panel);
	    dialog.setVisible(true);
	}
}
