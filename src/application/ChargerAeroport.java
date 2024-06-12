package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import javax.swing.JOptionPane;

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
     * Instancie ChargerAeroport.
     *
     * @param file le fichier
     */
    public ChargerAeroport(File file) {
    	this.selectedFile = file;
        m = new HashMap<>();
        readFile();
    }
    
    /**
     * Read file.
     */
    private void readFile() {
        //String st = null;
        BufferedReader reader ;
        //= new BufferedReader(new InputStreamReader(System.in));
        Scanner ent ;
        //= new Scanner(System.in);
        try {
        	FileReader fr = new FileReader(selectedFile);
            reader = new BufferedReader(fr) ;
            ent = new Scanner(fr);
            
            while (ent.hasNextLine()) {
                //((line=reader.readLine())!=null) {
            	String[] res = ent.nextLine().split(";");
            	if(res.length != 10) {
            		ToolBox.sendErrorMessage("Erreur lors de la lecture des aeroports :\r\n Il n'y a pas le bons nombre d'information par ligne.");
            		return;
            	}
                m.put(res[0], res);
            }
                reader.close(); fr.close(); ent.close();
            } catch(IOException ex) { System.err.println(ex); }
    }
    
    /**
     * Récupère la map aeroport.
     *
     * @return la map aeroport
     */
    public Map<String, String[]> getMapAero() {
        return m;
    }
}
