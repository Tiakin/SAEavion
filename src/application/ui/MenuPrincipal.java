package application.ui;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.TileFactoryInfo;
import org.jxmapviewer.viewer.Waypoint;
import org.jxmapviewer.viewer.WaypointPainter;

import application.aeroport.Aeroport;
import application.aeroport.ChargerAeroport;
import application.graph.GraphExporter;
import application.graph.GraphImporter;
import application.graph.GraphMap;
import application.utils.CustomWaypoint;
import application.utils.ProcessCollision;
import application.utils.ToolBox;
import application.utils.graphic.CustomWaypointRenderer;
import application.utils.graphic.RoutePainter;

/**
 * La Classe MenuPrincipal.
 * 
 * @author Killian
 * @author Farouk
 * @author Axel
 */
public class MenuPrincipal extends JFrame implements ActionListener {

	// Composant
	/** Le menu bar. */
	private JMenuBar menuBar;

	/**
	 * Le map viewer.
	 */
	private JXMapViewer mapViewer;

	/** Le menu fichier. */
	private JMenu fichierMenu;

	/** L'item aeroport. */
	private JMenuItem aeroportItem;

	/** L'item vols. */
	private JMenuItem volsItem;

	/** L'item graphe charge. */
	private JMenuItem grapheChargeItem;

	/** L'item graphe export. */
	private JMenuItem grapheExportItem;

	/** L'item listegraphe. */
	private JMenuItem listegrapheItem;

	/** Le menu afficher. */
	private JMenu afficherMenu;

	/** L'item horaire. */
	private JMenuItem horaireItem;

	/** L'item niveau. */
	private JMenuItem niveauItem;

	/** L'item (unique) statistique. */
	private JMenuItem statistiqueItem;

	/** Le menu edition. */
	private JMenu editionMenu;

	/** L'item kmax. */
	private JMenuItem kmaxItem;

	/** L'item marge. */
	private JMenuItem margeItem;

	// variable de classe

	/** Le ChargerAeroport. */
	private ChargerAeroport ch;

	/** Le ProcessCollision. */
	private ProcessCollision pfc;

	/**
	 * Le graph en cours.
	 */
	private Graph currentGraph;

	/**
	 * Le nombre de conflit en cours.
	 */
	private int conflits;

	/**
	 * L'horaire en cours.
	 */
	private int horaireValue = ToolBox.NOVALUE;

	/**
	 * Le niveau en cours.
	 */
	private int niveauValue = ToolBox.NOVALUE;

	/**
	 * La marge de sécurité, 15 est la valeur par défaut.
	 */
	private int margeValue = 15;

	/**
	 * valeur du kmax.
	 */
	private int kmaxValue = 10;

	/**
	 * le fichier des vols.
	 */
	private File fichierVols;

	/**
	 * les aeroports.
	 */
	private Aeroport[] aeroports;

	/**
	 * les waypoints.
	 */
	private List<Waypoint> waypoints;

	/**
	 * le mouse adapter waypoint.
	 */
	private MouseAdapter waypointMouseAdapter;
	
	/**
	 * Le dialogue d'édition pour Kmax.
	 */
	private EditDialog kmax;

	/**
	 * Le dialogue d'édition pour la marge.
	 */
	private EditDialog marge;
	
	/**
	 * Le dialogue d'édition pour l'horaire.
	 */
	private Horaire horaire;
	
	/**
	 * Le dialogue d'édition pour le niveau.
	 */
	private EditDialog niveau;
	
	/**
	 * Instancie un nouveau menu principal.
	 * 
	 */
	public MenuPrincipal() {
		this.setTitle("Gestionnaire des Vols");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(450, 500);

		initialisation();

		aeroportItem.addActionListener(this);
		volsItem.addActionListener(this);
		grapheChargeItem.addActionListener(this);

		grapheExportItem.addActionListener(this);

		listegrapheItem.addActionListener(this);

		horaireItem.addActionListener(this);
		niveauItem.addActionListener(this);

		statistiqueItem.addActionListener(this);

		kmaxItem.addActionListener(this);
		margeItem.addActionListener(this);
	}

	/**
	 * Initialisation du panel.
	 * 
	 */
	private void initialisation() {
		menuBar = new JMenuBar();

		// Menu Fichier
		fichierMenu = new JMenu("Fichier");

		aeroportItem = new JMenuItem("Charger les aéroports");
		fichierMenu.add(aeroportItem);

		volsItem = new JMenuItem("Charger les vols");
		fichierMenu.add(volsItem);

		grapheChargeItem = new JMenuItem("Charger un graphe");
		fichierMenu.add(grapheChargeItem);

		fichierMenu.addSeparator();

		grapheExportItem = new JMenuItem("Exporter le graphe");
		fichierMenu.add(grapheExportItem);

		fichierMenu.addSeparator();

		listegrapheItem = new JMenuItem("Calcul d'une liste de graphe");
		fichierMenu.add(listegrapheItem);

		menuBar.add(fichierMenu);

		// Menu Affichage
		afficherMenu = new JMenu("Affichage");

		horaireItem = new JMenuItem("Horaire");
		afficherMenu.add(horaireItem);

		niveauItem = new JMenuItem("Niveau");
		afficherMenu.add(niveauItem);

		afficherMenu.addSeparator();

		statistiqueItem = new JMenuItem("Statistique");
		afficherMenu.add(statistiqueItem);

		menuBar.add(afficherMenu);

		// Menu Édition
		editionMenu = new JMenu("Édition");

		kmaxItem = new JMenuItem("Kmax");
		editionMenu.add(kmaxItem);

		margeItem = new JMenuItem("Marge de sécurité");
		editionMenu.add(margeItem);

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
		mapViewer.setZoom(14);
		GeoPosition france = new GeoPosition(46, 13, 55, 2, 12, 34);
		mapViewer.setAddressLocation(france);

		// event mouvement de souris
		PanMouseInputListener mm = new PanMouseInputListener(mapViewer);
		mapViewer.addMouseListener(mm);
		mapViewer.addMouseMotionListener(mm);

		// event zoom de la souris
		mapViewer.addMouseWheelListener(new ZoomMouseWheelListenerCenter(mapViewer));

		// Ajouter JXMapViewer à JFrame
		getContentPane().add(mapViewer);

		// Initialisation des dialogues d'édition
		kmax = new EditDialog(this, "Kmax", "Entrez un nombre :");
		marge = new EditDialog(this, "Marge de sécurité", "Entrez un temps (en minutes) :");
		horaire = new Horaire(this);
		niveau = new EditDialog(this, "Niveau", "Entrez un niveau :");

		
		this.add(mapViewer);

		this.setJMenuBar(menuBar);
	}

	/**
	 * Action performé.
	 *
	 * @param action l'action de clique
	 * 
	 */
	@Override
	public void actionPerformed(ActionEvent action) {
		if (action.getSource() == aeroportItem) {
			Choix charger = new Choix(this, false);
			int option = charger.showOpenDialog(this);
			if (option == JFileChooser.APPROVE_OPTION) {
				File file = charger.getSelectedFile();
				ch = new ChargerAeroport(file);
				afficherAeroports(mapViewer);
			}
		}
		if (action.getSource() == volsItem) {
			Choix charger = new Choix(this, false);
			int option = charger.showOpenDialog(this);
			if (option == JFileChooser.APPROVE_OPTION) {
				fichierVols = charger.getSelectedFile();
				System.out.println("vols");
				calculerGrapheVols();

			}
		}
		if (action.getSource() == grapheChargeItem) {
			Choix charger = new Choix(this, false);
			int option = charger.showOpenDialog(this);
			if (option == JFileChooser.APPROVE_OPTION) {
				File file = charger.getSelectedFile();

				GraphMap<String, Integer> gm = GraphImporter.importGraphMap(file);

				// coloration
				conflits = gm.greedyColoring();

				Graph graph = GraphImporter.importGraph(gm);

				if (graph != null) {
					System.out.println("Graph imported with " + graph.getNodeCount() + " nodes and "
							+ graph.getEdgeCount() + " edges. the graph have " + conflits + " Conflicts.");

					ToolBox.displaygraph(graph);
				} else {
					ToolBox.sendErrorMessage("Erreur lors de l'importation du graphe");
					System.out.println("Failed to import graph.");
				}

			}
		}
		if (action.getSource() == grapheExportItem) {
			Choix sauver = new Choix(this, false);
			int option = sauver.showSaveDialog(this);
			if (option == JFileChooser.APPROVE_OPTION) {
				File file = sauver.getSelectedFile();
				if (currentGraph != null) {
					GraphExporter.exportGraph(file, currentGraph, kmaxValue);
					System.out.println("Graph exported to " + file.getAbsolutePath());
				} else {
					ToolBox.sendErrorMessage("Erreur lors de l'exportation du graphe");
					System.out.println("Failed to export graph.");
				}
			}
		}
		if (action.getSource() == listegrapheItem) {
			boolean show = false;
			if ((action.getModifiers() & 1) != 0) { // shift = 1
				show = true;
			}
			CalculListeGraphe calculListeGraphe = new CalculListeGraphe(this);
			calculListeGraphe.showDialog();
			if (calculListeGraphe.isValid()) {
				String pathSource = calculListeGraphe.getSourcePath();
				String patternSource = calculListeGraphe.getSourcePattern();
				String pathSortie = calculListeGraphe.getSortiePath();
				String patternSortie = calculListeGraphe.getSortiePattern();
				try (BufferedWriter writeToCSV = new BufferedWriter(
						new FileWriter(pathSortie + "coloration-groupe2.E1.csv"))) {
					boolean hasNext = true;
					for (int i = 0; hasNext; i++) {
						// importation
						String nameSource = patternSource.replace("0", i + "");
						File fileSource = new File(pathSource + nameSource);
						if (fileSource.exists()) {
							System.out.println(fileSource);
							GraphMap<String, Integer> gm = GraphImporter.importGraphMap(fileSource);

							// coloration
							int conflits = gm.greedyColoring();

							// si shift appuyé
							if (show) {
								ToolBox.displaygraph(GraphImporter.importGraph(gm));
							}
							// exportation
							String nameSortie = patternSortie.replace("0", i + "");
							File fileSortie = new File(pathSortie + nameSortie);
							System.out.println(fileSortie);
							GraphExporter.exportGraphColor(fileSortie, gm);

							// écrire dans le csv
							writeToCSV.write(nameSortie + ";" + conflits);
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
		if (action.getSource() == horaireItem) {
			int value = horaire.showDialog();
			if (value == ToolBox.RESETVALUE) {
				horaireValue = ToolBox.NOVALUE;
				dessinerVols();
			} else if (value != ToolBox.KEEPVALUE) {
				horaireValue = value;
				dessinerVols();
			}
		}
		if (action.getSource() == niveauItem) {
			int value = niveau.showDialog();
			if (value == ToolBox.RESETVALUE) {
				niveauValue = ToolBox.NOVALUE;
				dessinerVols();
			} else if (value != ToolBox.KEEPVALUE) {
				niveauValue = value;
				dessinerVols();
			}
		}
		if (action.getSource() == statistiqueItem) {
			Statistique statistique = new Statistique(this);
			if (currentGraph != null) {
				statistique.updateStatistics(currentGraph, conflits);
			}
			statistique.showDialog();
			statistique.dispose();
		}
		if (action.getSource() == kmaxItem) {
			
			int value = kmax.showDialog();
			if (value == ToolBox.RESETVALUE) {
				kmaxValue = 10;
			} else if (value != ToolBox.KEEPVALUE) {
				kmaxValue = value;
				calculerGrapheVols();
				System.out.println("kmax");
			}
		}
		if (action.getSource() == margeItem) {
			int value = marge.showDialog();
			if (value == ToolBox.RESETVALUE) {
				margeValue = 15; // valeur par défaut
			} else if (value != ToolBox.KEEPVALUE) {
				margeValue = value;
				calculerGrapheVols();
				System.out.println("marge");
			}
		}

	}

	/**
	 * Afficher aeroports.
	 *
	 * @param mapViewer la map viewer
	 * 
	 */
	private void afficherAeroports(JXMapViewer mapViewer) {
		if (ch != null && ch.isValid()) {
			// Utilisation de la méthode creationAeroports de ChargerAeroport pour obtenir
			// les Aeroport[]
			aeroports = ch.creationAeroports();

			// Création d'une liste de Waypoints pour stocker les marqueurs des aéroports
			waypoints = new ArrayList<>();

			// Parcourir chaque aéroport dans le tableau
			for (Aeroport aeroport : aeroports) {
				afficherUnAeroport(aeroport);
			}

			// Création d'un Set de Waypoints à partir de la liste
			Set<Waypoint> waypointSet = new HashSet<>(waypoints);

			// Création d'un WaypointPainter avec CustomWaypointRenderer pour dessiner les
			// marqueurs sur la carte
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

	/**
	 * Afficher un aeroport.
	 *
	 * @param aeroport l'aeroport
	 * 
	 */
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

			aeroport.setVisible(true);
			aeroport.setWaypoint(waypoint);
		} else {
			System.out.println("Coordonnées invalides pour l'aéroport: " + aeroport.getCode());
		}
	}

	/**
	 * Dessiner vols.
	 * 
	 */
	public void dessinerVols() {
		Set<CustomWaypoint> waypoints = new HashSet<>();
		List<GeoPosition[]> allLines = new ArrayList<>();
		List<Integer> colors = new ArrayList<>();
		LocalTime lt;
		if (horaireValue != ToolBox.NOVALUE) {
			lt = LocalTime.of(horaireValue / 100, horaireValue % 100);
		} else {
			lt = null;
		}

		for (GraphMap<String, Integer>.SommetPrinc p : pfc.getGraphMap().getNodes()) {
			if (niveauValue == ToolBox.NOVALUE || niveauValue == p.getCoul()) {
				if (horaireValue == ToolBox.NOVALUE || lt != null) {
					if (lt != null) {
						if (!(p.getTime().minusMinutes(1).isBefore(lt)
								&& p.getTime().plusMinutes(p.getDuree() + 1).isAfter(lt))) {
							continue;
						}
					}
					Aeroport aeroportA = ToolBox.codeToAeroport(aeroports, p.getVal().split("\\(")[1].split("-")[0]);
					Aeroport aeroportD = ToolBox.codeToAeroport(aeroports,
							p.getVal().split("\\(")[1].split("-")[1].replace(")", ""));

					if (!aeroportA.isVisible()) {
						afficherUnAeroport(aeroportA);
					} else if (!aeroportD.isVisible()) {
						afficherUnAeroport(aeroportD);
					}

					// Utiliser CustomWaypoint au lieu de DefaultWaypoint
					CustomWaypoint wpA = new CustomWaypoint(
							new GeoPosition(aeroportA.getLatitude(), aeroportA.getLongitude()), aeroportA.getCode());
					CustomWaypoint wpD = new CustomWaypoint(
							new GeoPosition(aeroportD.getLatitude(), aeroportD.getLongitude()), aeroportD.getCode());
					// Ajouter les custom waypoints à l'ensemble
					waypoints.add(wpA);
					waypoints.add(wpD);

					// Ajouter les GeoPositions à la liste des tracks
					allLines.add(new GeoPosition[] { wpA.getPosition(), wpD.getPosition() });
					colors.add(p.getCoul());
				}
			}
		}

		// Créer un WaypointPainter avec tous les custom waypoints
		WaypointPainter<Waypoint> waypointPainter = new WaypointPainter<>();
		waypointPainter.setWaypoints(waypoints);
		waypointPainter.setRenderer(new CustomWaypointRenderer()); // Utiliser CustomWaypointRenderer

		// Créer un RoutePainter avec toutes les routes
		RoutePainter routePainter = new RoutePainter(pfc.getGraphMap(), allLines, colors);

		// Créer un CompoundPainter avec les deux peintres
		CompoundPainter<JXMapViewer> painter = new CompoundPainter<>();
		painter.setPainters(List.of(routePainter, waypointPainter));

		mapViewer.setOverlayPainter(painter);

		if (waypointMouseAdapter != null) {
			mapViewer.removeMouseListener(waypointMouseAdapter);
		}

		waypointMouseAdapter = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Point point = e.getPoint();
				for (CustomWaypoint wp : waypoints) {
					Point2D mapPoint = mapViewer.convertGeoPositionToPoint(wp.getPosition());
					Rectangle rect = new Rectangle((int) mapPoint.getX() - 15, (int) mapPoint.getY() - 15, 30, 30);
					if (rect.contains(point)) {
						ListeVolsAeroport lva = new ListeVolsAeroport(MenuPrincipal.this, wp.getCode(),
								pfc.getGraphMap());
						lva.showDialog();
						break;
					}
				}
			}
		};
		mapViewer.addMouseListener(waypointMouseAdapter);
	}

	/**
	 * Calculer graphe vols.
	 * 
	 */
	private void calculerGrapheVols() {
		if (ch != null && ch.isValid()) {
			if (fichierVols != null) {
				pfc = new ProcessCollision(fichierVols, kmaxValue);
				// initialisation de la liste des aeroports
				pfc.processLineCollision(ch, margeValue);
				conflits = pfc.getGraphMap().greedyColoring();
				currentGraph = GraphImporter.importGraph(pfc.getGraphMap());
				System.out.println(pfc.getGraphMap());
				if (currentGraph != null) {
					if (currentGraph.getNodeCount() > 0) {
						System.out.println("Graph imported with " + currentGraph.getNodeCount() + " nodes and "
								+ currentGraph.getEdgeCount() + " edges.");
						ToolBox.displaygraph(currentGraph);

						dessinerVols();
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
