package application;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import org.graphstream.graph.Graph;
import org.graphstream.ui.view.Viewer.CloseFramePolicy;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.cache.FileBasedLocalCache;
import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.input.ZoomMouseWheelListenerCenter;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.TileFactoryInfo;


/**
 * La Classe MenuPrincipal.
 */
public class MenuPrincipal extends JFrame implements ActionListener{

	//Composant
	/** Le menu bar. */
	private JMenuBar menuBar;
	
	
	/** Le menu fichier. */
	private JMenu fichierMenu;
	
	/** L'item aeroport. */
	private JMenuItem aeroport;
	
	/** L'item vols. */
	private JMenuItem vols;
	
	/** L'item graphe charge. */
	private JMenuItem grapheCharge;
	
	/** L'item graphe export. */
	private JMenuItem grapheExport;
	
	/** L'item listegraphe. */
	private JMenuItem listegraphe;
	
	
	/** Le menu afficher. */
	private JMenu afficherMenu;
	
	/** L'item horaire. */
	private JMenuItem horaire;
	
	/** L'item niveau. */
	private JMenuItem niveau;
	
	
	/** L'item (unique) statistique. */
	private JMenuItem statistique;
	
	
	/** Le menu edition. */
	private JMenu editionMenu;
	
	/** L'item kmax. */
	private JMenuItem kmax;
	
	/** L'item marge. */
	private JMenuItem marge;

	// variable de classe
	
	/** Le ChargerAeroport. */
	private ChargerAeroport ch;


	/** Le ProcessCollision. */
	private ProcessCollision pfc;

	/** Le graph en cours */
	private Graph currentGraph;
	
	/**
	 * Instancie un nouveau menu principal.
	 */
	public MenuPrincipal() {
		this.setTitle("Gestionnaire des Vols");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(450, 500);
        
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
	
	/**
	 * Initialisation du panel.
	 */
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

	/**
	 * Action performé.
	 *
	 * @param action l'action de clique
	 */
	@Override
	public void actionPerformed(ActionEvent action) {
		if (action.getSource() == aeroport){
			Choix charger = new Choix(this, false);
			int option =charger.showOpenDialog(this);
            if (option == JFileChooser.APPROVE_OPTION) {
                File file = charger.getSelectedFile();
                ch = new ChargerAeroport(file);
            }  
	    }
		if (action.getSource() == vols){
			Choix charger = new Choix(this, false);
			int option =charger.showOpenDialog(this);
            if (option == JFileChooser.APPROVE_OPTION) {
                File file = charger.getSelectedFile();
                pfc = new ProcessCollision(file);
                
                pfc.processLineCollision(ch);
                pfc.getGraphMap().greedyColoring();
                currentGraph = GraphImporter.importGraph(pfc.getGraphMap());
                System.out.println(pfc.getGraphMap());
                if (currentGraph != null) {
                    System.out.println("Graph imported with " + currentGraph.getNodeCount() + " nodes and " + currentGraph.getEdgeCount() + " edges.");
                    currentGraph.display().setCloseFramePolicy(CloseFramePolicy.CLOSE_VIEWER);;
                } else {
                	ToolBox.sendErrorMessage("Erreur lors de l'importation du graphe");
                    System.out.println("Failed to import graph.");
                }
                
            }       
		}
		if (action.getSource() == grapheCharge){
			Choix charger = new Choix(this, false);
			int option = charger.showOpenDialog(this);
            if (option == JFileChooser.APPROVE_OPTION) {
                File file = charger.getSelectedFile();
                Graph graph = GraphImporter.importGraph(file);

                if (graph != null) {
                    System.out.println("Graph imported with " + graph.getNodeCount() + " nodes and " + graph.getEdgeCount() + " edges.");
                    graph.display().setCloseFramePolicy(CloseFramePolicy.CLOSE_VIEWER);;
                } else {
                	ToolBox.sendErrorMessage("Erreur lors de l'importation du graphe");
                    System.out.println("Failed to import graph.");
                }
                
            }  
		}
		if (action.getSource() == grapheExport){
			Choix sauver = new Choix(this, false);
			int option = sauver.showSaveDialog(this);
            if (option == JFileChooser.APPROVE_OPTION) {
                File file = sauver.getSelectedFile();
                if(currentGraph != null) {
                	GraphExporter.exportGraph(file, currentGraph);
                	System.out.println("Graph exported to " + file.getAbsolutePath());
                } else {
                	ToolBox.sendErrorMessage("Erreur lors de l'exportation du graphe");
                    System.out.println("Failed to export graph.");
                }
            } 
		}
		if (action.getSource() == listegraphe){
	        CalculListeGraphe calculListeGraphe = new CalculListeGraphe(this);
	        calculListeGraphe.showDialog();
	    }
		if (action.getSource() == horaire){
			Horaire horaire = new Horaire(this);
	        horaire.showDialog();
	    }
		if (action.getSource() == niveau){
			EditDialog niveau = new EditDialog(this, "Niveau", "Entrez un niveau :");
			niveau.showDialog();
	    }
		if (action.getSource() == statistique){
	        Statistique statistique = new Statistique(this);
	        statistique.showDialog();
	    }
		if (action.getSource() == kmax){
			EditDialog kmax = new EditDialog(this, "Kmax", "Entrez un nombre :");
			kmax.showDialog();
	    }
		if (action.getSource() == marge){
			EditDialog marge = new EditDialog(this, "Marge de sécurité", "Entrez un temps (en minutes) :");
			marge.showDialog();
	    }
		 
		
	}

}
