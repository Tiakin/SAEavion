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
	}
	
	private void initialisation() {
    	JMenuBar menuBar = new JMenuBar();

        // Menu Fichier
        JMenu fichierMenu = new JMenu("Fichier");
        
        JMenuItem aeroport = new JMenuItem("Charger les aéroports");
        aeroport.addActionListener(e -> Main.openAeroport());
        fichierMenu.add(aeroport);
        
        fichierMenu.add(new JMenuItem("Charger les vols"));
        
        fichierMenu.add(new JMenuItem("Charger un graphe"));
        
        
        fichierMenu.addSeparator();
        
        
        fichierMenu.add(new JMenuItem("Exporter le graphe"));
        
        
        fichierMenu.addSeparator();
        
        
        JMenuItem listegraphe = new JMenuItem("Calcul d'une liste de graphe");
        listegraphe.addActionListener(e -> Main.openListe());
        fichierMenu.add(listegraphe);
        
        menuBar.add(fichierMenu);

        // Menu Affichage
        JMenu afficherMenu = new JMenu("Affichage");
        
        JMenuItem horaire = new JMenuItem("Horaire");
        horaire.addActionListener(e -> Main.openHoraire());
        afficherMenu.add(horaire);
        
        JMenuItem niveau = new JMenuItem("Niveau");
        niveau.addActionListener(e -> Main.openNiveau());
        afficherMenu.add(niveau);
        
        afficherMenu.addSeparator();
        
        JMenuItem statistique = new JMenuItem("Statistique");
        statistique.addActionListener(e -> Main.openStatistique());
        afficherMenu.add(statistique);
        
        menuBar.add(afficherMenu);

        // Menu Édition
        JMenu editionMenu = new JMenu("Édition");
        
        JMenuItem kmax = new JMenuItem("Kmax");
        kmax.addActionListener(e -> Main.openKmax());
        editionMenu.add(kmax);
        
        JMenuItem marge = new JMenuItem("Marge de sécurité");
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
		// TODO Auto-generated method stub
		
	}

}
