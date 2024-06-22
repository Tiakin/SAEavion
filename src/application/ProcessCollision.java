package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * La classe ProcessCollision.
 */
public class ProcessCollision {
    
    /**
     * Les lignes.
     */
    private ArrayList<String> lines = new ArrayList<>();
    
    /**
     * Le graphmap.
     */
    private GraphMap<String, Integer> gm;
    
    /**
     * Le fichier sélectionner.
     */
    private File selectedFile;
   
    /**
     * La liste des vols.
     */
    private List<Vol> vols;

    /**
     * Instancie un nouveau process collision.
     *
     * @param file le fichier
     * @param kmax le kmax
     */
    public ProcessCollision(File file, int kmax) {
        this.selectedFile = file;
        this.gm = new GraphMap<String, Integer>(kmax);
        this.vols = new ArrayList<>();
        readFile();
    }
    
    /**
     * Lit le contenu du fichier sélectionné dans lines.
     */
    public void readFile() {
        BufferedReader reader;
        Scanner ent;
        try {
            // Instanciation d'un objet FileReader puis du BufferedReader
            FileReader fr = new FileReader(selectedFile);
            reader = new BufferedReader(fr);
            ent = new Scanner(fr);
            
            while (ent.hasNextLine()) {
                lines.add(ent.nextLine());
            }
            System.out.println("Fichier lu avec succès. Nombre de lignes lues : " + lines.size());
            
            reader.close(); 
            fr.close(); 
            ent.close();
        } catch (IOException ex) { 
            System.err.println("Erreur lors de la lecture du fichier : " + ex); 
        }
    }
    
    /**
     * Process line collision.
     *
     * @param ch les aeroports dans ChargerAeroport
     * @param marge la marge de sécurité
     */
    public void processLineCollision(ChargerAeroport ch, int marge) {
        System.out.println("Début du traitement des collisions...");
        
        for (int i = 0; i < lines.size(); i++) {
            String sl = lines.get(i);
            String[] res1 = processLine(sl);
            if (res1.length != 6) {
                ToolBox.sendErrorMessage("Erreur lors de la lecture des vols :\r\n Il n'y a pas le bon nombre d'informations par ligne (à la ligne : " + i + ").");
                System.out.println("Erreur : nombre incorrect d'informations à la ligne " + i);
                return;
            }
            try {
                this.gm.addNode(res1[0] + "(" + res1[1] + "-" + res1[2] + ")");
                
                LocalTime lt1 = LocalTime.of(Integer.valueOf(res1[3]), Integer.valueOf(res1[4]));
                
                // Créer un vol à partir des informations de la ligne
                createVol(res1[0], codeToAeroport(res1[1]), codeToAeroport(res1[2]), lt1, Integer.valueOf(res1[5]));

                for (int j = i + 1; j < lines.size(); j++) {
                    String[] res2 = processLine(lines.get(j));
                    LocalTime lt2 = LocalTime.of(Integer.valueOf(res2[3]), Integer.valueOf(res2[4]));
                    boolean res = ToolBox.processACollision(
                        ch, 
                        res1[1], 
                        res1[2], 
                        res2[1], 
                        res2[2], 
                        lt1, 
                        lt2, 
                        Integer.valueOf(res1[5]), 
                        Integer.valueOf(res2[5]), 
                        marge
                    );

                    if (res) {
                        this.gm.addEdge(res1[0] + "(" + res1[1] + "-" + res1[2] + ")", res2[0] + "(" + res2[1] + "-" + res2[2] + ")", 0);
                        System.out.println("Collision détectée entre " + res1[0] + " et " + res2[0]);
                    }
                }
            } catch (Exception e) {
                ToolBox.sendErrorMessage("Erreur lors de la lecture des vols :\r\n Une erreur est survenue dans la lecture d'une ligne");
                System.out.println("Erreur : une exception est survenue à la ligne " + i);
            }
        }

        System.out.println("Fin du traitement des collisions.");
    }

    /**
     * Transforme une ligne en tableau de chaînes en utilisant le délimiteur ";".
     *
     * @param sl la ligne à traiter
     * @return le tableau de chaînes résultant
     */
    private String[] processLine(String sl) {
        return sl.split(";");
    }
    
    /**
     * Crée un vol et l'ajoute à la liste des vols.
     *
     * @param nomVol le nom du vol
     * @param aeroportDepart l'aéroport de départ
     * @param aeroportArrivee l'aéroport d'arrivée
     * @param heureDepart l'heure de départ
     * @param duree la durée du vol en minutes
     */
    private void createVol(String nomVol, Aeroport aeroportDepart, Aeroport aeroportArrivee, LocalTime heureDepart, int duree) {
        Vol vol = new Vol(nomVol, aeroportDepart, aeroportArrivee, heureDepart, duree);
        vols.add(vol);
    }
    
    /**
     * Retrouve l'aéroport correspondant au code donné.
     *
     * @param codeAeroport le code de l'aéroport à rechercher
     * @return l'aéroport correspondant, ou null si non trouvé
     */
    public Aeroport codeToAeroport(String codeAeroport) {
        if (aeroports == null) {
            System.err.println("Erreur : la liste des aéroports n'a pas été initialisée.");
            return null;
        }
        
        for (Aeroport aeroport : aeroports) {
            if (aeroport.getCode().equals(codeAeroport)) {
                return aeroport;
            }
        }
        System.err.println("Aéroport non trouvé pour le code : " + codeAeroport);
        return null; // Retourne null si l'aéroport n'est pas trouvé
    }
    
    /**
     * Récupère le graph map.
     *
     * @return le graph map
     */
    public GraphMap<String, Integer> getGraphMap() {
        return gm;
    }

    /**
     * Récupère la liste des vols.
     *
     * @return la liste des vols
     */
    public List<Vol> getListVols() {
        return vols;
    }

    private Aeroport[] aeroports;

    /**
     * Définit la liste des aéroports utilisée par le processus de collision.
     *
     * @param aero la liste des aéroports à définir
     */
    public void setAeroportsPC(Aeroport[] aero) {
        aeroports = aero;
    }
}
