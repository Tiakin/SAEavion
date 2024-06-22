package application;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import org.graphstream.graph.Graph;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.cache.FileBasedLocalCache;
import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.input.ZoomMouseWheelListenerCenter;
import org.jxmapviewer.painter.CompoundPainter;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.DefaultWaypoint;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.TileFactoryInfo;
import org.jxmapviewer.viewer.Waypoint;
import org.jxmapviewer.viewer.WaypointPainter;


/**
 * La Classe MenuPrincipal.
 */
public class MenuPrincipal extends JFrame implements ActionListener{

	//Composant
	/** Le menu bar. */
	private JMenuBar menuBar;
	
	
	private JXMapViewer mapViewer;
	
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
	
	/** Le nombre de conflit en cours */
	private int conflits;
	
	/** L'horaire en cours */
	private int horaireValue;
	
	/** Le niveau en cours */
	private int niveauValue;
	
	/** La marge de sécurité, 15 est la valeur par défaut */
	private int margeValue = 15;
	
	/** valeur du kmax */
	private int kmaxValue = 10;


	private File fichierVols;

	
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
        mapViewer = new JXMapViewer();
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
        
        // Ajouter JXMapViewer à JFrame
        getContentPane().add(mapViewer);
        
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
                afficherAeroports(mapViewer);
            }  
	    }
		if (action.getSource() == vols){
			Choix charger = new Choix(this, false);
			int option =charger.showOpenDialog(this);
            if (option == JFileChooser.APPROVE_OPTION) {
            	fichierVols = charger.getSelectedFile();
                calculerGrapheVols();
            }       
		}
		if (action.getSource() == grapheCharge){
			Choix charger = new Choix(this, false);
			int option = charger.showOpenDialog(this);
            if (option == JFileChooser.APPROVE_OPTION) {
                File file = charger.getSelectedFile();
                
                GraphMap<String, Integer> gm = GraphImporter.importGraphMap(file);
    			
    			//coloration
    			conflits = gm.greedyColoring();
    			
                Graph graph = GraphImporter.importGraph(gm);

                if (graph != null) {
                    System.out.println("Graph imported with " + graph.getNodeCount() + " nodes and " + graph.getEdgeCount() + " edges. the graph have "+conflits+" Conflicts.");
                    
                    ToolBox.displaygraph(graph);
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
                	GraphExporter.exportGraph(file, currentGraph, kmaxValue);
                	System.out.println("Graph exported to " + file.getAbsolutePath());
                } else {
                	ToolBox.sendErrorMessage("Erreur lors de l'exportation du graphe");
                    System.out.println("Failed to export graph.");
                }
            } 
		}
		if (action.getSource() == listegraphe){
			boolean show = false;
			if((action.getModifiers() & 1) != 0) { //shift = 1
				show = true;
            }
	        CalculListeGraphe calculListeGraphe = new CalculListeGraphe(this);
	        calculListeGraphe.showDialog();
	        if(calculListeGraphe.isValid()) {
	        	String pathSource = calculListeGraphe.getSourcePath();
	        	String patternSource = calculListeGraphe.getSourcePattern();
	        	String pathSortie = calculListeGraphe.getSortiePath();
    			String patternSortie = calculListeGraphe.getSortiePattern();
    			try (BufferedWriter writeToCSV = new BufferedWriter(new FileWriter(pathSortie+"coloration-groupe2.E1.csv"))) {
		        	boolean hasNext = true;
		        	for(int i = 0; hasNext; i++) {
		        		//importation
		        		String nameSource = patternSource.replace("0", i+"");
		        		File fileSource = new File(pathSource+nameSource);
		        		if(fileSource.exists()) {
		        			System.out.println(fileSource);
		        			GraphMap<String, Integer> gm = GraphImporter.importGraphMap(fileSource);
		        			
		        			//coloration
		        			int conflits = gm.greedyColoring();
		        			
		        			
		        			//si shift appuyé
		        			if(show) {
		        				ToolBox.displaygraph(GraphImporter.importGraph(gm));
		        			}
		        			//exportation
		        			String nameSortie = patternSortie.replace("0", i+"");
		        			File fileSortie = new File(pathSortie+nameSortie);
		        			System.out.println(fileSortie);
		        			GraphExporter.exportGraphColor(fileSortie, gm);
		        			
		        			//écrire dans le csv
		        			writeToCSV.write(nameSortie+";"+conflits);
		        			writeToCSV.newLine();
		        			
		        		} else {
		        			hasNext = false;
		        		}
		        	}
	        	} catch (IOException e) {
					e.printStackTrace();
				}
	        }
	    }
		if (action.getSource() == horaire){
			Horaire horaire = new Horaire(this);
	        int value = horaire.showDialog();
	        if(value == ToolBox.RESETVALUE) {
	        	horaireValue = ToolBox.NOVALUE;
	        } else if(value != ToolBox.KEEPVALUE) {
				horaireValue = value;
			}
	    }
		if (action.getSource() == niveau){
			EditDialog niveau = new EditDialog(this, "Niveau", "Entrez un niveau :");
			int value = niveau.showDialog();
			if(value == ToolBox.RESETVALUE) {
				niveauValue = ToolBox.NOVALUE;
	        } else if(value != ToolBox.KEEPVALUE) {
				niveauValue = value;
			}
	    }
		if (action.getSource() == statistique){
	        Statistique statistique = new Statistique(this);
	        if(currentGraph != null) {
	        	statistique.updateStatistics(currentGraph, conflits);
	        }
	        statistique.showDialog();
	        statistique.dispose();
	        if((action.getModifiers() & 1) != 0) { //shift = 1
			ListeVolsAeroport lva = new ListeVolsAeroport(this, "DOL",pfc.getGraphMap());
			lva.showDialog();
            }
	    }
		if (action.getSource() == kmax){
			EditDialog kmax = new EditDialog(this, "Kmax", "Entrez un nombre :");
			int value = kmax.showDialog();
			if(value == ToolBox.RESETVALUE) {
				kmaxValue = 10;
	        } else if(value != ToolBox.KEEPVALUE) {
				kmaxValue = value;
				calculerGrapheVols();
			}
	    }
		if (action.getSource() == marge){
			EditDialog marge = new EditDialog(this, "Marge de sécurité", "Entrez un temps (en minutes) :");
			int value = marge.showDialog();
			if(value == ToolBox.RESETVALUE) {
				margeValue = 15; // valeur par défaut
	        } else if(value != ToolBox.KEEPVALUE) {
				margeValue = value;
				calculerGrapheVols();
			}
	    }
		 
		
	}
	private List<Waypoint> waypoints;
	
	private void afficherAeroports(JXMapViewer mapViewer) {
	    if (ch != null && ch.isValid()) {
	        // Utilisation de la méthode creationAeroports de ChargerAeroport pour obtenir les Aeroport[]
	        Aeroport[] aeroports = ch.creationAeroports();

	        /* Afficher les aéroports pour débogage
            for (Aeroport aeroport : aeroports) {
                System.out.println(aeroport); // Utilisation de System.out.println pour afficher les aéroports
            }
			*/
	        
	        // Création d'une liste de Waypoints pour stocker les marqueurs des aéroports
	        waypoints = new ArrayList<>();

	        // Parcourir chaque aéroport dans le tableau
	        for (Aeroport aeroport : aeroports) {
	            afficherUnAeroport(aeroport);
	        }

	        // Création d'un Set de Waypoints à partir de la liste
	        Set<Waypoint> waypointSet = new HashSet<>(waypoints);

	        // Création d'un WaypointPainter avec CustomWaypointRenderer pour dessiner les marqueurs sur la carte
	        WaypointPainter<Waypoint> waypointPainter = new WaypointPainter<>();
	        waypointPainter.setRenderer(new CustomWaypointRenderer());
	        waypointPainter.setWaypoints(waypointSet);

	        // Ajout du WaypointPainter à JXMapViewer pour afficher les marqueurs
	        mapViewer.setOverlayPainter(waypointPainter);

	        // Forcer la carte à se rafraîchir
	        mapViewer.repaint();
	    } else {
	        System.out.println("Le chargeur d'aéroports n'est pas valide ou est nul.");
	    }
	}
	
	public void afficherUnAeroport(Aeroport aeroport) {
		double latitude = aeroport.getLatitude();
        double longitude = aeroport.getLongitude();

        // Vérification des coordonnées avant de créer un GeoPosition
        if (latitude >= -90 && latitude <= 90 && longitude >= -180 && longitude <= 180) {
            GeoPosition position = new GeoPosition(latitude, longitude);

            // Utilisation de CustomWaypoint avec code d'aéroport
            CustomWaypoint waypoint = new CustomWaypoint(position, aeroport.getCode());
            // Ajouter le Waypoint à la liste des marqueurs
            waypoints.add(waypoint);
            
            aeroport.setIsValide(true);
            aeroport.setWaypoint(waypoint);
        } else {
            System.out.println("Coordonnées invalides pour l'aéroport: " + aeroport.getCode());
        }
	}
	
	public void dessinerVols(List<Vol> vols) {
        Set<Waypoint> waypoints = new HashSet<>();
        List<GeoPosition> allTracks = new ArrayList<>();

        for (Vol vol : vols) {
            Aeroport aeroportA = vol.getAeroportArrivee();
            Aeroport aeroportD = vol.getAeroportDepart();

            if (!aeroportA.isValide()) {
                afficherUnAeroport(aeroportA);
            } else if (!aeroportD.isValide()) {
                afficherUnAeroport(aeroportD);
            }

            // Utiliser CustomWaypoint au lieu de DefaultWaypoint
            CustomWaypoint wpA = new CustomWaypoint(new GeoPosition(aeroportA.getLatitude(), aeroportA.getLongitude()), aeroportA.getCode());
            CustomWaypoint wpD = new CustomWaypoint(new GeoPosition(aeroportD.getLatitude(), aeroportD.getLongitude()), aeroportD.getCode());

            // Ajouter les custom waypoints à l'ensemble
            waypoints.add(wpA);
            waypoints.add(wpD);

            // Ajouter les GeoPositions à la liste des tracks
            allTracks.add(wpA.getPosition());
            allTracks.add(wpD.getPosition());
        }

        // Créer un WaypointPainter avec tous les custom waypoints
        WaypointPainter<Waypoint> waypointPainter = new WaypointPainter<>();
        waypointPainter.setWaypoints(waypoints);
        waypointPainter.setRenderer(new CustomWaypointRenderer()); // Utiliser CustomWaypointRenderer

        // Créer un RoutePainter avec toutes les routes
        RoutePainter routePainter = new RoutePainter(allTracks);

        // Créer un CompoundPainter avec les deux peintres
        CompoundPainter<JXMapViewer> painter = new CompoundPainter<>();
        painter.setPainters(List.of(routePainter, waypointPainter));

        mapViewer.setOverlayPainter(painter);
    }
	
	private void calculerGrapheVols() {
		if(ch != null && ch.isValid()) {
			if(fichierVols != null) {
				pfc = new ProcessCollision(fichierVols,kmaxValue);
				// initialisation de la liste des aeroports
				pfc.setAeroportsPC(ch.getAeroports());
				pfc.processLineCollision(ch, margeValue);
				conflits = pfc.getGraphMap().greedyColoring();
				currentGraph = GraphImporter.importGraph(pfc.getGraphMap());
				System.out.println(pfc.getGraphMap());
				if (currentGraph != null) {
					if(currentGraph.getNodeCount() > 0) {
						System.out.println("Graph imported with " + currentGraph.getNodeCount() + " nodes and " + currentGraph.getEdgeCount() + " edges.");
				    	ToolBox.displaygraph(currentGraph);
				    	
				    	dessinerVols(pfc.getListVols());
					}
				} else {
					ToolBox.sendErrorMessage("Erreur lors de l'importation du graphe");
				    System.out.println("Failed to import graph.");
				}
			} else {
				ToolBox.sendErrorMessage("Erreur : Il faut d'abord importer les vols");
			}
		} else {
			ToolBox.sendErrorMessage("Erreur : Il faut d'abord importer les aéroports");
		}
	}

}
