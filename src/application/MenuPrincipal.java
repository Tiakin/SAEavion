package application;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.cache.FileBasedLocalCache;
import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.input.ZoomMouseWheelListenerCenter;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.TileFactoryInfo;

public class MenuPrincipal extends JFrame implements ActionListener{

	
	private JMenuBar menuBar;
	
	
	private JMenu fichierMenu;
	
	private JMenuItem aeroport;
	private JMenuItem vols;
	private JMenuItem grapheCharge;
	
	private JMenuItem grapheExport;
	
	private JMenuItem listegraphe;
	
	
	private JMenu afficherMenu;
	
	private JMenuItem horaire;
	private JMenuItem niveau;
	
	
	private JMenuItem statistique;
	
	
	private JMenu editionMenu;
	
	private JMenuItem kmax;
	private JMenuItem marge;

	
	public MenuPrincipal() {
		this.setTitle("Gestionnaire des Vols");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(450, 500);
        
        try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			UIManager.put("FileChooser.noPlacesBar", true);
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
        
        initialisation();
        
        aeroport.addActionListener(this);
        vols.addActionListener(this);
        grapheCharge.addActionListener(this);
        
        grapheExport.addActionListener(this);
        
        listegraphe.addActionListener(this);
        
        horaire.addActionListener(this);
        niveau.addActionListener(this);
        
        statistique.addActionListener(this);
        
        kmax.addActionListener(this);
        marge.addActionListener(this);
	}
	
	private void initialisation() {
    	menuBar = new JMenuBar();

        // Menu Fichier
        fichierMenu = new JMenu("Fichier");
        
        aeroport = new JMenuItem("Charger les aéroports");
        fichierMenu.add(aeroport);
        
        vols = new JMenuItem("Charger les vols");
        fichierMenu.add(vols);
        
        grapheCharge = new JMenuItem("Charger un graphe");
        fichierMenu.add(grapheCharge);
        
        
        fichierMenu.addSeparator();
        
        grapheExport = new JMenuItem("Exporter le graphe");
        fichierMenu.add(grapheExport);
        
        
        fichierMenu.addSeparator();
        
        
        listegraphe = new JMenuItem("Calcul d'une liste de graphe");
        fichierMenu.add(listegraphe);
        
        menuBar.add(fichierMenu);

        // Menu Affichage
        afficherMenu = new JMenu("Affichage");
        
        horaire = new JMenuItem("Horaire");
        afficherMenu.add(horaire);
        
        niveau = new JMenuItem("Niveau");
        afficherMenu.add(niveau);
        
        afficherMenu.addSeparator();
        
        statistique = new JMenuItem("Statistique");
        afficherMenu.add(statistique);
        
        menuBar.add(afficherMenu);

        // Menu Édition
        editionMenu = new JMenu("Édition");
        
        kmax = new JMenuItem("Kmax");
        editionMenu.add(kmax);
        
        marge = new JMenuItem("Marge de sécurité");
        editionMenu.add(marge);
        
        menuBar.add(editionMenu);
        
        
        // Create a TileFactoryInfo for OSM
        TileFactoryInfo info = new OSMTileFactoryInfo();
        DefaultTileFactory tileFactory = new DefaultTileFactory(info);
        tileFactory.setThreadPoolSize(8);

        // Setup local file cache
        File cacheDir = new File(System.getProperty("user.home") + File.separator + ".jxmapviewer2");
        tileFactory.setLocalCache(new FileBasedLocalCache(cacheDir, false));

        
        // Setup JXMapViewer
        JXMapViewer mapViewer = new JXMapViewer();
        mapViewer.setTileFactory(tileFactory);
        mapViewer.setZoom(14); // mettre à 13 en plein écran ça serait top je pense
        GeoPosition france = new GeoPosition(46,  13, 55, 2, 12, 34);
        mapViewer.setAddressLocation(france);
        
        
        //event mouvement de souris
        PanMouseInputListener mm = new PanMouseInputListener(mapViewer);
        mapViewer.addMouseListener(mm);
        mapViewer.addMouseMotionListener(mm);
        
        //event zoom de la souris
        mapViewer.addMouseWheelListener(new ZoomMouseWheelListenerCenter(mapViewer));
        
        
        this.add(mapViewer);
        
        
        this.setJMenuBar(menuBar);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == aeroport){
			Choix charger = new Choix(this, false);
			int option =charger.showOpenDialog(this);
            if (option == JFileChooser.APPROVE_OPTION) {
                File file = charger.getSelectedFile();
                
            }  
	    }
		if (e.getSource() == vols){
			Choix charger = new Choix(this, false);
			int option =charger.showOpenDialog(this);
            if (option == JFileChooser.APPROVE_OPTION) {
                File file = charger.getSelectedFile();
                
            }       
		}
		if (e.getSource() == grapheCharge){
			Choix charger = new Choix(this, false);
			int option =charger.showOpenDialog(this);
            if (option == JFileChooser.APPROVE_OPTION) {
                File file = charger.getSelectedFile();
                
            }  
		}
		if (e.getSource() == grapheExport){
			Choix sauver = new Choix(this, false);
			int option = sauver.showSaveDialog(this);
            if (option == JFileChooser.APPROVE_OPTION) {
                File file = sauver.getSelectedFile();
                
            } 
		}
		if (e.getSource() == listegraphe){
	        CalculListeGraphe calculListeGraphe = new CalculListeGraphe(this);
	        calculListeGraphe.showDialog();
	    }
		if (e.getSource() == horaire){
			Horaire horaire = new Horaire(this);
	        horaire.showDialog();
	    }
		if (e.getSource() == niveau){
			EditDialog niveau = new EditDialog(this, "Niveau", "Entrez un niveau :");
			niveau.showDialog();
	    }
		if (e.getSource() == statistique){
	        Statistique statistique = new Statistique(this);
	        statistique.showDialog();
	    }
		if (e.getSource() == kmax){
			EditDialog kmax = new EditDialog(this, "Kmax", "Entrez un nombre :");
			kmax.showDialog();
	    }
		if (e.getSource() == marge){
			EditDialog marge = new EditDialog(this, "Marge de sécurité", "Entrez un temps (en minutes) :");
			marge.showDialog();
	    }
		 
		
	}

}
