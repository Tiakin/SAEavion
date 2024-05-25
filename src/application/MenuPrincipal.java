package application;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.cache.FileBasedLocalCache;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.WaypointPainter;
import org.jxmapviewer.viewer.TileFactoryInfo;

public class MenuPrincipal extends JFrame implements ActionListener{

	
	JMenuBar menuBar;
	
	
	JMenu fichierMenu;
	
	JMenuItem aeroport;
	JMenuItem listegraphe;
	
	
	JMenu afficherMenu;
	
	JMenuItem horaire;
	JMenuItem niveau;
	
	
	JMenuItem statistique;
	
	
	JMenu editionMenu;
	
	JMenuItem kmax;
	JMenuItem marge;
	
	public MenuPrincipal() {
		this.setTitle("Gestionnaire des Vols");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(450, 500);
        
        try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			UIManager.put("FileChooser.noPlacesBar", Boolean.TRUE);
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
        
        initialisation();
        
        aeroport.addActionListener(this);
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
        aeroport.addActionListener(e -> Main.openAeroport());
        fichierMenu.add(aeroport);
        
        fichierMenu.add(new JMenuItem("Charger les vols"));
        
        fichierMenu.add(new JMenuItem("Charger un graphe"));
        
        
        fichierMenu.addSeparator();
        
        
        fichierMenu.add(new JMenuItem("Exporter le graphe"));
        
        
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
        statistique.addActionListener(e -> Main.openStatistique());
        afficherMenu.add(statistique);
        
        menuBar.add(afficherMenu);

        // Menu Édition
        editionMenu = new JMenu("Édition");
        
        kmax = new JMenuItem("Kmax");
        editionMenu.add(kmax);
        
        marge = new JMenuItem("Marge de sécurité");
        marge.addActionListener(e -> Main.openMarge());
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
        
        
        this.add(mapViewer);
        
        
        this.setJMenuBar(menuBar);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == aeroport){
	        
	    }
		if (e.getSource() == listegraphe){
	        
	    }
		if (e.getSource() == horaire){
			Horaire horaire = new Horaire(this);
	        horaire.showDialog();
	    }
		if (e.getSource() == niveau){
			Niveau niveau = new Niveau(this);
	        niveau.showDialog();
	    }
		if (e.getSource() == statistique){
	        
	    }
		if (e.getSource() == kmax){
	        Kmax kmax = new Kmax(this);
	        kmax.showDialog();
	    }
		if (e.getSource() == marge){
	        
	    }
		 
		
	}

}
