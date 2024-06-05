package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ChargerAeroport {
	private Map<String,String[]> m;
	private File selectedFile;
    
    public ChargerAeroport(File file) {
    	this.selectedFile = file;
        m = new HashMap<>();
        readFile();
    }
    
    public void readFile() {
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
                processLine(ent.nextLine());
            }
                reader.close(); fr.close(); ent.close();
            } catch(IOException ex) { System.err.println(ex); }
    }
    private void processLine(String s) {
        String[] res = s.split(";");
        m.put(res[0], res);
    }
    
    public Map getMapAero() {
        return m;
    }
}
