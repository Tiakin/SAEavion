package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * La classe ChargerAeroport.
 */
public class ChargerAeroport {
	
	/**
	 * La map.
	 */
	private Map<String,String[]> m;
	
	private Aeroport[] aeroports;
	
	/**
	 * Le fichier sélectionné.
	 */
	private File selectedFile;

	/**
	 * Si ChargerAeroport est valide
	 */
	private boolean valid;
    
    /**
     * Instancie ChargerAeroport.
     *
     * @param file le fichier
     */
    public ChargerAeroport(File file) {
    	this.selectedFile = file;
        m = new HashMap<>();
        valid = true;
        readFile();
    }
    
    /**
     * Read file.
     */
    private void readFile() {
        BufferedReader reader;
        Scanner ent;
        try {
        	FileReader fr = new FileReader(selectedFile);
            reader = new BufferedReader(fr);
            ent = new Scanner(fr);
            
            while (ent.hasNextLine()) {
            	String[] res = ent.nextLine().split(";");
            	if(res.length != 10) {
            		ToolBox.sendErrorMessage("Erreur lors de la lecture des aéroports :\r\n Il n'y a pas le bon nombre d'informations par ligne.");
            		valid = false;
            		return;
            	}
                m.put(res[0], res);
            }
            reader.close();
            fr.close();
            ent.close();
        } catch(IOException ex) { 
        	ToolBox.sendErrorMessage("Erreur lors de la lecture des aéroports :\r\n Le fichier n'existe pas.");
        	valid = false; 
        }
    }
    
    /**
     * Récupère la map aéroport.
     *
     * @return la map aéroport
     */
    public Map<String, String[]> getMapAero() {
        return m;
    }

    /**
     * Si ChargerAeroport est valide.
     *
     * @return true si valide
     */
	public boolean isValid() {
		return valid;
	}

    /**
     * Crée les objets Aeroport à partir des données chargées.
     *
     * @return un tableau d'objets Aeroport
     */
	public Aeroport[] creationAeroports() {
	    aeroports = new Aeroport[m.size()];
	    int index = 0;
	    for (Map.Entry<String, String[]> entry : m.entrySet()) {
	        String[] data = entry.getValue();
	        String code = data[0];

	        // Conversion des coordonnées géographiques en degrés décimaux
	        double latitude = convertToDecimal(
	                Integer.parseInt(data[2]),  // Latitude degrés
	                Integer.parseInt(data[3]),  // Latitude minutes
	                Integer.parseInt(data[4]),  // Latitude secondes
	                data[5].charAt(0));         // Latitude direction (N ou S)

	        double longitude = convertToDecimal(
	                Integer.parseInt(data[6]),  // Longitude degrés
	                Integer.parseInt(data[7]),  // Longitude minutes
	                Integer.parseInt(data[8]),  // Longitude secondes
	                data[9].charAt(0));         // Longitude direction (E ou W)

	        // Vérification de la validité des coordonnées
	        if (latitude < -90 || latitude > 90 || longitude < -180 || longitude > 180) {
	            System.out.println("Coordonnées invalides pour l'aéroport: " + code);
	            continue;
	        }

	        Aeroport aeroport = new Aeroport(code, latitude, longitude);
	        aeroports[index++] = aeroport;
	    }
	    
	    return aeroports;
	}

	// Méthode pour convertir les coordonnées géographiques en degrés décimaux
	private double convertToDecimal(int deg, int min, int sec, char dir) {
	    double decimal = deg + (min / 60.0) + (sec / 3600.0);
	    if (dir == 'S' || dir == 'W') {
	        decimal = -decimal;
	    }
	    return decimal;
	}
	
	public Aeroport[] getAeroports() {
		return aeroports;
	}
}
