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
	
	/**
	 * Le fichier selectionné.
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
        //String st = null;
        BufferedReader reader ;
        Scanner ent ;
        try {
        	FileReader fr = new FileReader(selectedFile);
            reader = new BufferedReader(fr) ;
            ent = new Scanner(fr);
            
            while (ent.hasNextLine()) {
            	String[] res = ent.nextLine().split(";");
            	if(res.length != 10) {
            		ToolBox.sendErrorMessage("Erreur lors de la lecture des aeroports :\r\n Il n'y a pas le bon nombre d'informations par ligne.");
            		valid = false;
            		return;
            	}
                m.put(res[0], res);
            }
                reader.close(); fr.close(); ent.close();
            } catch(IOException ex) { 
            	ToolBox.sendErrorMessage("Erreur lors de la lecture des aeroports :\r\n Le fichier n'existe pas.");
            	valid = false; 
            }
    }
    
    /**
     * Récupère la map aeroport.
     *
     * @return la map aeroport
     */
    public Map<String, String[]> getMapAero() {
        return m;
    }

    /**
     * Si ChargerAeroport est valide
     *
     * @return true si valide
     */
	public boolean isValid() {
		return valid;
	}
}
