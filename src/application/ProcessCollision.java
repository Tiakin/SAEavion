package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
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
     * Instancie un nouveau process collision.
     *
     * @param file le fichier
     * @param kmax le kmax
     */
    public ProcessCollision(File file,int kmax) {
    	this.selectedFile = file;
        this.gm = new GraphMap<String,Integer>(kmax);
        readFile();
    }
    
    /**
     * Read file.
     */
    public void readFile() {
        BufferedReader reader;
        Scanner ent;
        try {
            //instanciation d'un objet FileReader puis du BufferedReader
        	FileReader fr = new FileReader(selectedFile);
            reader = new BufferedReader (fr) ;
            ent = new Scanner(fr);
            
            while (ent.hasNextLine()) {
                //((line=reader.readLine())!=null) {
                lines.add(ent.nextLine());
            }
            reader.close(); fr.close(); ent.close();
        } catch(IOException ex) { System.err.println(ex); }
    }
    
    /**
     * Process line.
     *
     * @param sl la ligne
     * @return le String[]
     */
    private String[] processLine (String sl) {
        String[] result = sl.split(";");
        return result;
    }
    
    /**
     * Process line collision.
     *
     * @param ch les aeroports dans ChargerAeroport
     * @param marge la marge de sécurité
     */
    public void processLineCollision(ChargerAeroport ch, int marge) {
        for (int i=0;i<lines.size();i++) {
            String sl = lines.get(i);
            String[] res1 = processLine(sl);
            if(res1.length != 6) {
            	ToolBox.sendErrorMessage("Erreur lors de la lecture des vols :\r\n Il n'y a pas le bon nombre d'informations par ligne (à la ligne : "+i+").");
            	return;
            }
            try {
	            this.gm.addNode(res1[0]+"("+res1[1]+"-"+res1[2]+")");
	            
	            LocalTime lt1 = LocalTime.of(Integer.valueOf(res1[3]),
	            Integer.valueOf(res1[4]));
	            
	            for (int j=i+1;j<lines.size();j++) {
	                String[] res2 = processLine(lines.get(j));
	                LocalTime lt2 = LocalTime.of(Integer.valueOf(res2[3]),
	                Integer.valueOf(res2[4]));
	                boolean res = ToolBox.processACollision(ch, res1[1],
	                res1[2], res2[1], res2[2], lt1, lt2,
	                Integer.valueOf(res1[5]),
	                Integer.valueOf(res2[5]),
	                marge);
	                
	                if (res) {
	                    this.gm.addEdge(res1[0]+"("+res1[1]+"-"+res1[2]+")", res2[0]+"("+res2[1]+"-"+res2[2]+")", 0);
	                }
	            }
	        } catch (Exception e) {
	        	ToolBox.sendErrorMessage("Erreur lors de la lecture des vols :\r\n Une erreur est survenue dans la lecture d'une ligne");
			}
        }
    }
    
    /**
     * Récupère le graph map.
     *
     * @return le graph map
     */
    public GraphMap<String, Integer> getGraphMap () {
        return gm;
    }
}
